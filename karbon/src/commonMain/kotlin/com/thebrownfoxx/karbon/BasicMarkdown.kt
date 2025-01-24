package com.thebrownfoxx.karbon

public class BasicMarkdown : Markdown {
    private val stringBuilder = StringBuilder()
    public override val value: String get() = stringBuilder.toString()

    override fun markdown(content: () -> String) {
        stringBuilder.appendLine(content())
    }

    override fun text(value: String) {
        val trimmedText = value.singleLine()
        stringBuilder.append(trimmedText)
    }

    override fun paragraph(content: Markdown.() -> Unit) {
        stringBuilder.append("${value(content)}  \n")
    }

    override fun header(level: HeaderLevel, content: Markdown.() -> Unit) {
        val hashes = "#".repeat(level.value)
        val contentValue = value(content).singleLine()
        stringBuilder.appendLine("$hashes $contentValue")
    }

    override fun link(uri: String, content: Markdown.() -> Unit) {
        stringBuilder.append("[${value(content)}]($uri)")
    }

    override fun bold(content: Markdown.() -> Unit) {
        stringBuilder.append("**${value(content)}**")
    }

    override fun italic(content: Markdown.() -> Unit) {
        stringBuilder.append("*${value(content)}*")
    }

    override fun block(content: Markdown.() -> Unit) {
        val contentValues = value(content).split("\n")
        contentValues.forEach { contentValue ->
            if (contentValue.isNotBlank()) stringBuilder.append("> ")
            stringBuilder.appendLine(contentValue)
        }
    }

    override fun image(uri: String, altText: String) {
        stringBuilder.append("![$altText]($uri)")
    }

    override fun code(language: String, content: String) {
        if ("\n" !in content && language == "")
            stringBuilder.append("`$content`")
        else {
            stringBuilder.appendLine("```${language.singleLine()}")
            stringBuilder.appendLine(content)
            stringBuilder.appendLine("```")
        }
    }

    override fun horizontalRule() {
        stringBuilder.appendLine("\n---")
    }

    override fun unorderedList(content: MarkdownList.() -> Unit) {
        MarkdownList(null).apply(content).emit(indents = 0)
    }

    override fun orderedList(startingIndex: Int, content: MarkdownList.() -> Unit) {
        MarkdownList(startingIndex).apply(content).emit(indents = 0)
    }

    private fun MarkdownList.emit(indents: Int) {
        when (startingIndex) {
            null -> items.emitUnorderedList(indents)
            else -> items.emitOrderedList(startingIndex, indents)
        }
    }

    private fun List<MarkdownList.Item>.emitUnorderedList(indents: Int) {
        forEach { item ->
            when (item) {
                is MarkdownList.ListItem -> item.emitUnorderedListItem(indents)
                is MarkdownList.SubList -> item.list.emit(indents + 2)
            }
        }
    }
    
    private fun MarkdownList.ListItem.emitUnorderedListItem(indents: Int) {
        val indent = " ".repeat(indents)
        val contentValue = value(content).singleLine()
        stringBuilder.appendLine("$indent- $contentValue")
    }

    private fun List<MarkdownList.Item>.emitOrderedList(startingIndex: Int, indents: Int) {
        var previousItemIndex = 0
        forEach { item ->
            when (item) {
                is MarkdownList.ListItem -> {
                    val listIndex = filterIsInstance<MarkdownList.ListItem>().indexOf(item)
                    val indexToShow = startingIndex + listIndex
                    item.emitOrderedListItem(indexToShow, indents)
                    previousItemIndex = indexToShow
                }
                is MarkdownList.SubList -> item.list.emit(indents + previousItemIndex.toString().length + 2)
            }
        }
    }

    private fun MarkdownList.ListItem.emitOrderedListItem(index: Int, indents: Int) {
        val indent = " ".repeat(indents)
        val contentValue = value(content).singleLine()
        stringBuilder.appendLine("$indent$index. $contentValue")
    }

    private fun value(function: Markdown.() -> Unit): String {
        return BasicMarkdown().apply(function).value
    }

    private fun String.singleLine() = replace("\\\n", " ")
        .replace("  \n", " ")
        .replace("\n", " ")
}