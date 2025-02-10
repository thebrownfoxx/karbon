package com.thebrownfoxx.karbon14

import kotlin.jvm.JvmInline

@JvmInline
public value class HeaderLevel(public val value: Int) {
    init {
        require(value in 1..6) { "Header level must be between 1 and 6" }
    }
}

public val Int.headerLevel: HeaderLevel get() = HeaderLevel(this)