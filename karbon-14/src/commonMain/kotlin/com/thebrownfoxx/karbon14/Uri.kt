package com.thebrownfoxx.karbon14

public data class Uri(val value: String) {
    override fun toString(): String = "Uri($value.)"
}

public fun https(url: String): Uri = Uri("https://$url")
public fun http(url: String): Uri = Uri("http://$url")
public fun email(address: String): Uri = Uri("mailto:$address")
public fun phone(number: String): Uri = Uri("tel:$number")
public fun path(value: String): Uri = Uri(value)