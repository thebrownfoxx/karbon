package com.thebrownfoxx.karbon14

public interface Node {
    public fun toTreeBranchString(): String
}

public interface InternalNode : Node {
    public val children: List<Node>
}

public data class RootNode(override val children: List<BlockNode>) : InternalNode {
    override fun toTreeBranchString(): String = "Root"

    public operator fun plus(other: RootNode): RootNode {
        return RootNode(children + other.children)
    }
}

public interface InlineNode : Node

public data class TextNode(val text: String) : InlineNode {
    override fun toTreeBranchString(): String = "Text(${text.singleLine()})"

    public operator fun plus(other: TextNode): TextNode {
        return TextNode(text + other.text)
    }
}

public data object LineBreakNode : InlineNode {
    override fun toTreeBranchString(): String = "LineBreak"
}

public data class LinkNode(
    val uri: Uri,
    override val children: List<InlineNode>,
) : InternalNode, InlineNode {
    override fun toTreeBranchString(): String = "Link(${uri.value.singleLine()})"
}

public data class BoldNode(override val children: List<InlineNode>) : InternalNode, InlineNode {
    override fun toTreeBranchString(): String = "Bold"

    public operator fun plus(other: BoldNode): BoldNode {
        return BoldNode(children + other.children)
    }
}

public data class ItalicNode(override val children: List<InlineNode>) : InternalNode, InlineNode {
    override fun toTreeBranchString(): String = "Italic"

    public operator fun plus(other: ItalicNode): ItalicNode {
        return ItalicNode(children + other.children)
    }
}

public data class StrikethroughNode(override val children: List<InlineNode>) : InternalNode, InlineNode {
    override fun toTreeBranchString(): String = "Strikethrough"

    public operator fun plus(other: StrikethroughNode): StrikethroughNode {
        return StrikethroughNode(children + other.children)
    }
}

public data class InlineCodeNode(val content: String) : InlineNode {
    override fun toTreeBranchString(): String = "InlineCode(${content.singleLine()})"
}

public data class ImageNode(
    val uri: Uri,
    val altText: String,
) : InlineNode {
    override fun toTreeBranchString(): String = "Image(${uri.value.singleLine()}, $altText)"
}

public interface BlockNode : Node

public data class BlockQuoteNode(override val children: List<Node>) : InternalNode, BlockNode {
    override fun toTreeBranchString(): String = "BlockQuote"

    public operator fun plus(other: BlockQuoteNode): BlockQuoteNode {
        return BlockQuoteNode(children + other.children)
    }
}

public data class ParagraphNode(override val children: List<InlineNode>) : InternalNode, BlockNode {
    override fun toTreeBranchString(): String = "Paragraph"

    public operator fun plus(other: ParagraphNode): ParagraphNode {
        return ParagraphNode(children + other.children)
    }
}

public data class HeaderNode(
    val level: HeaderLevel,
    override val children: List<InlineNode>,
) : InternalNode, BlockNode {
    override fun toTreeBranchString(): String = "Header ${level.value}"
}

public data class BlockCodeNode(
    val language: CodeLanguage?,
    val content: String,
) : Node, BlockNode {
    override fun toTreeBranchString(): String {
        return "BlockCode(language=${language?.name}, ${content.singleLine()})"
    }
}

public data object WhitespaceNode : BlockNode {
    override fun toTreeBranchString(): String = "Whitespace"
}

public data object HorizontalRuleNode : BlockNode {
    override fun toTreeBranchString(): String = "HorizontalRule"
}

private fun String.singleLine() = this.replace("\n", "\\n")