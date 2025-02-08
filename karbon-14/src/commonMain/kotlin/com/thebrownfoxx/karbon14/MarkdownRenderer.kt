package com.thebrownfoxx.karbon14

public fun interface MarkdownRenderer {
    public fun render(markdown: Markdown): String
}

public fun Markdown.render(renderer: MarkdownRenderer = CommonMarkRenderer()): String {
    return renderer.render(this)
}