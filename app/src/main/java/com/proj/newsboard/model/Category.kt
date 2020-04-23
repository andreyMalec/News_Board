package com.proj.newsboard.model

enum class Category(val value: String) {
    Business("business"),
    Entertainment("entertainment"),
    General("general"),
    Health("health"),
    Science("science"),
    Sports("sports"),
    Technology("technology");

    companion object {
        fun from(findValue: String): Category = Category.values().first { it.value == findValue }
    }
}