package com.thebrownfoxx.karbon14.parser

import com.thebrownfoxx.karbon14.RootNode

public interface MarkdownParser {
    public val fullText: String
    public fun parseText(): RootNode
}