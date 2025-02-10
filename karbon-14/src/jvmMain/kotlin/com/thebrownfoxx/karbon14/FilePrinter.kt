package com.thebrownfoxx.karbon14

import java.io.File

public class FilePrinter(
    public val file: File,
    public val renderer: MarkdownRenderer = DefaultMarkdownRenderer(),
) : MarkdownPrinter {
    override fun print(root: RootNode) {
        file.writeText(renderer.render(root))
    }
}