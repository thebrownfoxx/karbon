# Karbon
A Kotlin DSL for making composable Markdown documents.  
  
## Community
This library is still early in development. Feedback can be submitted through [GitHub Issues](https://github.com/thebrownfoxx/karbon/issues). Open-source contributions are very welcome, especially implementations of other Markdown features and flavors.  
  
## Usage
Add it as a dependency to your project, hosted in [Maven Central](https://central.sonatype.com/artifact/com.thebrownfoxx/karbon).  
```kotlin
dependencies {
    implementation("com.thebrownfoxx:karbon:0.4.0")
}
```
  
### Entry Point
You can start writing your Karbon Markdown inside a `markdown` block.  
```kotlin
markdown {
    h1("Headline")
    line {
        text("The quick brown ")
        bold("foxx")
        text(" jumps over the ")
        italic("lazy dog.")
    }
}
```
### Kotlin features
You can use all your standard Kotlin features to build your Markdown.  
```kotlin
val roll = (1..6).random()
markdown {
    line("You rolled a \$roll")
    if (roll == 1) line("You won!")
}
```
  
### Composing Markdown
You can break Markdown into smaller composable functions via extension functions for the `Markdown` interface.  
```kotlin
fun Markdown.header() {
    h1("Karbon")
}

fun Markdown.body() {
    line("The quick brown foxx.")
    line { link("https://example.com", "Link") }
}

fun main() {
    markdown {
        header()
        body()
    }
}
```
  
### Printing Markdown
You can print the Markdown you wrote with the print function, which accepts `MarkdownPrinter`s. There are prebuilt printers included in this library including `ConsolePrinter` and `FilePrinter`. You can also extend the `MarkdownPrinter` interface to make your own.  
```kotlin
markdown {
    text("Print this.")
}.print(ConsolePrinter)
```
Alternatively, you can access the `value`property of a `Markdown` instance to get the raw Markdown String.  
