package com.thebrownfoxx.karbon

public class MarkdownList(public val startingIndex: Int?) {
    private val _items = mutableListOf<Item>()
    public val items: List<Item> get() = _items.toList()

    public fun item(content: Markdown.() -> Unit) {
        _items.add(ListItem(content))
    }

    public fun unorderedList(content: MarkdownList.() -> Unit) {
        _items.add(SubList(MarkdownList(startingIndex = null).apply(content)))
    }

    public fun orderedList(startingIndex: Int = 1, content: MarkdownList.() -> Unit) {
        _items.add(SubList(MarkdownList(startingIndex).apply(content)))
    }

    public fun li(content: Markdown.() -> Unit): Unit = item(content)
    public fun li(text: String): Unit = li { text(text) }
    public fun ul(content: MarkdownList.() -> Unit): Unit = unorderedList(content)
    public fun ol(startingIndex: Int = 1, content: MarkdownList.() -> Unit): Unit =
        orderedList(startingIndex, content)

    public sealed interface Item

    public class ListItem(public val content: Markdown.() -> Unit) : Item

    public class SubList(public val list: MarkdownList) : Item
}