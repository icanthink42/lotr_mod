# LOTR Craft

Fabric mod for Minecraft Java Edition 26.1.2.

## Requirements

- Java 25 JDK
- `curl` and `unzip`
- A Fabric 26.1.2 Minecraft instance, such as MultiMC or Prism Launcher

## Setup

Create a `.env` file in the project root with the path to your instance's `mods` folder:

```sh
export_path="/home/jack/.local/share/multimc/instances/lotr/.minecraft/mods/"
```

Use your own path if your launcher instance is somewhere else.

## Build And Export

Run:

```sh
./build.sh
```

The script will:

- Load `export_path` from `.env`
- Use Java 25
- Build the mod with Gradle
- Copy the built jar into the configured `mods` folder

After it finishes, launch the Minecraft instance with Fabric Loader.

## Fabric 26.1 Notes

Minecraft 26.1 is unobfuscated. This project uses Fabric's 26.1 Loom plugin:

```gradle
id 'net.fabricmc.fabric-loom'
```

There is no `mappings` dependency in `build.gradle` for 26.1.
