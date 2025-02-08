package com.thebrownfoxx.karbon14

public class InlineMarkdownImpl : InlineMarkdown {
    private val _content = mutableListOf<InlineNode>()
    public val content: List<InlineNode> get() = _content.toList()

    override fun text(value: String) {
        _content.add(TextNode(value))
    }

    override fun lineBreak() {
        _content.add(LineBreakNode)
    }

    override fun paragraphBreak() {
        _content.add(ParagraphBreakNode)
    }

    override fun link(uri: Uri, content: InlineMarkdown.() -> Unit) {
        _content.add(LinkNode(uri, getContentChildren(content)))
    }

    override fun bold(content: InlineMarkdown.() -> Unit) {
        _content.add(BoldNode(getContentChildren(content)))
    }

    override fun italic(content: InlineMarkdown.() -> Unit) {
        _content.add(ItalicNode(getContentChildren(content)))
    }

    override fun strikethrough(content: InlineMarkdown.() -> Unit) {
        _content.add(StrikethroughNode(getContentChildren(content)))
    }

    override fun inlineCode(content: String) {
        _content.add(InlineCodeNode(content))
    }

    override fun image(uri: Uri, altText: String) {
        _content.add(ImageNode(uri, altText))
    }
}

public class MarkdownImpl : Markdown {
    private val nodes = mutableListOf<Node>()

    override val root: RootNode
        get() = RootNode(nodes)

    override fun block(content: InlineMarkdown.() -> Unit) {
        nodes.add(BlockNode(getContentChildren(content)))
    }

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

    override fun whitespace() {
        nodes.add(WhitespaceNode)
    }
}

private fun getContentChildren(content: InlineMarkdown.() -> Unit): List<InlineNode> {
    return InlineMarkdownImpl().apply(content).content
}