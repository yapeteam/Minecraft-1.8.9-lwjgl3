#!/usr/bin/env python3
"""
Generate patches/net_minecraft.patch from current src/net/minecraft/ changes.
Run this after modifying source, before committing.

Usage:
    python3 gen_patches.py
"""

import sys
import difflib
from pathlib import Path

SCRIPT_DIR = Path(__file__).parent.resolve()
VANILLA    = SCRIPT_DIR / 'decompile' / 'src' / 'minecraft' / 'net' / 'minecraft'
MODIFIED   = SCRIPT_DIR / 'src' / 'net' / 'minecraft'
PATCH      = SCRIPT_DIR / 'patches' / 'net_minecraft.patch'


def read_lines(path: Path):
    """Read file, normalize to LF, return list of lines with endings."""
    return path.read_bytes().replace(b'\r\n', b'\n').replace(b'\r', b'\n') \
               .decode('utf-8', errors='replace').splitlines(keepends=True)


def diff_trees(a_root: Path, b_root: Path):
    """Yield unified diff chunks for all changed/added/deleted files."""
    a_files = {f.relative_to(a_root) for f in a_root.rglob('*.java')}
    b_files = {f.relative_to(b_root) for f in b_root.rglob('*.java')}

    for rel in sorted(a_files | b_files):
        a_path = a_root / rel
        b_path = b_root / rel

        a_lines = read_lines(a_path) if a_path.exists() else []
        b_lines = read_lines(b_path) if b_path.exists() else []

        if a_lines == b_lines:
            continue

        rel_posix = rel.as_posix()
        diff = list(difflib.unified_diff(
            a_lines, b_lines,
            fromfile=f'a/src/net/minecraft/{rel_posix}',
            tofile=f'b/src/net/minecraft/{rel_posix}',
        ))
        if diff:
            yield f'diff -ruN a/src/net/minecraft/{rel_posix} b/src/net/minecraft/{rel_posix}\n'
            yield from diff


def main():
    if not VANILLA.exists():
        print("ERROR: Vanilla source not found at decompile/src/minecraft/")
        print("Run setup first:  python3 setup.py  (or: mvn compile)")
        sys.exit(1)

    if not MODIFIED.exists():
        print("ERROR: Modified source not found at src/net/minecraft/")
        sys.exit(1)

    chunks = list(diff_trees(VANILLA, MODIFIED))
    PATCH.write_text(''.join(chunks), encoding='utf-8')

    file_count = sum(1 for c in chunks if c.startswith('diff -ruN'))
    print(f"Patch updated: {file_count} files changed -> patches/net_minecraft.patch")


if __name__ == '__main__':
    main()
