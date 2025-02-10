package com.thebrownfoxx.karbon14.parser

import com.thebrownfoxx.karbon14.*
import org.intellij.markdown.ast.ASTNode

internal abstract class NodeMapper<T : Node>(
    val nodes: List<ASTNode>,
    val mergeNodes: Boolean = true,
) {
    private var index = -1

    private val _mappedNodes = mutableListOf<T>()
    val mappedNodes get() = _mappedNodes.toList()

    val hasPrevious: Boolean get() = index > 0
    val hasNext: Boolean get() = index < nodes.lastIndex

    fun current(): ASTNode? {
        return nodes.getOrNull(index)
    }

    fun previous(): ASTNode? {
        return nodes.getOrNull(index--)
    }

    fun next(): ASTNode? {
        return nodes.getOrNull(index++)
    }

    fun addMapped(node: T) {
        if (mergeNodes) {
            val mergedNode = _mappedNodes.lastOrNull()?.plus(node)
            if (mergedNode != null) {
                _mappedNodes.removeLast()
                _mappedNodes.add(mergedNode)
                return
            }
        }
        _mappedNodes.add(node)
    }

    abstract operator fun T.plus(other: T): T?
}

internal class InlineNodeMapper(
    nodes: List<ASTNode>,
    mergeNodes: Boolean = true,
) : NodeMapper<InlineNode>(nodes, mergeNodes) {
    override fun InlineNode.plus(other: InlineNode): InlineNode? {
        return when {
            this is TextNode && other is TextNode -> this + other
            this is BoldNode && other is BoldNode -> this + other
            this is ItalicNode && other is ItalicNode -> this + other
            this is StrikethroughNode && other is StrikethroughNode -> this + other
            else -> null
        }
    }
}

internal class BlockNodeMapper(
    nodes: List<ASTNode>,
    mergeNodes: Boolean = true,
) : NodeMapper<BlockNode>(nodes, mergeNodes) {
    override fun BlockNode.plus(other: BlockNode): BlockNode? {
        return when {
            this is BlockQuoteNode && other is BlockQuoteNode -> this + other
            this is ParagraphNode && other is ParagraphNode -> this + other
            this is WhitespaceNode && other is WhitespaceNode -> WhitespaceNode
            else -> null
        }
    }
}