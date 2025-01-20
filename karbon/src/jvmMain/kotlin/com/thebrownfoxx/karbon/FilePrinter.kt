package com.thebrownfoxx.karbon

import java.io.File

class FilePrinter(private val file: File) : MarkdownPrinter {
    override fun Markdown.print() {
        file.writeText(value)
    }
}