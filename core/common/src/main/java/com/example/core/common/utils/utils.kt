package com.example.core.common.utils

fun extractYear(dateString: String): String? {
    val regex = """^(\d{4})""".toRegex()
    val matchResult = regex.find(dateString)
    return matchResult?.groups?.get(1)?.value
}