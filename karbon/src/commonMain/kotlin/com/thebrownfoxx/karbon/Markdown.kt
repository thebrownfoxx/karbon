package com.thebrownfoxx.karbon

public interface Markdown {
    public val value: String

    public fun markdown(content: () -> String)
    public fun text(value: String)
    public fun line(content: Markdown.() -> Unit)
    public fun header(level: HeaderLevel, content: Markdown.() -> Unit)
    public fun link(uri: String, content: Markdown.() -> Unit)
    public fun bold(content: Markdown.() -> Unit)
    public fun italic(content: Markdown.() -> Unit)
    public fun block(content: Markdown.() -> Unit)
    public fun image(uri: String, altText: String)
    public fun code(language: String = "", content: Markdown.() -> Unit)
    public fun horizontalRule()

    public fun unorderedList(content: MarkdownList.() -> Unit)
    public fun orderedList(startingIndex: Int = 1, content: MarkdownList.() -> Unit)

    public fun line(text: String): Unit = line { text(text) }
    public fun whitespace(): Unit = line {  }
    public fun h1(content: Markdown.() -> Unit): Unit = header(1.headerLevel, content)
    public fun h2(content: Markdown.() -> Unit): Unit = header(2.headerLevel, content)
    public fun h3(content: Markdown.() -> Unit): Unit = header(3.headerLevel, content)
    public fun h4(content: Markdown.() -> Unit): Unit = header(4.headerLevel, content)
    public fun h5(content: Markdown.() -> Unit): Unit = header(5.headerLevel, content)
    public fun h6(content: Markdown.() -> Unit): Unit = header(6.headerLevel, content)
    public fun h1(text: String): Unit = h1 { text(text) }
    public fun h2(text: String): Unit = h2 { text(text) }
    public fun h3(text: String): Unit = h3 { text(text) }
    public fun h4(text: String): Unit = h4 { text(text) }
    public fun h5(text: String): Unit = h5 { text(text) }
    public fun h6(text: String): Unit = h6 { text(text) }
    public fun link(uri: String, text: String): Unit = link(uri) { text(text) }
    public fun bold(text: String): Unit = bold { text(text) }
    public fun italic(text: String): Unit = italic { text(text) }
    public fun code(language: String = "", text: String): Unit = code(language) { text(text) }

    public fun ul(content: MarkdownList.() -> Unit): Unit = unorderedList(content)
    public fun ol(startingIndex: Int = 1, content: MarkdownList.() -> Unit): Unit =
        orderedList(startingIndex, content)
}

public fun markdown(
    markdown: Markdown = DefaultMarkdown(),
    builder: Markdown.() -> Unit,
): Markdown {
    return markdown.apply(builder)
}