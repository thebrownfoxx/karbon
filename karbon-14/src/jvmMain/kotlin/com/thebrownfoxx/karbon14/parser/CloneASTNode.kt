package com.thebrownfoxx.karbon14.parser

import org.intellij.markdown.IElementType
import org.intellij.markdown.ast.ASTNode

internal fun ASTNode.clone(
    type : IElementType = this.type,
    startOffset : Int = this.startOffset,
    endOffset : Int = this.endOffset,
    parent: ASTNode? = this.parent,
    children : List<ASTNode> = this.children,
): ASTNode = object : ASTNode {
    override val type = type
    override val startOffset = startOffset
    override val endOffset = endOffset
    override val parent = parent
    override val children = children
}