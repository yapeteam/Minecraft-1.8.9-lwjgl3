# Minecraft-1.8.9-lwjgl3

A project focused on upgrading **MCP 1.8.9** to use **LWJGL 3**.

---

## Introduction

This project aims to provide a modern rendering backend for Minecraft 1.8.9 by integrating LWJGL 3.

> [!IMPORTANT]
> This project incorporates code and inspiration from:
> - [lwjgl3ify](https://github.com/GTNewHorizons/lwjgl3ify) by GTNewHorizons
> - [MCLWJGL3](https://github.com/Verschwiegener/MCLWJGL3) by Verschwiegener

---

## Getting Started


**1. Set up the run directory**

```bash
mkdir run

# Linux / macOS — copy assets from your local Minecraft installation
cp -r ~/.minecraft/assets run/

# Windows
xcopy /E /I %APPDATA%\.minecraft\assets run\assets
```

**2. Compile and launch**

```bash
mvn compile exec:exec
```

---

## Known Issues

- **Window Icon:** Currently unable to set the window icon. (Investigation in progress)

---

## Contributing

Feel free to open an issue or PR.
