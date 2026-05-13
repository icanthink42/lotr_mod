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
    - Left drag:   rotate
    - Right drag:  pan
    - Scroll:      zoom
    - q:           quit

Dependencies:
    pip install polyscope numpy Pillow
"""

import argparse
import os
import sys
import time

import numpy as np
import polyscope as ps
from PIL import Image

SEA_LEVEL = 62.0        # blocks
HEIGHT_MAP_PEAK = 800.0 # blocks
MAP_SCALE = 32.0        # blocks per map pixel at original resolution


def build_faces(h, w):
    """Build triangle face indices for an h x w vertex grid."""
    idx = np.arange(h * w).reshape(h, w)
    tl = idx[:-1, :-1].ravel()
    tr = idx[:-1, 1:].ravel()
    bl = idx[1:, :-1].ravel()
    br = idx[1:, 1:].ravel()
    return np.concatenate(
        [
            np.stack([tl, tr, br], axis=1),
            np.stack([tl, br, bl], axis=1),
        ]
    )


def load_mesh(height_path, map_path):
    img = Image.open(height_path).convert("L")
    arr = np.array(img, dtype=np.float32)
    orig_h, orig_w = arr.shape

    max_dim = 1024
    if max(orig_h, orig_w) > max_dim:
        factor = max_dim / max(orig_h, orig_w)
        img = img.resize((int(orig_w * factor), int(orig_h * factor)), Image.BILINEAR)
        arr = np.array(img, dtype=np.float32)
    h, w = arr.shape

    # blocks per pixel accounts for downsampling
    bpp_x = (orig_w / w) * MAP_SCALE
    bpp_z = (orig_h / h) * MAP_SCALE

    normalized = arr / 255.0
    zz = SEA_LEVEL + normalized * (HEIGHT_MAP_PEAK - SEA_LEVEL)  # block Y

    x = np.arange(w) * bpp_x
    y = np.arange(h) * bpp_z
    xx, yy = np.meshgrid(x, y)

    yy = yy[::-1]  # flip Y so north is up

    vertices = np.stack([xx.ravel(), yy.ravel(), zz.ravel()], axis=1)

    color_img = Image.open(map_path).convert("RGB")
    color_img = color_img.resize((w, h), Image.BILINEAR)
    colors = np.array(color_img, dtype=np.float32) / 255.0
    vertex_colors = colors.reshape(-1, 3)

    return vertices, vertex_colors, h, w


def main():
    parser = argparse.ArgumentParser(description="Real-time 3D heightmap previewer")
    parser.add_argument("directory", help="Directory containing height.png and map.png")
    parser.add_argument(
        "--poll",
        type=float,
        default=0.5,
        help="File poll interval in seconds (default: 0.5)",
    )
    args = parser.parse_args()

    height_path = os.path.join(args.directory, "height.png")
    map_path = os.path.join(args.directory, "map.png")

    for path in [height_path, map_path]:
        if not os.path.exists(path):
            print(f"Error: {path} not found")
            sys.exit(1)

    print(f"Loading from {args.directory}...")
    vertices, colors, h, w = load_mesh(height_path, map_path)
    faces = build_faces(h, w)

    ps.init()
    ps.set_up_dir("z_up")
    ps.set_ground_plane_mode("none")

    mesh = ps.register_surface_mesh("heightmap", vertices, faces, smooth_shade=True)
    mesh.add_color_quantity("color", colors, enabled=True)

    state = {
        "mesh": mesh,
        "h": h,
        "w": w,
        "faces": faces,
        "last_height_mtime": os.path.getmtime(height_path),
        "last_map_mtime": os.path.getmtime(map_path),
        "last_poll_time": time.time(),
    }

    def callback():
        now = time.time()
        if now - state["last_poll_time"] < args.poll:
            return
        state["last_poll_time"] = now
        try:
            height_mtime = os.path.getmtime(height_path)
            map_mtime = os.path.getmtime(map_path)
            if (
                height_mtime == state["last_height_mtime"]
                and map_mtime == state["last_map_mtime"]
            ):
                return
            state["last_height_mtime"] = height_mtime
            state["last_map_mtime"] = map_mtime
            print("File changed, reloading...")
            new_verts, new_colors, new_h, new_w = load_mesh(height_path, map_path)
            if new_h != state["h"] or new_w != state["w"]:
                state["h"], state["w"] = new_h, new_w
                state["faces"] = build_faces(new_h, new_w)
                state["mesh"] = ps.register_surface_mesh(
                    "heightmap", new_verts, state["faces"], smooth_shade=True
                )
            else:
                state["mesh"].update_vertex_positions(new_verts)
            state["mesh"].add_color_quantity("color", new_colors, enabled=True)
            print("Updated.")
        except Exception as e:
            print(f"Reload error: {e}")

    print("Preview running. Edit your heightmap and save -- preview will update.")
    ps.set_user_callback(callback)
    ps.show()


if __name__ == "__main__":
    main()
