package com.example.drawapp

import androidx.annotation.ColorRes
import com.example.drawapp.base.Item
import com.example.drawapp.items.COLOR
import com.example.drawapp.items.SIZE
import com.example.drawapp.items.TOOLS

sealed class ToolItem : Item {
    data class ColorModel(@ColorRes val color: Int) : ToolItem()
    data class SizeModel(var size: Int) : ToolItem()
    data class ToolModel(
        val type: TOOLS,
        val selectedTool: TOOLS = TOOLS.NORMAL,
        var selectedSize: SIZE = SIZE.SMALL,
        val selectedColor: COLOR = COLOR.BLACK,
        val isSelected: Boolean = false
    ): ToolItem()
 }