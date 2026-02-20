#!/usr/bin/env python3
"""
Decompile Minecraft 1.8.9 and apply lwjgl3 patches.

Prerequisite: copy minecraft 1.8.9 client jar to decompile/jars/minecraft.jar
Also requires:  python3, astyle (system), git

Usage:
    python3 setup.py          # or: mvn compile (auto-triggered on first run)
"""

import sys
import shutil
import subprocess
from pathlib import Path

SCRIPT_DIR = Path(__file__).parent.resolve()
MCP_DIR    = SCRIPT_DIR / 'decompile'
JAR        = MCP_DIR / 'jars' / 'minecraft.jar'
MCP_SRC    = MCP_DIR / 'src' / 'minecraft'
MC_SRC     = SCRIPT_DIR / 'src' / 'net' / 'minecraft'
PATCH      = SCRIPT_DIR / 'patches' / 'net_minecraft.patch'


def run(*args, cwd=None):
    subprocess.run(list(args), cwd=str(cwd or SCRIPT_DIR), check=True)


def copy_normalized(src: Path, dst: Path):
    """Copy a Java source tree, normalizing line endings to LF."""
    if dst.exists():
        shutil.rmtree(dst)
    dst.mkdir(parents=True)
    for src_file in src.rglob('*'):
        rel = src_file.relative_to(src)
        dst_file = dst / rel
        if src_file.is_dir():
            dst_file.mkdir(parents=True, exist_ok=True)
        else:
            text = src_file.read_bytes().replace(b'\r\n', b'\n').replace(b'\r', b'\n')
            dst_file.write_bytes(text)


def main():
    # Already set up — skip entirely
    if MC_SRC.exists():
        print("src/net/minecraft/ already exists, skipping setup.")
        print("Delete it to force re-setup.")
        return

    # 1. Check jar
    if not JAR.exists():
        print(f"ERROR: minecraft.jar not found at {JAR.relative_to(SCRIPT_DIR)}")
        print("Copy your Minecraft 1.8.9 client jar there, then re-run.")
        sys.exit(1)

    # 2. Decompile
    if MCP_SRC.exists():
        print("[1/3] decompile/src/ already exists, skipping decompile.")
    else:
        print("[1/3] Decompiling Minecraft 1.8.9 (this takes a few minutes)...")
        run(sys.executable, 'runtime/decompile.py',
            '--client', '--norecompile', '--nocopy',
            cwd=MCP_DIR)

    # 3. Copy vanilla source (LF-normalized)
    print("[2/3] Copying vanilla source...")
    copy_normalized(MCP_SRC / 'net' / 'minecraft', MC_SRC)

    # 4. Apply patches
    print("[3/3] Applying patches...")
    try:
        run('git', 'apply', '--whitespace=nowarn', str(PATCH))
    except subprocess.CalledProcessError:
        # Roll back the partial copy so the next run retries from scratch
        if MC_SRC.exists():
            shutil.rmtree(MC_SRC)
        print("\nERROR: git apply failed. Partial source has been removed.", file=sys.stderr)
        print("Ensure astyle is installed and decompile/src/ is clean, then retry.", file=sys.stderr)
        sys.exit(1)

    print("\nDone. Run: mvn compile")


if __name__ == '__main__':
    main()
