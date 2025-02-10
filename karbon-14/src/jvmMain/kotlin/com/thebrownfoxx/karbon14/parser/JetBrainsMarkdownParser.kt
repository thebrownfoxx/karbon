package com.thebrownfoxx.karbon14.parser

import com.thebrownfoxx.karbon14.*
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMElementTypes
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser as IntellijMarkdownParser

public class JetBrainsMarkdownParser(
    override val fullText: String,
    public val flavor: MarkdownFlavourDescriptor = GFMFlavourDescriptor(),
) : MarkdownParser {
    override fun parseText(): RootNode {
        val parsedTree = IntellijMarkdownParser(flavor).buildMarkdownTreeFromString(fullText)
        return parsedTree.toRootNode()
    }

    private fun ASTNode.toRootNode(): RootNode {
        return RootNode(children.mapBlockNodes())
    }

    private fun List<ASTNode>.mapBlockNodes(): List<BlockNode> {
        val mapper = BlockNodeMapper(this)
        while (mapper.hasNext) {
            mapper.next()
            mapper.mapCurrentNode()
        }
        return mapper.mappedNodes.toList()
    }

    @JvmName("mapCurrentNodeBlock")
    private fun BlockNodeMapper.mapCurrentNode() {
        val node = current() ?: return
        when (node.type) {
            MarkdownElementTypes.BLOCK_QUOTE -> mapBlockQuoteNode()
            MarkdownElementTypes.ATX_1 -> mapAtxHeadingNode(1.headerLevel)
            MarkdownElementTypes.ATX_2 -> mapAtxHeadingNode(2.headerLevel)
            MarkdownElementTypes.ATX_3 -> mapAtxHeadingNode(3.headerLevel)
            MarkdownElementTypes.ATX_4 -> mapAtxHeadingNode(4.headerLevel)
            MarkdownElementTypes.ATX_5 -> mapAtxHeadingNode(5.headerLevel)
            MarkdownElementTypes.ATX_6 -> mapAtxHeadingNode(6.headerLevel)
            MarkdownElementTypes.CODE_FENCE -> mapBlockCodeNode()
            MarkdownTokenTypes.EOL -> addMapped(WhitespaceNode)
            MarkdownTokenTypes.HORIZONTAL_RULE -> addMapped(HorizontalRuleNode)
            else -> addMapped(ParagraphNode(node.children.mapInlineNodes()))
        }
    }

    private fun BlockNodeMapper.mapBlockQuoteNode() {
        val children = current()?.children ?: return
        val filteredChildren = children
            .filter { it.type != MarkdownTokenTypes.BLOCK_QUOTE }
            .map { child ->
                val filteredSubChildren = child.children.filter { it.type != MarkdownTokenTypes.BLOCK_QUOTE }
                child.clone(children = filteredSubChildren)
            }
        val mappedChildren = filteredChildren.mapBlockNodes()
        addMapped(BlockQuoteNode(mappedChildren))
    }

    private fun BlockNodeMapper.mapAtxHeadingNode(level: HeaderLevel) {
        val children = current()?.children?.first { it.type == MarkdownTokenTypes.ATX_CONTENT }?.children ?: return
        val mappedChildren = children.drop(1).mapInlineNodes()
        addMapped(HeaderNode(level, mappedChildren))
    }

    private fun BlockNodeMapper.mapBlockCodeNode() {
        val children = current()?.children ?: return
        val language = children.firstOrNull { it.type == MarkdownTokenTypes.FENCE_LANG }?.text
            ?.let { CodeLanguage(it) }
        val unwrappedChildren = children.filter { it.type != MarkdownTokenTypes.FENCE_LANG }.dropWrapper(2)
        addMapped(BlockCodeNode(language, unwrappedChildren.text))
    }

    private fun List<ASTNode>.mapInlineNodes(): List<InlineNode> {
        val mapper = InlineNodeMapper(this)
        while (mapper.hasNext) {
            mapper.next()
            mapper.mapCurrentNode()
        }
        return mapper.mappedNodes.toList()
    }

    @JvmName("mapCurrentNodeInline")
    private fun InlineNodeMapper.mapCurrentNode() {
        val node = current() ?: return
        when (node.type) {
            MarkdownTokenTypes.HARD_LINE_BREAK -> mapLineBreakNode()
            MarkdownElementTypes.INLINE_LINK -> mapLinkNode()
            MarkdownElementTypes.STRONG -> mapBoldNode()
            MarkdownElementTypes.EMPH -> mapItalicNode()
            GFMElementTypes.STRIKETHROUGH -> mapStrikethroughNode()
            MarkdownElementTypes.CODE_SPAN -> mapInlineCodeNode()
            MarkdownElementTypes.IMAGE -> mapImageNode()
            else -> addMapped(TextNode(node.text))
        }
    }

    private fun InlineNodeMapper.mapLineBreakNode() {
        addMapped(LineBreakNode)
        next() // Ignore the EOL that always follows it
    }

    private fun InlineNodeMapper.mapLinkNode() {
        val children = current()?.children ?: return
        val text = children.first { it.type == MarkdownElementTypes.LINK_TEXT }
        val mappedText = text.children.dropWrapper(1).mapInlineNodes()
        val uri = children.first { it.type == MarkdownElementTypes.LINK_DESTINATION }
        val mappedUri = Uri(uri.text)
        addMapped(LinkNode(mappedUri, mappedText))
    }

    private fun InlineNodeMapper.mapBoldNode() {
        val children = current()?.children ?: return
        val mappedChildren = children.dropWrapper(2).mapInlineNodes()
        addMapped(BoldNode(mappedChildren))
    }

    private fun InlineNodeMapper.mapItalicNode() {
        val children = current()?.children ?: return
        val mappedChildren = children.dropWrapper(1).mapInlineNodes()
        addMapped(ItalicNode(mappedChildren))
    }

    private fun InlineNodeMapper.mapStrikethroughNode() {
        val children = current()?.children ?: return
        val mappedChildren = children.dropWrapper(2).mapInlineNodes()
        addMapped(StrikethroughNode(mappedChildren))
    }

    private fun InlineNodeMapper.mapInlineCodeNode() {
        val children = current()?.children ?: return
        val unwrappedChildren = children.dropWrapper(1)
        addMapped(InlineCodeNode(unwrappedChildren.text))
    }

    private fun InlineNodeMapper.mapImageNode() {
        val children = current()?.children?.first { it.type == MarkdownElementTypes.INLINE_LINK }?.children ?: return
        val text = children.first { it.type == MarkdownElementTypes.LINK_TEXT }.children.dropWrapper(1).text
        val uri = children.first { it.type == MarkdownElementTypes.LINK_DESTINATION }
        val mappedUri = Uri(uri.text)
        addMapped(ImageNode(mappedUri, text))
    }

    private fun <T> List<T>.dropWrapper(width: Int): List<T> {
        return subList(width, size - width)
    }

    private val List<ASTNode>.text: String get() {
        val startOffset = first().startOffset
        val endOffset = last().endOffset
        return fullText.substring(startOffset, endOffset)
    }

    private val ASTNode.text get() = fullText.substring(startOffset, endOffset)
}