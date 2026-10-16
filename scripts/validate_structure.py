"""Validate this kit's skill metadata and inline local Markdown links.

Uses only the standard library. This is a structural check, not a full YAML or
Markdown parser and not a semantic validator of generated specifications.
Output templates named TEMPLATE*.md are excluded from link resolution because
their links target the future feature directory, not the skill directory.
"""

import argparse
import re
from pathlib import Path
from urllib.parse import unquote, urlsplit


LINK = re.compile(r"\[[^\]\n]*\]\((<[^>]+>|[^\s)]+)(?:\s+\"[^\"]*\")?\)")
NAME = re.compile(r"[a-z0-9]+(?:-[a-z0-9]+)*")


def prose_lines(text):
    """Yield numbered Markdown lines outside fenced code blocks."""
    fence = None
    for number, line in enumerate(text.splitlines(), 1):
        marker = re.match(r"^\s*(`{3,}|~{3,})(.*)$", line)
        if marker:
            token, suffix = marker.groups()
            if fence is None:
                fence = token
            elif token[0] == fence[0] and len(token) >= len(fence) and not suffix.strip():
                fence = None
            continue
        if fence is None:
            # Inline code can include illustrative Markdown links.
            yield number, re.sub(r"(`+).*?\1", "", line)


def required_metadata(text):
    """Extract required scalar fields; leave full YAML validation to skill tools."""
    match = re.match(r"\A---\r?\n(.*?)\r?\n---(?:\r?\n|$)", text, re.S)
    if not match:
        return {}, ["frontmatter ausente ou não delimitado"]
    fields, errors = {}, []
    lines = match.group(1).splitlines()
    for index, line in enumerate(lines):
        field = re.match(r"^(name|description):\s*(.*)$", line)
        if not field:
            continue
        key, value = field.groups()
        if key in fields:
            errors.append(f"campo duplicado: {key}")
        if value in {">", ">-", ">+", "|", "|-", "|+"}:
            fragments = []
            for continuation in lines[index + 1:]:
                if continuation and not continuation[0].isspace():
                    break
                fragments.append(continuation.strip())
            value = " ".join(fragments)
        fields[key] = value.strip().strip("\"'")
    for key in ("name", "description"):
        if not fields.get(key):
            errors.append(f"campo obrigatório vazio ou ausente: {key}")
    return fields, errors


def validate(root):
    root = root.resolve()
    errors, skills, links = [], {}, 0
    agents = root / "AGENTS.md"
    skills_root = root / ".agents" / "skills"
    if not agents.is_file():
        errors.append("AGENTS.md ausente")
    if not skills_root.is_dir():
        errors.append(".agents/skills ausente")
    documents = {p for p in (agents, root / "README.md") if p.is_file()}
    for directory in (root / ".agents", root / "docs"):
        if directory.is_dir():
            documents.update(directory.rglob("*.md"))
    skill_paths = sorted(skills_root.glob("*/SKILL.md"))
    if skills_root.is_dir():
        for directory in skills_root.iterdir():
            if directory.is_dir() and not (directory / "SKILL.md").is_file():
                errors.append(f"{directory.relative_to(root)}: SKILL.md ausente")
    if not skill_paths:
        errors.append("nenhuma skill encontrada")

    for path in skill_paths:
        label = path.relative_to(root).as_posix()
        fields, problems = required_metadata(path.read_text(encoding="utf-8-sig"))
        errors.extend(f"{label}: {problem}" for problem in problems)
        name = fields.get("name", "")
        if not NAME.fullmatch(name) or len(name) > 64:
            errors.append(f"{label}: nome inválido")
        if name != path.parent.name:
            errors.append(f"{label}: nome difere do diretório")
        if name in skills:
            errors.append(f"{label}: nome duplicado com {skills[name]}")
        skills[name] = label
        description = fields.get("description", "")
        if len(description) > 1024 or "<" in description or ">" in description:
            errors.append(f"{label}: descrição excede limites de descoberta")

    for path in sorted(documents):
        if path.name.startswith("TEMPLATE"):
            continue
        for number, line in prose_lines(path.read_text(encoding="utf-8-sig")):
            for match in LINK.finditer(line):
                destination = match.group(1).strip("<>")
                parsed = urlsplit(destination)
                if parsed.scheme or parsed.netloc or not parsed.path:
                    continue
                relative = unquote(parsed.path)
                label = f"{path.relative_to(root).as_posix()}:{number}"
                if Path(relative).is_absolute():
                    errors.append(f"{label}: link local absoluto não portátil: {destination}")
                    continue
                target = (path.parent / relative).resolve()
                if not target.is_relative_to(root):
                    errors.append(f"{label}: link fora do repositório: {destination}")
                elif not target.exists():
                    errors.append(f"{label}: link local ausente: {destination}")
                links += 1
    return errors, {"documents": len(documents), "skills": len(skill_paths), "links": links}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--root", type=Path, default=Path(__file__).resolve().parents[1])
    args = parser.parse_args()
    errors, counts = validate(args.root)
    for error in errors:
        print(f"ERRO: {error}")
    print(f"{counts['documents']} documentos, {counts['skills']} skills, "
          f"{counts['links']} links locais: {len(errors)} erro(s).")
    return 1 if errors else 0


if __name__ == "__main__":
    raise SystemExit(main())
