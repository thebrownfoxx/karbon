package com.thebrownfoxx.karbon14

public data class CodeLanguage(val name: String) {
    override fun toString(): String = "CodeLanguage($name)"
}

// TODO: Pre-write popular languages here:

public val Kotlin: CodeLanguage = CodeLanguage("kotlin")