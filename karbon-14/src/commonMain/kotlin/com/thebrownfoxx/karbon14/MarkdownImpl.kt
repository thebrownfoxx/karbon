package com.thebrownfoxx.karbon14

public class InlineMarkdownImpl: InlineMarkdown {
    private val nodes = mutableListOf<InlineNode>()

    override val children: List<InlineNode> = nodes.toList()

    override fun text(value: String) {
        nodes.add(TextNode(value))
    }

    override fun lineBreak() {
        nodes.add(LineBreakNode)
    }

    override fun paragraphBreak() {
        nodes.add(ParagraphBreakNode)
    }

    override fun link(uri: String, content: InlineMarkdown.() -> Unit) {
        nodes.add(LinkNode(uri, getContentChildren(content)))
    }

    override fun bold(content: InlineMarkdown.() -> Unit) {
        nodes.add(BoldNode(getContentChildren(content)))
    }

    override fun italic(content: InlineMarkdown.() -> Unit) {
        nodes.add(ItalicNode(getContentChildren(content)))
    }

    override fun strikethrough(content: InlineMarkdown.() -> Unit) {
        nodes.add(StrikethroughNode(getContentChildren(content)))
    }

    override fun inlineCode(content: String) {
        nodes.add(InlineCodeNode(content))
    }

    override fun image(uri: String, altText: String) {
        nodes.add(ImageNode(uri, altText))
    }
}

public class MarkdownImpl : Markdown, InlineMarkdown by InlineMarkdownImpl() {
    private val nodes = mutableListOf<Node>()

    override val root: RootNode
        get() = RootNode(nodes)

    override fun heading(
        level: HeadingLevel,
        content: InlineMarkdown.() -> Unit,
    ) {
        nodes.add(HeadingNode(level, getContentChildren(content)))
    }

    override fun blockQuote(content: InlineMarkdown.() -> Unit) {
        nodes.add(BlockQuoteNode(getContentChildren(content)))
    }

    override fun blockCode(language: CodeLanguage?, content: String) {
        nodes.add(BlockCodeNode(language, content))
    }

    override fun horizontalRule() {
        nodes.add(HorizontalRuleNode)
    }
}

private fun getContentChildren(content: InlineMarkdown.() -> Unit): List<InlineNode> {
    return InlineMarkdownImpl().apply(content).children
}