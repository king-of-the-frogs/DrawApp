package com.example.drawapp.items

import androidx.annotation.Size

enum class SIZE(
    @Size
    val value: Int
) {
    SMALL(4),
    MEDIUM(16),
    LARGE(32);

    companion object {
        private val map = values().associateBy (SIZE::value)
        fun from(size: Int) = map[size] ?: SMALL
    }
}