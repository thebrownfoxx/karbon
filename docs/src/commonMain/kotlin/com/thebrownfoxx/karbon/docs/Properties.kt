package com.thebrownfoxx.karbon.docs

import java.io.FileInputStream
import java.util.Properties

object Config {
    private val versionProps by lazy {
        Properties().also {
            it.load(FileInputStream("../gradle.properties"))
        }
    }

    val version by lazy {
        versionProps["version"]?.toString() ?: "no version"
    }
}