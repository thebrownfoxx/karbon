package com.thebrownfoxx.karbon

interface Markdown {
    val value: String

    fun markdown(content: () -> String)
    fun text(value: String)
    fun line(content: Markdown.() -> Unit)
    fun header(level: HeaderLevel, content: Markdown.() -> Unit)
    fun link(uri: String, content: Markdown.() -> Unit)
    fun bold(content: Markdown.() -> Unit)
    fun italic(content: Markdown.() -> Unit)
    fun block(content: Markdown.() -> Unit)
    fun image(uri: String, altText: String)
    fun code(language: String = "", content: Markdown.() -> Unit)
    fun horizontalRule()

    fun unorderedList(content: MarkdownList.() -> Unit)
    fun orderedList(startingIndex: Int = 1, content: MarkdownList.() -> Unit)

    fun line(text: String) = line { text(text) }
    fun whitespace() = line {  }
    fun h1(content: Markdown.() -> Unit) = header(1.headerLevel, content)
    fun h2(content: Markdown.() -> Unit) = header(2.headerLevel, content)
    fun h3(content: Markdown.() -> Unit) = header(3.headerLevel, content)
    fun h4(content: Markdown.() -> Unit) = header(4.headerLevel, content)
    fun h5(content: Markdown.() -> Unit) = header(5.headerLevel, content)
    fun h6(content: Markdown.() -> Unit) = header(6.headerLevel, content)
    fun h1(text: String) = h1 { text(text) }
    fun h2(text: String) = h2 { text(text) }
    fun h3(text: String) = h3 { text(text) }
    fun h4(text: String) = h4 { text(text) }
    fun h5(text: String) = h5 { text(text) }
    fun h6(text: String) = h6 { text(text) }
    fun link(uri: String, text: String) = link(uri) { text(text) }
    fun bold(text: String) = bold { text(text) }
    fun italic(text: String) = italic { text(text) }
    fun code(language: String = "", text: String) = code(language) { text(text) }

    fun ul(content: MarkdownList.() -> Unit) = unorderedList(content)
    fun ol(startingIndex: Int = 1, content: MarkdownList.() -> Unit) = orderedList(startingIndex, content)
}

fun markdown(
    markdown: Markdown = DefaultMarkdown(),
    builder: Markdown.() -> Unit,
): Markdown {
    return markdown.apply(builder)
}