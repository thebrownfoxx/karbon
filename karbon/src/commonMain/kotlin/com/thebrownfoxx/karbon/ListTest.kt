package com.thebrownfoxx.karbon

fun main() {
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
    }.print()
}