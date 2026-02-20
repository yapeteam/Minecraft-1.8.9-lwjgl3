# Minecraft-1.8.9-lwjgl3

A project focused on upgrading **MCP 1.8.9** to use **LWJGL 3**.

---

## Introduction

This project aims to provide a modern rendering backend for Minecraft 1.8.9 by integrating LWJGL 3.

> [!IMPORTANT]
> This project incorporates code and inspiration from:
> - [lwjgl3ify](https://github.com/GTNewHorizons/lwjgl3ify) by GTNewHorizons
> - [MCLWJGL3](https://github.com/Verschwiegener/MCLWJGL3) by Verschwiegener

> [!NOTE]
> Minecraft source code is **not included** in this repository. It is decompiled locally on your machine using the embedded [MCP](https://github.com/Marcelektro/MCP-919) toolchain, then patched automatically. This complies with Mojang's terms of use.

---

## Prerequisites

- Java 8
- Maven
- Python 3
- Git
- `astyle` (for MCP source formatting — Linux: `sudo pacman -S astyle` / `sudo apt install astyle`, macOS/Windows: bundled in MCP)

---

## Getting Started

**1. Clone the repository**

```bash
git clone https://github.com/yapeteam/minecraft-1.8.9-lwjgl3
cd Minecraft-1.8.9-lwjgl3
```

**2. Copy your Minecraft 1.8.9 client jar**

```bash
mkdir decompile/jars
mkdir run

# Linux / macOS
cp ~/.minecraft/versions/1.8.9/1.8.9.jar decompile/jars/minecraft.jar
cp ~/.minecraft/assets run/

# Windows
copy %APPDATA%\.minecraft\versions\1.8.9\1.8.9.jar decompile\jars\minecraft.jar
copy %APPDATA%\.minecraft\assets run\
```

**3. Build** — decompile and patch run automatically on first build

```bash
mvn compile
```

**4. Run**

```bash
mvn compile exec:exec
```

---

## Development Workflow

```
clone → init → modify → gen_patches → commit
```

**After making changes to `src/net/minecraft/`**, regenerate the patch before committing:

```bash
# Linux / macOS
python3 gen_patches.py

# Windows
python gen_patches.py
```

Then commit `patches/net_minecraft.patch` along with any other changed files.

> [!TIP]
> `src/net/minecraft/` is git-ignored. Only `patches/net_minecraft.patch` and non-MC source files are tracked.

---

## Project Structure

```
├── decompile/          MCP toolchain (conf/, runtime/, jars/)
│   └── jars/           Put minecraft.jar here
├── patches/            Patch file applied over vanilla MCP source
├── src/
│   ├── net/minecraft/  Generated — do not commit (git-ignored)
│   ├── org/lwjglx/     LWJGL2 compatibility layer
│   └── ...             Other project sources
├── setup.py            Decompile + apply patches (auto-run by Maven)
└── gen_patches.py      Regenerate patches from current source
```

---

## Known Issues

- **Window Icon:** Currently unable to set the window icon. (Investigation/Debugging in progress)

---

## Contributing

Feel free to open an issue or PR.
