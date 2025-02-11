package com.thebrownfoxx.karbon14.list

import com.thebrownfoxx.karbon14.ConsolePrinter
import com.thebrownfoxx.karbon14.ConsoleTreePrinter
import com.thebrownfoxx.karbon14.markdown
import com.thebrownfoxx.karbon14.print

private fun main() {
    markdown {
        ol(startingIndex = 5) {
            repeat(100) {
                li { text("Item #$it") }
                ol(startingIndex = it) {
                    repeat(5) {
                        li { text("Item #$it") }
                    }
                }
                ul {
                    repeat(5) {
                        li { text("Item #$it") }
                    }
                }
            }
        }
    }.print(
        ConsoleTreePrinter,
        ConsolePrinter(),
    )
}