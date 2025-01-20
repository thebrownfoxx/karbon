package com.thebrownfoxx.karbon

class MarkdownList(val startingIndex: Int?) {
    private val _items = mutableListOf<Item>()
    val items get() = _items.toList()

    fun item(content: Markdown.() -> Unit) {
        _items.add(ListItem(content))
    }

    fun unorderedList(content: MarkdownList.() -> Unit) {
        _items.add(SubList(MarkdownList(startingIndex = null).apply(content)))
    }

    fun orderedList(startingIndex: Int = 1, content: MarkdownList.() -> Unit) {
        _items.add(SubList(MarkdownList(startingIndex).apply(content)))
    }

    fun li(content: Markdown.() -> Unit) = item(content)
    fun li(text: String) = li { text(text) }
    fun ul(content: MarkdownList.() -> Unit) = unorderedList(content)
    fun ol(startingIndex: Int = 1, content: MarkdownList.() -> Unit) = orderedList(startingIndex, content)

    sealed interface Item

    class ListItem(val content: Markdown.() -> Unit) : Item

    class SubList(val list: MarkdownList) : Item
}