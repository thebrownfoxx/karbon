package com.thebrownfoxx.karbon14.list

import com.thebrownfoxx.karbon14.BlockNode
import com.thebrownfoxx.karbon14.InternalNode

public sealed interface ListNode : InternalNode, BlockNode {
    public override val children: List<ListElementNode>
}

public data class UnorderedListNode(override val children: List<ListElementNode>) : ListNode {
    override fun toTreeBranchString(): String = "UnorderedList"
}

public data class OrderedListNode(
    val startingIndex: Int,
    override val children: List<ListElementNode>,
) : ListNode {
    override fun toTreeBranchString(): String = "OrderedList(startingIndex=$startingIndex)"
}

public sealed interface ListElementNode : InternalNode

public class ListItemNode(override val children: List<BlockNode>) : ListElementNode {
    override fun toTreeBranchString(): String = "Item"
}

public class SubListNode(public val list: ListNode) : ListElementNode {
    override val children: List<ListElementNode> = list.children
    override fun toTreeBranchString(): String = "SubList"
}