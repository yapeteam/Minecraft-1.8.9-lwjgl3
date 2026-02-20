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
> Minecraft source code is **not included** in this repository. It is decompiled locally on your
> machine using the embedded MCP toolchain (`decompile/`), then patched automatically.
> This complies with Mojang's terms of use.

---

## Prerequisites

| Tool | Version | Notes |
|------|---------|-------|
| Java | 8 | Must be on `PATH` |
| Maven | any | Must be on `PATH` |
| Python | 3.8+ | Must be on `PATH` as `python3` (Linux/macOS) or `python` (Windows) |
| Git | any | Required to clone and apply patches |
| `astyle` | any | Source formatter — **required** for consistent code formatting |

Install `astyle`:
- **Linux (Arch):** `sudo pacman -S astyle`
- **Linux (Debian/Ubuntu):** `sudo apt install astyle`
- **macOS:** `brew install astyle`
- **Windows:** download from [astyle.sourceforge.net](https://astyle.sourceforge.net/) and add to `PATH`

---

## Getting Started

**1. Clone the repository**

```bash
git clone https://github.com/yapeteam/Minecraft-1.8.9-lwjgl3
cd Minecraft-1.8.9-lwjgl3
```

**2. Copy your Minecraft 1.8.9 client jar**

```bash
# Linux / macOS
cp ~/.minecraft/versions/1.8.9/1.8.9.jar decompile/jars/minecraft.jar

# Windows
copy %APPDATA%\.minecraft\versions\1.8.9\1.8.9.jar decompile\jars\minecraft.jar
```

**3. Build** — decompile and patch run automatically on first build

```bash
mvn compile
```

> [!TIP]
> The first build takes several minutes because it runs SpecialSource, MCInjector,
> Fernflower, and all post-processing steps. Subsequent builds are instant.

**4. Set up the run directory and launch**

```bash
mkdir -p run

# Linux / macOS — copy assets from your local Minecraft installation
cp -r ~/.minecraft/assets run/

# Windows
xcopy /E /I %APPDATA%\.minecraft\assets run\assets
```

```bash
mvn compile exec:exec
```

---

## Development Workflow

```
clone → build (auto-decompile) → modify → gen_patches → commit
```

**After making changes to `src/net/minecraft/`**, regenerate the patch before committing:

```bash
# Linux / macOS
python3 gen_patches.py

# Windows
python gen_patches.py
```

Then commit `patches/net_minecraft.patch` together with any other modified files.

> [!TIP]
> `src/net/minecraft/` is git-ignored. Only `patches/net_minecraft.patch` and
> non-MC source files (e.g. `src/org/lwjglx/`) are tracked.

---

## Project Structure

```
├── decompile/              MCP toolchain (embedded, Python 3)
│   ├── conf/               MCP mappings, patches, astyle config
│   │   └── patches/
│   │       └── minecraft_ff/   Per-file Fernflower fix patches
│   ├── jars/               Put minecraft.jar here (git-ignored)
│   └── runtime/
│       ├── bin/            Java tools (specialsource, mcinjector, fernflower)
│       └── decompile.py    Python 3 decompile driver (replaces MCP Python 2)
├── patches/
│   └── net_minecraft.patch LWJGL3 patch applied over vanilla MCP source
├── src/
│   ├── net/minecraft/      Generated — do not commit (git-ignored)
│   ├── org/lwjglx/         LWJGL2 compatibility shim
│   └── ...                 Other project sources (Start.java, etc.)
├── setup.py                Auto-decompile + patch (triggered by mvn compile)
├── gen_patches.py          Regenerate patches from current src/net/minecraft/
└── pom.xml
```

---

## How the Decompile Works

`setup.py` is invoked automatically during `mvn compile` (via `exec-maven-plugin`).
It runs only once — subsequent builds skip this step if `src/net/minecraft/` already exists.

Pipeline inside `decompile/runtime/decompile.py`:

1. **SpecialSource** — deobfuscate class names using `conf/joined.srg`
2. **MCInjector** — inject exception info and parameter names (`conf/joined.exc`)
3. **Fernflower** — decompile bytecode to Java source
4. **fffix** — clean up common Fernflower output quirks
5. **FF patches** — apply `conf/patches/minecraft_ff/*.patch`
6. **Cleanup** — strip comments, fix imports, normalise PI/GL constants
7. **AStyle** — reformat source (skipped gracefully if `astyle` is not installed)
8. **Rename** — replace `func_`/`field_`/`p_` srg names with MCP names

After decompilation, `setup.py` copies `net/minecraft/` into `src/net/minecraft/`
and applies `patches/net_minecraft.patch` (the LWJGL3 migration patch).

---

## Known Issues

- **Window Icon:** Currently unable to set the window icon. (Investigation in progress)

---

## Contributing

Feel free to open an issue or PR.
