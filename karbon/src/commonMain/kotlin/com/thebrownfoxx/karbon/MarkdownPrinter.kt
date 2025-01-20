package com.thebrownfoxx.karbon

fun interface MarkdownPrinter {
    fun Markdown.print()
}

object ConsolePrinter : MarkdownPrinter {
    override fun Markdown.print() {
        print(value)
    }
}

fun Markdown.print(vararg printers: MarkdownPrinter = arrayOf(ConsolePrinter)) {
    printers.forEach { with(it) { print() } }
}