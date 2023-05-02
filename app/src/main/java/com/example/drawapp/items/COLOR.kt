package com.example.drawapp.items

import androidx.annotation.ColorRes
import com.example.namespace.R

enum class COLOR(
    @ColorRes
    val value: Int
) {

    BLACK(R.color.dark_900),
    RED(R.color.red_900),
    BLUE(R.color.blue_900),
    GREEN(R.color.green_500),
    YELLOW(R.color.yellow_900);

    companion object {
        private val map = values().associateBy (COLOR::value)
        fun from(color: Int) = map[color] ?: BLACK
    }
}