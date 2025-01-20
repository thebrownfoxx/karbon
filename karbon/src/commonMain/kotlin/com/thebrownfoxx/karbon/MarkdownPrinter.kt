package com.thebrownfoxx.karbon

public fun interface MarkdownPrinter {
    public fun Markdown.print()
}

public object ConsolePrinter : MarkdownPrinter {
    override fun Markdown.print() {
        print(value)
    }
}

public fun Markdown.print(vararg printers: MarkdownPrinter = arrayOf(ConsolePrinter)) {
    printers.forEach { with(it) { print() } }
}