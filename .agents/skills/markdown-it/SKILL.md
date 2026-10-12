---
name: markdown-it
description: >-
  Standardized CommonMark & markdown-it parser, converter, and validator skill.
  Use when converting Word documents (.docx), HTML, or raw text to clean, semantic,
  CommonMark-compliant Markdown with GitHub Flavored Markdown (GFM) extensions,
  proper tables, fenced codeblocks, and syntax validation.
---

# markdown-it Document Conversion and Validation Skill

This skill provides guidelines, procedures, and validation rules for converting documents (Word .docx, HTML, and raw text) into standardized, semantic Markdown compatible with markdown-it (CommonMark + GFM extensions).

## Core Principles

1. **CommonMark & GFM Compliance**: Adhere strictly to the CommonMark spec and GitHub Flavored Markdown extensions (tables, task lists, strikethrough, autolinks).
2. **Explicit Code Fencing**: Always use triple backticks with explicit language identifiers (e.g. `` `java ``, `` `ash ``, `` `markdown ``).
3. **Robust Table Formatting**:
   - Every table must have a header row, delimiter row with alignment colons (| :--- | :---: | ---: |), and data rows.
   - Cells must escape pipe characters (\|) and avoid unescaped multiline line breaks.
4. **Lists & Indentation**:
   - Use -  or *  for unordered lists.
   - Use 1.  for ordered lists.
   - Indent nested list items by 2 or 4 spaces consistently.
   - Use - [ ] and - [x] for GFM task list items.
5. **Clean Typography**:
   - Ensure a blank line before and after headings, tables, code blocks, blockquotes, and lists.
   - Avoid non-standard HTML tags when standard Markdown constructs exist.
   - Convert Word special characters (curly quotes, em-dashes, non-breaking spaces) to clean UTF-8 or standard equivalents.

## Conversion Procedures for Word (.docx)

When converting Word documents to Markdown:

1. **Extract OpenXML Body**:
   - A .docx file is a ZIP archive containing word/document.xml and word/media/.
   - Read paragraph styles (<w:pStyle>), text runs (<w:t>), bold (<w:b>), italics (<w:i>), hyperlinks (<w:hyperlink>), and table nodes (<w:tbl>).
2. **Map Document Headings**:
   - Title / Subtitle -> Level 1 (#) or Level 2 (##).
   - Heading 1 -> #.
   - Heading 2 -> ##.
   - Heading 3 -> ###.
   - Heading 4 -> ####.
3. **Format Tables**:
   - Parse each <w:tr> and <w:tc>.
   - Ensure header row is distinct and followed by delimiter dashes | --- | --- |.
4. **Code Extraction**:
   - Detect source code blocks (fixed-width fonts, keywords, indented code blocks).
   - Wrap them in fenced code blocks with appropriate language tags.
5. **Sanitize & Validate**:
   - Check for unclosed backticks or unbalanced pipes in tables.
   - Validate that relative links, image paths, and list hierarchies are properly maintained.
