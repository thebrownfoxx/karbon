package com.thebrownfoxx.karbon14

public fun interface MarkdownRenderer {
    public fun render(root: RootNode): String
}

public fun Markdown.render(renderer: MarkdownRenderer = DefaultMarkdownRenderer()): String {
    return renderer.render(root)
}