package com.thebrownfoxx.karbon14.parser

import com.thebrownfoxx.karbon14.ConsolePrinter
import com.thebrownfoxx.karbon14.markdown
import com.thebrownfoxx.karbon14.print

private fun main() {
    markdown {
        paragraph("Hi")
        whitespace()
        paragraph("Hello")
    }.print(ConsolePrinter())
}