package com.thebrownfoxx.karbon14

public interface Node {
    public fun toTreeBranchString(): String
}

public interface InternalNode : Node {
    public val children: List<Node>
}

public interface InlineNode : Node

public data class RootNode(override val children: List<Node>) : InternalNode {
    override fun toTreeBranchString(): String = "Root"
}

public data class TextNode(val text: String) : InlineNode {
    override fun toTreeBranchString(): String = "Text($text)"
}

public data object LineBreakNode : InlineNode {
    override fun toTreeBranchString(): String = "LineBreak"
}

public data object ParagraphBreakNode : InlineNode {
    override fun toTreeBranchString(): String = "ParagraphBreak"
}

public data class LinkNode(
    val uri: Uri,
    override val children: List<InlineNode>,
) : InternalNode, InlineNode {
    override fun toTreeBranchString(): String = "Link($uri)"
}

public data class BoldNode(override val children: List<InlineNode>) : InternalNode, InlineNode {
    override fun toTreeBranchString(): String = "Bold"
}

public data class ItalicNode(override val children: List<InlineNode>) : InternalNode, InlineNode {
    override fun toTreeBranchString(): String = "Italic"
}

public data class StrikethroughNode(override val children: List<InlineNode>) : InternalNode, InlineNode {
    override fun toTreeBranchString(): String = "Strikethrough"
}

public data class InlineCodeNode(val content: String) : InlineNode {
    override fun toTreeBranchString(): String = "InlineCode"
}

public data class ImageNode(
    val uri: Uri,
    val altText: String,
) : InlineNode {
    override fun toTreeBranchString(): String = "Image($uri, $altText)"
}

public data class BlockNode(override val children: List<InlineNode>) : InternalNode {
    override fun toTreeBranchString(): String = "Block"
}

public data class HeadingNode(
    val level: HeadingLevel,
    override val children: List<InlineNode>,
) : InternalNode {
    override fun toTreeBranchString(): String = "Heading ${level.value}"
}

public data class BlockQuoteNode(override val children: List<InlineNode>) : InternalNode {
    override fun toTreeBranchString(): String = "BlockQuote"
}

public data class BlockCodeNode(
    val language: CodeLanguage?,
    val content: String,
) : Node {
    override fun toTreeBranchString(): String {
        val singleLineContent = content.split("\n").joinToString(" ") { it.trim() }
        return "BlockCode(language=${language?.name}, $singleLineContent)"
    }
}

public data object WhitespaceNode : Node {
    override fun toTreeBranchString(): String = "Whitespace"
}

public data object HorizontalRuleNode : Node {
    override fun toTreeBranchString(): String = "HorizontalRule"
}