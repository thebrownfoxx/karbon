package com.thebrownfoxx.karbon14

public interface InlineMarkdown {
    public fun text(value: String)
    public fun lineBreak()
    public fun link(uri: Uri, content: InlineMarkdown.() -> Unit)
    public fun bold(content: InlineMarkdown.() -> Unit)
    public fun italic(content: InlineMarkdown.() -> Unit)
    public fun strikethrough(content: InlineMarkdown.() -> Unit)
    public fun inlineCode(content: String)
    public fun image(uri: Uri, altText: String)

    public fun link(uri: Uri, text: String): Unit = link(uri) { text(text) }
    public fun bold(text: String): Unit = bold { text(text) }
    public fun italic(text: String): Unit = italic { text(text) }
}

public interface Markdown {
    public val root: RootNode

    public fun blockQuote(content: Markdown.() -> Unit)
    public fun paragraph(content: InlineMarkdown.() -> Unit)
    public fun header(level: HeaderLevel, content: InlineMarkdown.() -> Unit)
    public fun blockCode(language: CodeLanguage?, content: String)
    public fun horizontalRule()
    public fun whitespace()

    public fun paragraph(text: String): Unit = paragraph { text(text) }
    public fun h1(content: InlineMarkdown.() -> Unit): Unit = header(1.headerLevel, content)
    public fun h2(content: InlineMarkdown.() -> Unit): Unit = header(2.headerLevel, content)
    public fun h3(content: InlineMarkdown.() -> Unit): Unit = header(3.headerLevel, content)
    public fun h4(content: InlineMarkdown.() -> Unit): Unit = header(4.headerLevel, content)
    public fun h5(content: InlineMarkdown.() -> Unit): Unit = header(5.headerLevel, content)
    public fun h6(content: InlineMarkdown.() -> Unit): Unit = header(6.headerLevel, content)
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