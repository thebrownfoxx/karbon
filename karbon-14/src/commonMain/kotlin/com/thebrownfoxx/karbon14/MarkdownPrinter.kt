package com.thebrownfoxx.karbon14

public fun interface MarkdownPrinter {
    public fun print(markdown: Markdown)
}

public fun Markdown.print(vararg printers: MarkdownPrinter = arrayOf(ConsoleTreePrinter)) {
    printers.forEach { it.print(this) }
}

public object ConsoleTreePrinter : MarkdownPrinter {
    override fun print(markdown: Markdown) {
        markdown.root.print()
    }

    private fun Node.print(level: Int = 0) {
        if (level > 0) print("  ".repeat(level))
        println(toTreeBranchString())
        if (this is InternalNode) {
            children.forEach { it.print(level + 1) }
        }
    }
}