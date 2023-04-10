package com.example.drawapp

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.example.namespace.R

class CanvasViewState (val color: COLOR, val size: SIZE, val tools: TOOLS)

enum class COLOR(
    @ColorRes
    val value: Int
) {

    BLACK(R.color.dark_900),
    RED(R.color.red_900),
    BLUE(R.color.blue_900),
    GREEN(R.color.green_500);

    companion object {
        private val map = values().associateBy (COLOR::value)
        fun from(color: Int) = map[color] ?: BLACK
    }
}

enum class SIZE(
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

