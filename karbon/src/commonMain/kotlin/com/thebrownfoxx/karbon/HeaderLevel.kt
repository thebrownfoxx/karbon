package com.thebrownfoxx.karbon

import kotlin.jvm.JvmInline

@JvmInline
value class HeaderLevel(val value: Int) {
    init {
        require(value in 1..6) { "Header level must be between 1 and 6" }
    }
}

val Int.headerLevel get() = HeaderLevel(this)