package com.thebrownfoxx.karbon14

import com.thebrownfoxx.karbon14.list.*
import kotlin.jvm.JvmName

public class DefaultMarkdownRenderer(
    private val markdownInTextNodeStrategy: MarkdownInTextNodeStrategy = IgnoreMarkdownInTextNodeStrategy,
) : MarkdownRenderer {
    override fun render(root: RootNode): String {
        return root.render()
    }

    private fun Node.render(): String {
        return when (this) {
            is RootNode -> render()
            is TextNode -> render()
            is LineBreakNode -> render()
            is LinkNode -> render()
            is BoldNode -> render()
            is ItalicNode -> render()
            is StrikethroughNode -> render()
            is InlineCodeNode -> render()
            is ImageNode -> render()
            is BlockQuoteNode -> render()
            is ParagraphNode -> render()
            is HeaderNode -> render()
            is BlockCodeNode -> render()
            is WhitespaceNode -> render()
            is HorizontalRuleNode -> render()
            is ListNode -> render()
            else -> error("Unsupported node $this")
        }
    }

    @JvmName("renderRootNode")
    private fun RootNode.render(): String {
        return children.joinToString("\n") { it.render() }
    }

    private fun TextNode.render(): String {
        return markdownInTextNodeStrategy.apply(text)
    }

    private fun LineBreakNode.render(): String {
        return "  \n"
    }

    private fun LinkNode.render(): String {
        return "[$renderedContent](${uri.value})"
    }

    private fun BoldNode.render(): String {
        return "**$renderedContent**"
    }

    private fun ItalicNode.render(): String {
        return "_${renderedContent}_"
    }

    private fun StrikethroughNode.render(): String {
        return "~~$renderedContent~~"
    }

    private fun InlineCodeNode.render(): String {
        return "`$content`"
    }

    private fun ImageNode.render(): String {
        return "![$altText](${uri.value})"
    }

    private fun BlockQuoteNode.render(): String {
        val lines = renderedContent.split("\n")
        val decoratedLines = lines.map { "> $it" }
        return decoratedLines.joinToString("\n")
    }

    private fun ParagraphNode.render(): String {
        return renderedContent
    }

    private fun HeaderNode.render(): String {
        val hashes = "#".repeat(level.value)
        return "$hashes $renderedContent"
    }

    private fun BlockCodeNode.render(): String {
        val language = language?.name ?: ""
        return "```$language\n$content\n```"
    }

    private fun WhitespaceNode.render(): String {
        return ""
    }

    private fun HorizontalRuleNode.render(): String {
        return "___"
    }

    private fun ListNode.render(indents: Int = 0): String {
        return when (this) {
            is UnorderedListNode -> children.emitUnorderedList(indents)
            is OrderedListNode -> children.emitOrderedList(startingIndex, indents)
        }
    }

    private fun List<ListElementNode>.emitUnorderedList(indents: Int): String {
        return joinToString("\n") { item ->
            when (item) {
                is ListItemNode -> item.renderUnordered(indents)
                is SubListNode -> item.list.render(indents + 2)
            }
        }
    }

    private fun ListItemNode.renderUnordered(indents: Int): String {
        val indent = " ".repeat(indents)
        return "$indent- $renderedContent"
    }

    private fun List<ListElementNode>.emitOrderedList(startingIndex: Int, indents: Int): String {
        var previousIndex = 0
        return joinToString("\n") { item ->
            when (item) {
                is ListItemNode -> item.renderOrdered(startingIndex, indents, this) { previousIndex = it }
                is SubListNode -> item.list.render(indents + previousIndex.toString().length + 2)
            }
        }
    }

    private fun ListItemNode.renderOrdered(
        startingIndex: Int,
        indents: Int,
        list: List<ListElementNode>,
        updatePreviousIndex: (Int) -> Unit,
    ): String {
        val index = list.filterIsInstance<ListItemNode>().indexOf(this)
        val indexToShow = startingIndex + index
        updatePreviousIndex(index)
        return renderOrdered(indexToShow, indents)
    }

    private fun ListItemNode.renderOrdered(index: Int, indents: Int): String {
        val indent = " ".repeat(indents)
        return "$indent$index. $renderedContent"
    }

    private val InternalNode.renderedContent
        get() = children.joinToString("") { it.render() }
}

public fun interface MarkdownInTextNodeStrategy {
    public fun apply(content: String): String
}

public val IgnoreMarkdownInTextNodeStrategy: MarkdownInTextNodeStrategy = MarkdownInTextNodeStrategy { it }