package com.thebrownfoxx.karbon.docs

import com.thebrownfoxx.karbon14.ConsoleTreePrinter
import com.thebrownfoxx.karbon14.FilePrinter
import com.thebrownfoxx.karbon14.Kotlin
import com.thebrownfoxx.karbon14.Markdown
import com.thebrownfoxx.karbon14.https
import com.thebrownfoxx.karbon14.markdown
import com.thebrownfoxx.karbon14.print
import java.io.File

fun generateReadme() {
    markdown {
        description()
        feedback()
        usage()
    }.print(
        ConsoleTreePrinter,
        FilePrinter(File("../README.md")),
    )
}

private fun Markdown.description() {
    h1("Karbon")
    p("A Kotlin DSL for making composable Markdown documents.")
    whitespace()
}

private fun Markdown.feedback() {
    h2("Community")
    block {
        text("This library is still early in development. Feedback can be submitted through ")
        link(https("github.com/thebrownfoxx/karbon/issues"), "GitHub Issues")
        text(". Open-source contributions are very welcome, ")
        text("especially implementations of other Markdown features and flavors.")
    }
    whitespace()
}

private fun Markdown.usage() {
    h2("Usage")
    dependency()
    entryPoint()
    kotlinFeatures()
    composingMarkdown()
    printingMarkdown()
}

private fun Markdown.dependency() {
    block {
        text("Add it as a dependency to your project, hosted in ")
        val mavenCentralUri = https("central.sonatype.com/artifact/com.thebrownfoxx/karbon")
        link(mavenCentralUri, "Maven Central")
        text(".")
    }
    whitespace()
    dependencyCode(Config.version)
    whitespace()
}

private fun Markdown.dependencyCode(version: String) {
    kotlin(
        """
            dependencies {
                implementation("com.thebrownfoxx:karbon:$version")
            }
        """.trimIndent()
    )
}

private fun Markdown.entryPoint() {
    h3("Entry Point")
    block {
        text("You can start writing your Karbon Markdown inside a ")
        inlineCode("markdown")
        text(" block.")
    }
    whitespace()
    entryPointCode()
}

private fun Markdown.entryPointCode() {
    kotlin(
        """
            markdown {
                h1("Headline")
                p {
                    text("The quick brown ")
                    bold("foxx")
                    text(" jumps over the ")
                    italic("lazy dog.")
                }
            }
        """.trimIndent()
    )
}

private fun Markdown.kotlinFeatures() {
    h3("Kotlin features")
    p("You can use all your standard Kotlin features to build your Markdown.")
    whitespace()
    kotlinFeaturesCode()
    whitespace()
}

private fun Markdown.kotlinFeaturesCode() {
    kotlin(
        $$"""
            val roll = (1..6).random()
            markdown {
                p("You rolled a $roll")
                if (roll == 1) p("You won!")
            }
        """.trimIndent()
    )
}

private fun Markdown.composingMarkdown() {
    h3("Composing Markdown")
    block {
        text("You can break Markdown into smaller composable functions via extension functions for the ")
        inlineCode("Markdown")
        text(" interface.")
    }
    whitespace()
    composingMarkdownCode()
    whitespace()
}

private fun Markdown.composingMarkdownCode() {
    kotlin(
        """
            fun Markdown.header() {
                h1("Karbon")
            }

            fun Markdown.body() {
                p("The quick brown foxx.")
                p { link("https://example.com", "Link") }
            }

            fun main() {
                markdown {
                    header()
                    body()
                }
            }
        """.trimIndent()
    )
}

private fun Markdown.printingMarkdown() {
    h3("Printing Markdown")
    block {
        text("You can print the Markdown you wrote with the print function, which accepts ")
        inlineCode("MarkdownPrinter")
        text("s. There are prebuilt printers included in this library including ")
        inlineCode("ConsolePrinter")
        text(" and ")
        inlineCode("FilePrinter")
        text(". You can also extend the ")
        inlineCode("MarkdownPrinter")
        text(" interface to make your own.")
    }
    whitespace()
    printingMarkdownCode()
    whitespace()
    block {
        text("Alternatively, you can access the ")
        inlineCode("value")
        text(" property of a ")
        inlineCode("Markdown")
        text(" instance to get the raw Markdown String.")
    }
}

private fun Markdown.printingMarkdownCode() {
    kotlin(
        """
            markdown {
                text("Print this.")
            }.print(ConsolePrinter)
        """.trimIndent()
    )
}

private fun Markdown.kotlin(code: String) {
    blockCode(Kotlin, code)
}