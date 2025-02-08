package com.thebrownfoxx.karbon14

public interface InlineMarkdown {
    public val children: List<InlineNode>

    public fun text(value: String)
    public fun lineBreak()
    public fun paragraphBreak()
    public fun link(uri: String, content: InlineMarkdown.() -> Unit)
    public fun bold(content: InlineMarkdown.() -> Unit)
    public fun italic(content: InlineMarkdown.() -> Unit)
    public fun strikethrough(content: InlineMarkdown.() -> Unit)
    public fun inlineCode(content: String)
    public fun image(uri: String, altText: String)

    public fun link(uri: String, text: String): Unit = link(uri) { text(text) }
    public fun bold(text: String): Unit = bold { text(text) }
    public fun italic(text: String): Unit = italic { text(text) }
}

public interface Markdown : InlineMarkdown {
    public val root: RootNode

    public fun heading(level: HeadingLevel, content: InlineMarkdown.() -> Unit)
    public fun blockQuote(content: InlineMarkdown.() -> Unit)
    public fun blockCode(language: CodeLanguage?, content: String)
    public fun horizontalRule()

    public fun paragraph(content: InlineMarkdown.() -> Unit): Unit = run {
        content()
        paragraphBreak()
    }
    public fun paragraph(text: String): Unit = paragraph { text(text) }
    public fun p(content: InlineMarkdown.() -> Unit): Unit = paragraph(content)
    public fun p(text: String): Unit = paragraph(text)

    public fun h1(content: InlineMarkdown.() -> Unit): Unit = heading(1.headingLevel, content)
    public fun h2(content: InlineMarkdown.() -> Unit): Unit = heading(2.headingLevel, content)
    public fun h3(content: InlineMarkdown.() -> Unit): Unit = heading(3.headingLevel, content)
    public fun h4(content: InlineMarkdown.() -> Unit): Unit = heading(4.headingLevel, content)
    public fun h5(content: InlineMarkdown.() -> Unit): Unit = heading(5.headingLevel, content)
    public fun h6(content: InlineMarkdown.() -> Unit): Unit = heading(6.headingLevel, content)
    public fun h1(text: String): Unit = h1 { text(text) }
    public fun h2(text: String): Unit = h2 { text(text) }
    public fun h3(text: String): Unit = h3 { text(text) }
    public fun h4(text: String): Unit = h4 { text(text) }
    public fun h5(text: String): Unit = h5 { text(text) }
    public fun h6(text: String): Unit = h6 { text(text) }
    public fun blockCode(content: String): Unit = blockCode(null, content)
}

public fun <T : Markdown> markdown(
    implementation: T,
    builder: Markdown.() -> Unit,
): Markdown {
    return implementation.apply(builder)
}

public fun markdown(builder: Markdown.() -> Unit): Markdown {
    return MarkdownImpl().apply(builder)
}