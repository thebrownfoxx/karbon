package com.thebrownfoxx.karbon14

import kotlin.jvm.JvmInline

@JvmInline
public value class HeadingLevel(public val value: Int) {
    init {
        require(value in 1..6) { "Heading level must be between 1 and 6" }
    }
}

public val Int.headingLevel: HeadingLevel get() = HeadingLevel(this)