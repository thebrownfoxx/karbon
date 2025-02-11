package com.thebrownfoxx.karbon14.list

import com.thebrownfoxx.karbon14.InlineMarkdown
import com.thebrownfoxx.karbon14.Markdown

public interface MarkdownList {
    public val node: ListNode

    public fun blockItem(content: Markdown.() -> Unit)
    public fun unorderedList(content: MarkdownList.() -> Unit)
    public fun orderedList(startingIndex: Int = 1, content: MarkdownList.() -> Unit)

    public fun item(content: InlineMarkdown.() -> Unit): Unit = blockItem { p(content) }

    public fun li(content: InlineMarkdown.() -> Unit): Unit = item(content)
    public fun li(text: String): Unit = li { text(text) }
    public fun ul(content: MarkdownList.() -> Unit): Unit = unorderedList(content)
    public fun ol(startingIndex: Int = 1, content: MarkdownList.() -> Unit): Unit =
        orderedList(startingIndex, content)
}