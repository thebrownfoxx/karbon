package com.thebrownfoxx.karbon14

public class CommonMarkRenderer(
    private val markdownInTextNodeStrategy: MarkdownInTextNodeStrategy = IgnoreMarkdownInTextNodeStrategy,
) : MarkdownRenderer {
    override fun render(markdown: Markdown): String {
        return markdown.root.render()
    }

    private fun Node.render(): String {
        return when (this) {
            is RootNode -> render()
            is TextNode -> render()
            is LineBreakNode -> render()
            is ParagraphBreakNode -> render()
            is LinkNode -> render()
            is BoldNode -> render()
            is ItalicNode -> render()
            is StrikethroughNode -> render()
            is InlineCodeNode -> render()
            is ImageNode -> render()
            is HeadingNode -> render()
            is BlockQuoteNode -> render()
            is BlockCodeNode -> render()
            is HorizontalRuleNode -> render()
            else -> error("Unsupported node $this")
        }
    }

    private fun RootNode.render(): String {
        return children.joinToString("\n") { it.render() }
    }

    private fun TextNode.render(): String {
        return markdownInTextNodeStrategy.apply(text)
    }

    private fun LineBreakNode.render(): String {
        return "  \n"
    }

    private fun ParagraphBreakNode.render(): String {
        return "\n\n"
    }

    private fun LinkNode.render(): String {
        return "[$renderedContent]($uri)"
    }

    private fun BoldNode.render(): String {
        return "**$renderedContent**"
    }

    private fun ItalicNode.render(): String {
        return "_${renderedContent}_"
    }

    private fun StrikethroughNode.render(): String {
        return "~~$renderedContent~~"
    }

    private fun InlineCodeNode.render(): String {
        return "`$content`"
    }

    private fun ImageNode.render(): String {
        return "![$altText]($uri)"
    }

    private fun HeadingNode.render(): String {
        val hashes = "#".repeat(level.value)
        return "$hashes $renderedContent"
    }

    private fun BlockQuoteNode.render(): String {
        val lines = renderedContent.split("\n")
        val decoratedLines =  lines.map { "> $it" }
        return decoratedLines.joinToString("\n")
    }

    private fun BlockCodeNode.render(): String {
        val language = language?.name ?: ""
        return """
            ```$language
            $content
            ```
        """.trimIndent()
    }

    private fun HorizontalRuleNode.render(): String {
        return "___"
    }

    private val InternalNode.renderedContent
        get() = children.joinToString("\n") { it.render() }
}

public fun interface MarkdownInTextNodeStrategy {
    public fun apply(content: String): String
}

public val IgnoreMarkdownInTextNodeStrategy: MarkdownInTextNodeStrategy = MarkdownInTextNodeStrategy { it }