import importlib.util
import tempfile
import unittest
from pathlib import Path


SPEC = importlib.util.spec_from_file_location(
    "validate_structure", Path(__file__).resolve().parents[1] / "validate_structure.py"
)
validator = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(validator)


class StructureValidationTest(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.write("AGENTS.md", "# Guia\n[Skill](.agents/skills/example/SKILL.md)\n")
        self.write(".agents/skills/example/SKILL.md", "---\nname: example\ndescription: Faz uma tarefa.\n---\n")

    def write(self, path, text):
        target = self.root / path
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_text(text, encoding="utf-8")

    def errors(self):
        return validator.validate(self.root)[0]

    def test_valid_kit_and_folded_description(self):
        self.write(".agents/skills/example/SKILL.md", "---\nname: example\ndescription: >-\n  Primeira linha.\n  Segunda linha.\n---\n")
        self.assertEqual([], self.errors())

    def test_broken_link_is_reported_with_source(self):
        self.write("README.md", "[Guia](missing.md)\n")
        self.assertTrue(any("README.md:1" in e and "missing.md" in e for e in self.errors()))

    def test_links_resolve_from_containing_document(self):
        self.write(".agents/rules/flow.md", "[Skill](../skills/example/SKILL.md)\n")
        self.assertEqual([], self.errors())

    def test_duplicate_skill_names_are_rejected(self):
        self.write(".agents/skills/another/SKILL.md", "---\nname: example\ndescription: Outra.\n---\n")
        self.assertTrue(any("nome duplicado" in e for e in self.errors()))

    def test_missing_metadata_and_entrypoint_are_rejected(self):
        self.write(".agents/skills/example/SKILL.md", "---\nname: example\n---\n")
        self.write(".agents/skills/incomplete/references/info.md", "# Referência\n")
        errors = self.errors()
        self.assertTrue(any("description" in e for e in errors))
        self.assertTrue(any("SKILL.md ausente" in e for e in errors))

    def test_duplicate_metadata_is_rejected(self):
        self.write(".agents/skills/example/SKILL.md", "---\nname: example\nname: other\ndescription: Texto.\n---\n")
        self.assertTrue(any("campo duplicado" in e for e in self.errors()))

    def test_external_and_code_examples_are_not_local_links(self):
        self.write("README.md", '[Web](https://example.com/page)\n```md\n[Exemplo](missing.md)\n```\n`[Inline](missing.md)`\n')
        self.assertEqual([], self.errors())

    def test_output_template_links_are_not_resolved_in_skill_folder(self):
        self.write(".agents/skills/example/references/TEMPLATE.md", "[PRD](prd.md)\n")
        self.assertEqual([], self.errors())

    def test_escape_from_repository_is_rejected(self):
        self.write("README.md", "[Arquivo](../outside.md)\n")
        self.assertTrue(any("fora do repositório" in e for e in self.errors()))

    def test_encoded_spaces_and_anchors_resolve(self):
        self.write("docs/my guide.md", "# Guia\n")
        self.write("README.md", "[Guia](docs/my%20guide.md#guia)\n")
        self.assertEqual([], self.errors())


if __name__ == "__main__":
    unittest.main()
