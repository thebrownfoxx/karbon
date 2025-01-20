package com.thebrownfoxx.karbon

import java.io.File

public class FilePrinter(private val file: File) : MarkdownPrinter {
    override fun Markdown.print() {
        file.writeText(value)
    }
}