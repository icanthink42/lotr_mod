#!/usr/bin/env python3
"""
Real-time 3D heightmap previewer. Watches a heightmap PNG and updates
the 3D mesh whenever the file changes (e.g. when you save in Krita).

Usage:
    python preview_heightmap.py path/to/map/dir
    python preview_heightmap.py path/to/map/dir --poll 0.5

Expects the directory to contain:
    - height.png: grayscale heightmap
    - map.png: color texture

Controls:
    - WASD:         fly forward/left/back/right
    - Space/Shift:  fly up/down
    - Mouse drag:   look around
    - Scroll:       zoom
    - q:            quit

Dependencies:
    pip install pyvista numpy Pillow
"""

import sys
import os
import time
import math
import argparse
import numpy as np
from PIL import Image
import pyvista as pv


SEA_LEVEL = 62.0
HEIGHT_MAP_PEAK = 250.0
MAP_SCALE = 4.0


def load_texture(map_path, target_w, target_h):
    """Load map.png and resize to match the mesh dimensions."""
    img = Image.open(map_path).convert("RGB")
    img = img.resize((target_w, target_h), Image.BILINEAR)
    return pv.numpy_to_texture(np.array(img))


def load_heightmap(height_path, map_path):
    """Load a grayscale PNG and build a textured PyVista mesh from it."""
    img = Image.open(height_path).convert("L")
    arr = np.array(img, dtype=np.float32)
    h, w = arr.shape

    # Downsample if very large (>2M vertices bogs down interaction)
    max_dim = 1024
    if max(h, w) > max_dim:
        factor = max_dim / max(h, w)
        new_w = int(w * factor)
        new_h = int(h * factor)
        img = img.resize((new_w, new_h), Image.BILINEAR)
        arr = np.array(img, dtype=np.float32)
        h, w = arr.shape

    # Same linear mapping as MiddleEarthChunkGenerator:
    # black (0) -> SEA_LEVEL, white (255) -> HEIGHT_MAP_PEAK
    normalized = arr / 255.0
    world_heights = SEA_LEVEL + normalized * (HEIGHT_MAP_PEAK - SEA_LEVEL)

    # Mesh X/Y in 0-1 range, scale heights to match
    max_dim = max(h, w) * MAP_SCALE
    x = np.linspace(0, w * MAP_SCALE / max_dim, w)
    y = np.linspace(0, h * MAP_SCALE / max_dim, h)
    xx, yy = np.meshgrid(x, y)
    heights = world_heights / max_dim

    # Flip Y so north is "up" when viewed from default angle
    yy = yy[::-1]

    grid = pv.StructuredGrid(xx, yy, heights)

    # UV coordinates for texturing -- StructuredGrid stores points in Fortran order
    u = np.linspace(0, 1, w)
    v = np.linspace(1, 0, h)  # flip V to match flipped Y
    uu, vv = np.meshgrid(u, v)
    grid.active_texture_coordinates = np.column_stack([uu.ravel(order="F"), vv.ravel(order="F")])

    texture = load_texture(map_path, w, h)

    return grid, texture


def main():
    parser = argparse.ArgumentParser(description="Real-time 3D heightmap previewer")
    parser.add_argument("directory", help="Directory containing height.png and map.png")
    parser.add_argument(
        "--poll", type=float, default=0.5,
        help="File poll interval in seconds (default: 0.5)"
    )
    args = parser.parse_args()

    height_path = os.path.join(args.directory, "height.png")
    map_path = os.path.join(args.directory, "map.png")

    for path in [height_path, map_path]:
        if not os.path.exists(path):
            print(f"Error: {path} not found")
            sys.exit(1)

    # Initial load
    print(f"Loading from {args.directory}...")
    mesh, texture = load_heightmap(height_path, map_path)

    # Set up the plotter
    plotter = pv.Plotter(title="Heightmap Preview")
    actor = plotter.add_mesh(
        mesh,
        texture=texture,
        show_scalar_bar=False,
        lighting=True,
        smooth_shading=True,
    )
    plotter.add_light(pv.Light(position=(2, 2, 3), intensity=0.8))
    plotter.set_background("black")
    plotter.camera_position = "xz"
    plotter.camera.azimuth = 30
    plotter.camera.elevation = 30

    # WASD fly camera
    move_speed = 0.005
    keys_held = set()

    def on_key_press(obj, event):
        key = obj.GetKeySym().lower()
        keys_held.add(key)

    def on_key_release(obj, event):
        key = obj.GetKeySym().lower()
        keys_held.discard(key)

    plotter.iren.interactor.AddObserver("KeyPressEvent", on_key_press)
    plotter.iren.interactor.AddObserver("KeyReleaseEvent", on_key_release)

    # Disable default VTK keybindings (w=wireframe, s=surface, etc.)
    plotter.enable_terrain_style()
    plotter.iren.interactor.RemoveObservers("CharEvent")

    # Track file modification times
    last_height_mtime = os.path.getmtime(height_path)
    last_map_mtime = os.path.getmtime(map_path)
    last_poll_time = time.time()

    print("Preview running. Edit your heightmap and save -- preview will update.")
    print("Controls: WASD=fly, Space/Shift=up/down, mouse=look, scroll=zoom, q=quit")

    # Use a manual loop instead of VTK timer callbacks -- re-entrant render()
    # calls from timer callbacks deadlock on Linux/X11.
    plotter.show(auto_close=False, interactive_update=True)

    while True:
        if keys_held:
            cam = plotter.camera
            pos = np.array(cam.position)
            focal = np.array(cam.focal_point)
            up = np.array(cam.up)

            forward = focal - pos
            forward = forward / np.linalg.norm(forward)
            right = np.cross(forward, up)
            right = right / np.linalg.norm(right)

            delta = np.zeros(3)
            if "w" in keys_held:
                delta += forward * move_speed
            if "s" in keys_held:
                delta -= forward * move_speed
            if "a" in keys_held:
                delta -= right * move_speed
            if "d" in keys_held:
                delta += right * move_speed
            if "space" in keys_held:
                delta += up * move_speed
            if "shift_l" in keys_held or "shift_r" in keys_held:
                delta -= up * move_speed

            cam.position = pos + delta
            cam.focal_point = focal + delta

            rotate_speed = math.radians(1.0)
            yaw = 0.0
            pitch = 0.0
            if "left" in keys_held:
                yaw -= rotate_speed
            if "right" in keys_held:
                yaw += rotate_speed
            if "up" in keys_held:
                pitch += rotate_speed
            if "down" in keys_held:
                pitch -= rotate_speed

            if yaw != 0.0 or pitch != 0.0:
                world_up = np.array([0.0, 0.0, 1.0])
                fwd = focal - pos + delta
                fwd = fwd / np.linalg.norm(fwd)

                if yaw != 0.0:
                    # Rotate around world up so yaw never introduces roll
                    cos_y, sin_y = math.cos(yaw), math.sin(yaw)
                    fwd = (fwd * cos_y
                           + np.cross(world_up, fwd) * sin_y
                           + world_up * np.dot(world_up, fwd) * (1 - cos_y))
                    fwd /= np.linalg.norm(fwd)

                if pitch != 0.0:
                    # Pitch around the horizontal right vector
                    r = np.cross(fwd, world_up)
                    norm_r = np.linalg.norm(r)
                    if norm_r > 1e-6:
                        r /= norm_r
                        cos_p, sin_p = math.cos(pitch), math.sin(pitch)
                        fwd = fwd * cos_p + np.cross(r, fwd) * sin_p
                        fwd /= np.linalg.norm(fwd)

                cam.focal_point = cam.position + fwd
                cam.up = (0.0, 0.0, 1.0)

        now = time.time()
        if now - last_poll_time >= args.poll:
            last_poll_time = now
            try:
                height_mtime = os.path.getmtime(height_path)
                map_mtime = os.path.getmtime(map_path)
                if height_mtime != last_height_mtime or map_mtime != last_map_mtime:
                    last_height_mtime = height_mtime
                    last_map_mtime = map_mtime
                    print("File changed, reloading...")
                    new_mesh, new_texture = load_heightmap(height_path, map_path)
                    plotter.remove_actor(actor)
                    actor = plotter.add_mesh(
                        new_mesh,
                        texture=new_texture,
                        show_scalar_bar=False,
                        lighting=True,
                        smooth_shading=True,
                    )
                    mesh = new_mesh
                    texture = new_texture
                    print("Updated.")
            except Exception as e:
                print(f"Reload error: {e}")

        try:
            plotter.update(stime=16)  # ~60 fps, processes events and renders
        except Exception:
            break


if __name__ == "__main__":
    main()
