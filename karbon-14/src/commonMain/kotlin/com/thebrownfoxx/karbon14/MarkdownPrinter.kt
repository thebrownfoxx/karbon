package com.thebrownfoxx.karbon14

public fun interface MarkdownPrinter {
    public fun print(root: RootNode)
}

public fun Markdown.print(vararg printers: MarkdownPrinter = arrayOf(ConsoleTreePrinter)) {
    printers.forEach { it.print(root) }
}

public object ConsoleTreePrinter : MarkdownPrinter {
    override fun print(root: RootNode) {
        root.print()
    }

    private fun Node.print(level: Int = 0) {
        if (level > 0) print("  ".repeat(level))
        println(toTreeBranchString())
        if (this is InternalNode) {
            children.forEach { it.print(level + 1) }
        }
    }
}

public class ConsolePrinter(
    public val renderer: MarkdownRenderer = DefaultMarkdownRenderer(),
) : MarkdownPrinter {
    override fun print(root: RootNode) {
        println(renderer.render(root))
    }
}