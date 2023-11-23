package com.example.request

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
data class Addition(var number1: Int, var number2: Int) {
    override fun toString(): String {
        return "Addition(number1=$number1, number2=$number2)"
    }
}

