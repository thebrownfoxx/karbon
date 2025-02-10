package com.thebrownfoxx.karbon14.parser

import com.thebrownfoxx.karbon14.ConsoleTreePrinter
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser

private fun main() {
    val src = """
        pizzer
        
        pizza!
        [**Link!**](https://link)
        **bold**
        _italic_
        **_both_**
        `inline code`
        ![alt text](image.jpg)
        > Node
        ## Heading node
        > Block
        > Quote
        ```kotlin
        val hey = 1
        ```

        ---
    """.trimIndent()
    val flavor = GFMFlavourDescriptor()
    val parsedTree = MarkdownParser(flavor).buildMarkdownTreeFromString(src)

    parsedTree.printTree(src)
    val markdownParser = JetBrainsMarkdownParser(src)
    ConsoleTreePrinter.print(markdownParser.parseText())
}

private fun ASTNode.printTree(fullText: String, level: Int = 0) {
    if (level > 0) print("  ".repeat(level))
    print(type)
    if (children.isEmpty()) print(": ${fullText.substring(startOffset, endOffset).replace("\n", "\\n")}")
    println()
    children.forEach { it.printTree(fullText, level + 1) }
}