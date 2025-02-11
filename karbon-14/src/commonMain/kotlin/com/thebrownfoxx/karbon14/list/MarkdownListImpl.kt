package com.thebrownfoxx.karbon14.list

import com.thebrownfoxx.karbon14.Markdown
import com.thebrownfoxx.karbon14.MarkdownImpl

public class MarkdownListImpl(private val startingIndex: Int?) : MarkdownList {
    private val elements = mutableListOf<ListElementNode>()
    override val node: ListNode
        get() = when (startingIndex) {
            null -> UnorderedListNode(elements)
            else -> OrderedListNode(startingIndex, elements)
        }

    override fun blockItem(content: Markdown.() -> Unit) {
        elements.add(ListItemNode(MarkdownImpl().apply(content).root.children))
    }

    override fun unorderedList(content: MarkdownList.() -> Unit) {
        val list = MarkdownListImpl(null).apply(content).node
        elements.add(SubListNode(list))
    }

    override fun orderedList(
        startingIndex: Int,
        content: MarkdownList.() -> Unit,
    ) {
        val list = MarkdownListImpl(startingIndex).apply(content).node
        elements.add(SubListNode(list))
    }

}