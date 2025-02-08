package com.thebrownfoxx.karbon14

import java.io.File

public class FilePrinter(
    public val file: File,
    public val renderer: MarkdownRenderer = CommonMarkRenderer(),
) : MarkdownPrinter {
    override fun print(markdown: Markdown) {
        file.writeText(markdown.render(renderer))
    }
}