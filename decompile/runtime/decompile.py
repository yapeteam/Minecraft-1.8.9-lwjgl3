#!/usr/bin/env python3
"""
Python 3 standalone decompile script for Minecraft 1.8.9 using MCP-919 tools.
Replaces the Python 2 MCP runtime (decompile.py + commands.py).

Usage (from decompile/ directory):
    python3 runtime/decompile.py --client [--norecompile] [--nocopy]

Options --norecompile and --nocopy are accepted but ignored (kept for CLI
compatibility with setup.py).
"""

import re
import csv
import sys
import os
import shutil
import zipfile
import fnmatch
import subprocess
import argparse
from pathlib import Path
from textwrap import TextWrapper

# ── Directory layout (relative to decompile/) ────────────────────────────────
_HERE    = Path(__file__).parent.parent.resolve()   # decompile/
CONF     = _HERE / 'conf'
RUNTIME  = _HERE / 'runtime'
JARS     = _HERE / 'jars'
TEMP     = _HERE / 'temp'
SRC      = _HERE / 'src'
LOGS     = _HERE / 'logs'

# ── Java tool jars ────────────────────────────────────────────────────────────
SS_JAR   = RUNTIME / 'bin' / 'specialsource.jar'
MCI_JAR  = RUNTIME / 'bin' / 'mcinjector.jar'
FF_JAR   = RUNTIME / 'bin' / 'fernflower.jar'

# ── Client file paths ─────────────────────────────────────────────────────────
MC_JAR      = JARS / 'minecraft.jar'
RG_JAR      = TEMP / 'minecraft_rg.jar'
EXC_JAR     = TEMP / 'minecraft_exc.jar'
FF_IN       = TEMP / 'minecraft_ff_in.jar'
FF_EXTRA    = TEMP / 'minecraft_ff_extra.jar'
FF_OUT      = TEMP / 'minecraft_ff_out.jar'
FF_TEMP_SRC = TEMP / 'src' / 'minecraft'   # Fernflower output dir
SRC_CLIENT  = SRC  / 'minecraft'           # Final renamed source

# ── Config / mapping files ────────────────────────────────────────────────────
JOINED_SRG    = CONF / 'joined.srg'
CLIENT_RG_SRG = TEMP / 'client_rg.srg'
JOINED_EXC    = CONF / 'joined.exc'
EXCEPTOR_JSON = CONF / 'exceptor.json'
ASTYLE_CFG    = CONF / 'astyle.cfg'
FF_PATCHES    = CONF / 'patches' / 'minecraft_ff'
CSV_METHODS   = CONF / 'methods.csv'
CSV_FIELDS    = CONF / 'fields.csv'
CSV_PARAMS    = CONF / 'params.csv'
EXC_LOG       = LOGS / 'client_exc.log'
START_JAVA    = CONF / 'patches' / 'Start.java'

# Packages excluded from Fernflower decompilation
IGNORE_PKG = [
    'paulscode', 'com', 'isom', 'ibxm',
    'de/matthiasmann/twl', 'org', 'javax',
    'argo', 'gnu', 'io/netty', 'oshi',
]


# ── Helpers ───────────────────────────────────────────────────────────────────

def step(msg):
    print(f'\n=== {msg} ===', flush=True)


def run(cmd, cwd=None):
    """Print and execute a command list; raise CalledProcessError on failure."""
    print(' '.join(str(c) for c in cmd), flush=True)
    subprocess.run([str(c) for c in cmd], check=True, cwd=cwd)


def _is_ignored(filename):
    """Return True if a JAR entry belongs to an ignored package."""
    for pkg in IGNORE_PKG:
        if filename.startswith(pkg + '/') or filename == pkg:
            return True
    return False


# ── Pipeline steps ────────────────────────────────────────────────────────────

def createsrgs():
    step('Step 1/13 – Create SRGs')
    TEMP.mkdir(parents=True, exist_ok=True)
    LOGS.mkdir(parents=True, exist_ok=True)
    shutil.copy(JOINED_SRG, CLIENT_RG_SRG)
    print(f'  Copied joined.srg -> {CLIENT_RG_SRG.relative_to(_HERE)}')


def applyss():
    step('Step 2/13 – SpecialSource (deobfuscate)')
    run([
        'java', '-cp', str(SS_JAR), '-jar', str(SS_JAR),
        '-i', str(MC_JAR), '-o', str(RG_JAR),
        '-m', str(CLIENT_RG_SRG),
        '--kill-source', '--kill-lvt', '--kill-generics',
    ])


def applyexceptor():
    step('Step 3/13 – MCInjector (exceptions + parameter names)')
    run([
        'java', '-jar', str(MCI_JAR),
        '--jarIn',  str(RG_JAR),
        '--jarOut', str(EXC_JAR),
        '--mapIn',  str(JOINED_EXC),
        '--log',    str(EXC_LOG),
        '--applyMarkers', '--generateParams', '--lvt=LVT',
        '--json',   str(EXCEPTOR_JSON),
    ])


def filterffjar():
    step('Step 4/13 – Filter jar for Fernflower')
    if FF_IN.exists():
        FF_IN.unlink()
    with zipfile.ZipFile(EXC_JAR) as zin, \
         zipfile.ZipFile(FF_IN,    'w', zipfile.ZIP_DEFLATED) as zout, \
         zipfile.ZipFile(FF_EXTRA, 'w', zipfile.ZIP_DEFLATED) as zextra:
        for info in zin.infolist():
            data = zin.read(info.filename)
            if _is_ignored(info.filename):
                zextra.writestr(info, data)
            else:
                zout.writestr(info, data)
    print(f'  Written {FF_IN.name} and {FF_EXTRA.name}')


def applyff():
    step('Step 5/13 – Fernflower (decompile to Java)')
    if FF_TEMP_SRC.exists():
        shutil.rmtree(FF_TEMP_SRC)
    FF_TEMP_SRC.mkdir(parents=True)

    # Pass FF_EXTRA as an external classpath reference so Fernflower can
    # resolve cross-package type references from the MC jar itself.
    run([
        'java', '-jar', str(FF_JAR),
        '-din=1', '-rbr=1', '-dgs=1', '-asc=1',
        '-rsy=1', '-iec=1', '-jvn=1', '-log=WARN',
        f'-e={FF_EXTRA}',
        str(FF_IN), str(FF_TEMP_SRC),
    ])

    # Fernflower outputs a JAR named after the input inside the output dir
    jar_out = FF_TEMP_SRC / FF_IN.name
    shutil.move(str(jar_out), str(FF_OUT))

    print('  Extracting Fernflower output jar...')
    with zipfile.ZipFile(FF_OUT) as z:
        z.extractall(FF_TEMP_SRC)


def copysrc():
    step('Step 6/13 – Copy source to src/minecraft/')
    SRC_CLIENT.mkdir(parents=True, exist_ok=True)
    copied = 0
    for src_file in FF_TEMP_SRC.rglob('*.java'):
        # Skip ignored packages
        rel = src_file.relative_to(FF_TEMP_SRC)
        if _is_ignored(rel.parts[0]):
            continue
        dst_file = SRC_CLIENT / rel
        dst_file.parent.mkdir(parents=True, exist_ok=True)
        # Normalize line endings to LF
        text = src_file.read_bytes().replace(b'\r\n', b'\n').replace(b'\r', b'\n')
        dst_file.write_bytes(text)
        copied += 1

    # Also copy Start.java provided by MCP (launcher entry point)
    if START_JAVA.exists():
        dst = SRC_CLIENT / 'Start.java'
        text = START_JAVA.read_bytes().replace(b'\r\n', b'\n').replace(b'\r', b'\n')
        dst.write_bytes(text)

    print(f'  Copied {copied} Java files')


# ── Post-processing helpers ───────────────────────────────────────────────────

_RE_NORMLINES  = re.compile(r'^\n{2,}', re.MULTILINE)
_RE_ABSTRACT   = re.compile(
    r' (?P<method>func_(?P<number>\d+)_[a-zA-Z_]+)'
    r'\((?P<arguments>(?:(?:[^ ,]+(?:<.*>)?(?:\s\.\.\.)?) var\d+(?:,\s)?)+)\)'
    r'(?:\s+throws\s+(?:[\w$.]+,?\s?)+)?;$'
)
_RE_PARAMS_VAR = re.compile(
    r'(?P<type>(?:[^ ,])+(?:<.*>)?(?:\s\.\.\.)?) var(?P<id>\d+)(?P<end>,? )?'
)


def _fffix_data(text, class_name):
    """Port of pylibs/fffix.py _process_data – Python 3."""
    text = text.replace('\r\n', '\n').replace('\r', '\n')
    lines = text.split('\n')
    for idx, line in enumerate(lines):
        line_s = line.strip()
        if line_s == 'super();':
            line = ''
        if line.endswith(';'):
            def _abstract_sub(m):
                args = m.group('arguments')
                num  = m.group('number')
                args = _RE_PARAMS_VAR.sub(
                    lambda mm: '{} p_{}_{}_{}'.format(
                        mm.group('type'), num, mm.group('id'),
                        mm.group('end') if mm.group('end') else ''
                    ),
                    args,
                )
                return m.group(0).replace(m.group('arguments'), args)
            line = _RE_ABSTRACT.sub(_abstract_sub, line)
        lines[idx] = line.rstrip()
    text = '\n'.join(lines)
    text = _RE_NORMLINES.sub('\n', text)
    return text


def process_fffixes():
    step('Step 7/13 – fffix (clean Fernflower output)')
    fixed = 0
    for src_file in SRC_CLIENT.rglob('*.java'):
        class_name = src_file.stem
        orig = src_file.read_text(encoding='utf-8', errors='replace')
        new  = _fffix_data(orig, class_name)
        if new != orig:
            src_file.write_text(new, encoding='utf-8')
            fixed += 1
    print(f'  Fixed {fixed} files')


def _apply_single_patch(patch_path, target_dir):
    """
    Apply one unified-diff patch file using pure Python.
    Normalises Windows backslashes in path headers and strips one path
    component (equivalent to patch -p1).
    """
    with open(patch_path, 'r', errors='replace') as fh:
        lines = fh.readlines()

    i = 0
    while i < len(lines):
        line = lines[i]

        # Locate the '--- ' header
        if not line.startswith('--- '):
            i += 1
            continue

        raw_path = line[4:].split('\t')[0].strip().replace('\\', '/')
        parts    = raw_path.split('/')
        rel_path = '/'.join(parts[1:])          # strip first component (-p1)
        target   = Path(target_dir) / rel_path

        if not target.exists():
            i += 1
            continue

        i += 1  # skip --- line
        if i >= len(lines) or not lines[i].startswith('+++ '):
            continue
        i += 1  # skip +++ line

        # Collect all hunks
        hunks = []
        while i < len(lines) and lines[i].startswith('@@'):
            m = re.match(r'@@ -(\d+)(?:,\d+)? \+(\d+)(?:,\d+)? @@', lines[i])
            if not m:
                i += 1
                continue
            from_start = int(m.group(1))
            i += 1
            hunk = []
            while i < len(lines) and not lines[i].startswith(('@@', '---', 'diff ')):
                hunk.append(lines[i])
                i += 1
            hunks.append((from_start, hunk))

        # Apply hunks to file content
        content = target.read_text(encoding='utf-8', errors='replace').split('\n')
        # Apply in reverse order so earlier hunks don't shift later offsets
        for from_start, hunk in reversed(hunks):
            ctx_idx      = from_start - 1   # 0-based
            out_lines    = []
            src_idx      = ctx_idx
            src_consumed = 0
            for hline in hunk:
                if not hline:
                    continue
                prefix = hline[0]
                body   = hline[1:].rstrip('\n')
                if prefix == ' ':
                    out_lines.append(content[src_idx] if src_idx < len(content) else body)
                    src_idx      += 1
                    src_consumed += 1
                elif prefix == '-':
                    src_idx      += 1
                    src_consumed += 1
                elif prefix == '+':
                    out_lines.append(body)
                else:
                    out_lines.append(content[src_idx] if src_idx < len(content) else body)
                    src_idx      += 1
                    src_consumed += 1
            content[ctx_idx:ctx_idx + src_consumed] = out_lines

        target.write_text('\n'.join(content), encoding='utf-8')


def applypatches():
    step('Step 8/13 – Apply FF patches')
    patches = sorted(FF_PATCHES.glob('*.patch'))
    print(f'  Applying {len(patches)} FF patches...')
    for p in patches:
        _apply_single_patch(p, SRC_CLIENT)
    print('  Done')


# ── cleanup_src (port of pylibs/cleanup_src.py) ───────────────────────────────

_RE_COMMENTS    = re.compile(
    r'//.*?$|/\*.*?\*/|\'(?:\\.|[^\\\'])*\'|"(?:\\.|[^\\"])*"',
    re.MULTILINE | re.DOTALL,
)
_RE_CL_HEADER     = re.compile(r'^\s+')
_RE_CL_FOOTER     = re.compile(r'\s+$')
_RE_CL_TRAILING   = re.compile(r'[ \t]+$', re.MULTILINE)
_RE_CL_PACKAGE    = re.compile(r'^package (?P<pkg>[\w.]+);$', re.MULTILINE)
_RE_CL_IMPORT     = re.compile(r'^import (?:(?P<pkg>[\w.]*?)\.)?(?P<cls>[\w]+);\n', re.MULTILINE)
_RE_CL_NEWLINES   = re.compile(r'^\n{2,}', re.MULTILINE)
_RE_CL_BLOCKSTART = re.compile(r'(?<={)\s+(?=\n[ \t]*\S)', re.MULTILINE)
_RE_CL_BLOCKEND   = re.compile(r'(?<=[;}])\s+(?=\n\s*})', re.MULTILINE)
_RE_CL_GL         = re.compile(r'\s*/\*\s*GL_[^*]+\*/\s*')
_RE_CL_UNICODE    = re.compile(r"'\\u([0-9a-fA-F]{4})'")
_RE_CL_CHARVAL    = re.compile(r"Character\.valueOf\(('.')\)")
_RE_CL_MAXD       = re.compile(r'1\.7976[0-9]*[Ee]\+308[Dd]')
_RE_CL_PID        = re.compile(r'3\.1415[0-9]*[Dd]')
_RE_CL_PIF        = re.compile(r'3\.1415[0-9]*[Ff]')
_RE_CL_2PID       = re.compile(r'6\.2831[0-9]*[Dd]')
_RE_CL_2PIF       = re.compile(r'6\.2831[0-9]*[Ff]')
_RE_CL_PI2D       = re.compile(r'1\.5707[0-9]*[Dd]')
_RE_CL_PI2F       = re.compile(r'1\.5707[0-9]*[Ff]')
_RE_CL_3PI2D      = re.compile(r'4\.7123[0-9]*[Dd]')
_RE_CL_3PI2F      = re.compile(r'4\.7123[0-9]*[Ff]')
_RE_CL_PI4D       = re.compile(r'0\.7853[0-9]*[Dd]')
_RE_CL_PI4F       = re.compile(r'0\.7853[0-9]*[Ff]')
_RE_CL_180PID     = re.compile(r'57\.295[0-9]*[Dd]')
_RE_CL_180PIF     = re.compile(r'57\.295[0-9]*[Ff]')


def process_comments():
    step('Step 9/13 – Strip comments')

    def _comment_replacer(m):
        return '' if m.group(0).startswith('/') else m.group(0)

    for f in SRC_CLIENT.rglob('*.java'):
        buf = f.read_text(encoding='utf-8', errors='replace')
        buf = _RE_COMMENTS.sub(_comment_replacer, buf)
        buf = _RE_CL_TRAILING.sub('', buf)
        buf = _RE_CL_NEWLINES.sub('\n', buf)
        f.write_text(buf, encoding='utf-8')


def process_cleanup():
    step('Step 10/13 – Source cleanup')

    def _unicode_replacer(m):
        value = int(m.group(1), 16)
        return str(value) if value > 255 else m.group(0)

    for f in SRC_CLIENT.rglob('*.java'):
        buf = f.read_text(encoding='utf-8', errors='replace')

        # Remove same-package imports
        pkg_m = _RE_CL_PACKAGE.search(buf)
        if pkg_m:
            pkg = pkg_m.group('pkg')
            def _import_replacer(m, _pkg=pkg):
                return '' if m.group('pkg') == _pkg else m.group(0)
            buf = _RE_CL_IMPORT.sub(_import_replacer, buf)

        buf = _RE_CL_HEADER.sub('', buf, count=1)
        buf = _RE_CL_FOOTER.sub('\n', buf)
        buf = _RE_CL_TRAILING.sub('', buf)
        buf = _RE_CL_NEWLINES.sub('\n', buf)
        buf = _RE_CL_BLOCKSTART.sub('', buf)
        buf = _RE_CL_BLOCKEND.sub('', buf)
        buf = _RE_CL_GL.sub('', buf)
        buf = _RE_CL_MAXD.sub('Double.MAX_VALUE', buf)
        buf = _RE_CL_UNICODE.sub(_unicode_replacer, buf)
        buf = _RE_CL_CHARVAL.sub(r'\1', buf)
        buf = _RE_CL_PID.sub('Math.PI', buf)
        buf = _RE_CL_PIF.sub('(float)Math.PI', buf)
        buf = _RE_CL_2PID.sub('(Math.PI * 2D)', buf)
        buf = _RE_CL_2PIF.sub('((float)Math.PI * 2F)', buf)
        buf = _RE_CL_PI2D.sub('(Math.PI / 2D)', buf)
        buf = _RE_CL_PI2F.sub('((float)Math.PI / 2F)', buf)
        buf = _RE_CL_3PI2D.sub('(Math.PI * 3D / 2D)', buf)
        buf = _RE_CL_3PI2F.sub('((float)Math.PI * 3F / 2F)', buf)
        buf = _RE_CL_PI4D.sub('(Math.PI / 4D)', buf)
        buf = _RE_CL_PI4F.sub('((float)Math.PI / 4F)', buf)
        buf = _RE_CL_180PID.sub('(180D / Math.PI)', buf)
        buf = _RE_CL_180PIF.sub('(180F / (float)Math.PI)', buf)
        f.write_text(buf, encoding='utf-8')


# ── annotate_gl_constants (port) ──────────────────────────────────────────────

def _load_gl_constants():
    """
    Load GL annotation data from annotate_gl_constants.py without importing it
    (the file contains Python 2 print>> syntax in main() which fails on Py3).
    Executes only the data-definition portion in a sandboxed namespace.
    Returns (_CONSTANTS, _PACKAGES, _CALL_REGEX, _CONSTANT_REGEX) or empty on failure.
    """
    src_path = RUNTIME / 'pylibs' / 'annotate_gl_constants.py'
    if not src_path.exists():
        return [], [], None, None
    src = src_path.read_text(encoding='utf-8', errors='replace')
    # Strip Python-2-only main() and __main__ guard
    src = re.sub(r'\ndef main\(\).*', '', src, flags=re.DOTALL)
    src = re.sub(r'\nif __name__.*', '', src, flags=re.DOTALL)
    ns = {'re': re, 'sys': sys, 'shutil': shutil, 'os': os, 'fnmatch': fnmatch}
    try:
        exec(compile(src, str(src_path), 'exec'), ns)
        return (
            ns.get('_CONSTANTS', []),
            ns.get('_PACKAGES', []),
            ns.get('_CALL_REGEX'),
            ns.get('_CONSTANT_REGEX'),
        )
    except Exception as e:
        print(f'  WARNING: could not load GL annotation data: {e}', file=sys.stderr)
        return [], [], None, None


def process_annotate():
    step('Step 11/13 – Annotate OpenGL constants')
    _CONSTANTS, _PACKAGES, _CALL_REGEX, _CONSTANT_REGEX = _load_gl_constants()
    if not _CALL_REGEX:
        print('  Skipped (GL constants data not available)')
        return

    def annotate_constants(code):
        def process_call(m):
            package = m.group(1)
            method  = m.group(2)
            def expand_constant(cm):
                val = int(cm.group(0))
                for group in _CONSTANTS:
                    if package in group[0] and method in group[0][package]:
                        for cpkg, cmap in group[1].items():
                            if val in cmap:
                                return f'{cpkg}.{cmap[val]}'
                return cm.group(0)
            return _CONSTANT_REGEX.sub(expand_constant, m.group(0))
        return _CALL_REGEX.sub(process_call, code)

    def update_imports(code, imp):
        add_after = 'org.lwjgl.opengl.GL11'
        if f'import {imp};\n' not in code:
            code = code.replace(
                f'import {add_after};\n',
                f'import {add_after};\nimport {imp};\n',
            )
        return code

    annotated = 0
    for f in SRC_CLIENT.rglob('*.java'):
        code = f.read_text(encoding='utf-8', errors='replace')
        if 'import org.lwjgl.opengl.' not in code:
            continue
        new_code = annotate_constants(code)
        for pkg in _PACKAGES:
            if pkg + '.' in new_code:
                new_code = update_imports(new_code, 'org.lwjgl.opengl.' + pkg)
        if new_code != code:
            f.write_text(new_code, encoding='utf-8')
            annotated += 1
    print(f'  Annotated {annotated} files')


def applyastyle():
    step('Step 12/13 – AStyle reformatting')
    if not ASTYLE_CFG.exists():
        print('  Skipped (no astyle.cfg)')
        return
    run([
        'astyle',
        '--suffix=none', '--quiet',
        f'--options={ASTYLE_CFG}',
        '--recursive',
        f'{SRC_CLIENT}/*.java',
    ])


def process_javadoc():
    step('Step 13a/13 – Add javadoc from CSVs')
    if not CSV_METHODS.exists() or not CSV_FIELDS.exists():
        print('  Skipped (no CSV files)')
        return

    methods = {}
    fields  = {}
    with open(CSV_METHODS, newline='', encoding='utf-8') as fh:
        for row in csv.DictReader(fh):
            if int(row['side']) in (0, 2) and row.get('desc'):
                methods[row['searge']] = row['desc'].replace('*/', '* /')
    with open(CSV_FIELDS, newline='', encoding='utf-8') as fh:
        for row in csv.DictReader(fh):
            if int(row['side']) in (0, 2) and row.get('desc'):
                fields[row['searge']] = row['desc'].replace('*/', '* /')

    re_field  = re.compile(r'^(?P<indent>[ \t]+)(?:[\w$.[\]]+ )*(?P<name>field_[0-9]+_[a-zA-Z_]+) *(?:=|;)')
    re_method = re.compile(r'^(?P<indent>[ \t]+)(?:[\w$.[\]]+ )*(?P<name>func_[0-9]+_[a-zA-Z_]+)\(')
    wrapper   = TextWrapper(width=120)
    added     = 0

    for src_file in SRC_CLIENT.rglob('*.java'):
        buf_in  = src_file.read_text(encoding='utf-8', errors='replace').splitlines(keepends=True)
        buf_out = []
        for line in buf_in:
            fm = re_field.match(line)
            mm = re_method.match(line) if not fm else None
            if fm:
                indent = fm.group('indent')
                name   = fm.group('name')
                if name in fields:
                    desc = fields[name]
                    prev = buf_out[-1].strip() if buf_out else ''
                    if len(desc) < 70 and r'\n' not in desc:
                        if prev not in ('', '{'):
                            buf_out.append('\n')
                        buf_out.append(f'{indent}/** {desc} */\n')
                    else:
                        wrapper.initial_indent = indent + ' * '
                        wrapper.subsequent_indent = indent + ' * '
                        if prev not in ('', '{'):
                            buf_out.append('\n')
                        buf_out.append(f'{indent}/**\n')
                        for dl in desc.split(r'\n'):
                            wrapper.drop_whitespace = dl != ' '
                            buf_out.append(wrapper.fill(dl) + '\n')
                        buf_out.append(f'{indent} */\n')
                    added += 1
            elif mm:
                indent = mm.group('indent')
                name   = mm.group('name')
                if name in methods:
                    desc = methods[name]
                    prev = buf_out[-1].strip() if buf_out else ''
                    wrapper.initial_indent = indent + ' * '
                    wrapper.subsequent_indent = indent + ' * '
                    if prev not in ('', '{'):
                        buf_out.append('\n')
                    buf_out.append(f'{indent}/**\n')
                    for dl in desc.split(r'\n'):
                        wrapper.drop_whitespace = dl != ' '
                        buf_out.append(wrapper.fill(dl) + '\n')
                    buf_out.append(f'{indent} */\n')
                    added += 1
            buf_out.append(line)
        src_file.write_text(''.join(buf_out), encoding='utf-8')

    print(f'  Added {added} javadoc entries')


def process_rename():
    step('Step 13b/13 – Rename srg identifiers to MCP names')
    if not CSV_METHODS.exists() or not CSV_FIELDS.exists():
        print('  Skipped (no CSV files)')
        return

    names = {'methods': {}, 'fields': {}, 'params': {}}
    with open(CSV_METHODS, newline='', encoding='utf-8') as fh:
        for row in csv.DictReader(fh):
            if int(row['side']) in (0, 2) and row['name'] != row['searge']:
                names['methods'][row['searge']] = row['name']
    with open(CSV_FIELDS, newline='', encoding='utf-8') as fh:
        for row in csv.DictReader(fh):
            if int(row['side']) in (0, 2) and row['name'] != row['searge']:
                names['fields'][row['searge']] = row['name']
    if CSV_PARAMS.exists():
        with open(CSV_PARAMS, newline='', encoding='utf-8') as fh:
            for row in csv.DictReader(fh):
                if int(row['side']) in (0, 2):
                    names['params'][row['param']] = row['name']

    re_map = {
        'methods': re.compile(r'func_[0-9]+_[a-zA-Z_]+'),
        'fields':  re.compile(r'field_[0-9]+_[a-zA-Z_]+'),
        'params':  re.compile(r'p_[\w]+_\d+_'),
    }

    renamed = 0
    for src_file in SRC_CLIENT.rglob('*.java'):
        buf  = src_file.read_text(encoding='utf-8', errors='replace')
        orig = buf
        for group in ('methods', 'fields', 'params'):
            buf = re_map[group].sub(
                lambda m, g=group: names[g].get(m.group(0), m.group(0)),
                buf,
            )
        if buf != orig:
            src_file.write_text(buf, encoding='utf-8')
            renamed += 1
    print(f'  Renamed identifiers in {renamed} files')


# ── Main ──────────────────────────────────────────────────────────────────────

def main():
    parser = argparse.ArgumentParser(description='Decompile Minecraft 1.8.9 client')
    parser.add_argument('--client',      action='store_true', required=True)
    parser.add_argument('--norecompile', action='store_true')
    parser.add_argument('--nocopy',      action='store_true')
    args = parser.parse_args()

    if not MC_JAR.exists():
        print(f'ERROR: {MC_JAR.relative_to(_HERE)} not found.', file=sys.stderr)
        print('Copy your Minecraft 1.8.9 jar to decompile/jars/minecraft.jar', file=sys.stderr)
        sys.exit(1)

    if shutil.which('astyle') is None:
        print('ERROR: astyle is not installed or not on PATH.', file=sys.stderr)
        print('  Linux (Arch):   sudo pacman -S astyle', file=sys.stderr)
        print('  Linux (Debian): sudo apt install astyle', file=sys.stderr)
        print('  macOS:          brew install astyle', file=sys.stderr)
        print('  Windows:        https://astyle.sourceforge.net/', file=sys.stderr)
        sys.exit(1)

    createsrgs()
    applyss()
    applyexceptor()
    filterffjar()
    applyff()
    copysrc()
    process_fffixes()
    applypatches()
    process_comments()
    process_cleanup()
    process_annotate()
    applyastyle()
    process_javadoc()
    process_rename()

    print('\nDecompile complete. Source at:', SRC_CLIENT.relative_to(_HERE))


if __name__ == '__main__':
    main()
