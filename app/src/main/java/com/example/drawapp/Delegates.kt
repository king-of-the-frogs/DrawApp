package com.example.drawapp


import android.graphics.Paint
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.drawapp.base.Item

import com.example.drawapp.items.SIZE
import com.example.drawapp.items.TOOLS
import com.example.namespace.R
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer


fun colorAdapterDelegate(
    onClick: (Int) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<ToolItem.ColorModel, Item>(
        R.layout.item_palette
    ) {
        val color: ImageView = findViewById(R.id.ivColor)
        itemView.setOnClickListener {
            onClick(adapterPosition)
        }

        bind { list ->
            color.setColorFilter(
                context.resources.getColor(item.color, null),
                PorterDuff.Mode.SRC_IN
            )
        }
    }

fun sizeAdapterDelegate(
    onClick: (Int) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<ToolItem.SizeModel, Item>(
        R.layout.item_size
    ) {
        val size: ImageView = findViewById(R.id.ivSize)
        val sizeText: TextView = findViewById(R.id.tvSizeText)
        itemView.setOnClickListener {
            onClick(adapterPosition)
        }
        bind {  list ->
            size.setOnClickListener {
                // Получаем текущий размер кисти из модели данных
                val currentSize = item.size
                // Получаем следующий размер кисти из перечисления
                val nextSize = SIZE.values().find { it.value > currentSize }?.value ?: SIZE.SMALL.value
                // Устанавливаем следующий размер кисти в модели данных
                item.size = nextSize
                // Обновляем текст на элементе списка с размером кисти
                sizeText.text = item.size.toString()
            }
            sizeText.text = item.size.toString()
        }
    }


fun toolsAdapterDelegate(
    onToolsClick: (Int) -> Unit
): AdapterDelegate<List<Item>> = adapterDelegateLayoutContainer<ToolItem.ToolModel, Item>(
    R.layout.item_tools
) {
    val ivTools = findViewById<ImageView>(R.id.ivTools)
    val tvToolsText = findViewById<TextView>(R.id.tvToolsText)

    bind { list ->
        ivTools.setImageResource(item.type.value)

        if (tvToolsText.visibility == View.VISIBLE) {
            tvToolsText.visibility = View.GONE
        }

        when (item.type) {
            TOOLS.NORMAL -> {
                if (item.selectedTool == TOOLS.NORMAL) {
                    ivTools.setBackgroundResource(R.drawable.bg_selected)
                } else {
                    ivTools.setBackgroundResource(android.R.color.transparent)
                }
            }

            TOOLS.DASH -> {
                if (item.selectedTool == TOOLS.DASH) {
                    ivTools.setBackgroundResource(R.drawable.bg_selected)
                } else {
                    ivTools.setBackgroundResource(android.R.color.transparent)
                }
            }

            TOOLS.SIZE -> {
                ivTools.setOnClickListener {
                    // Получаем текущий размер кисти из модели данных
                    val currentSize = item.selectedSize.value
                    // Получаем следующий размер кисти из перечисления
                    val nextSize = SIZE.values().find { it.value > currentSize } ?: SIZE.SMALL
                    // Устанавливаем следующий размер кисти в модели данных
                    item.selectedSize = nextSize
                    // Обновляем текст на элементе списка инструментов с размером кисти
                    tvToolsText.text = item.selectedSize.value.toString()

                }
                tvToolsText.visibility = View.VISIBLE
                tvToolsText.text = item.selectedSize.value.toString()
            }

            TOOLS.PALETTE -> {
                ivTools.setColorFilter(
                    context.resources.getColor(item.selectedColor.value, null),
                    PorterDuff.Mode.SRC_IN
                )
            }

        }


        itemView.setOnClickListener {
            onToolsClick(adapterPosition)
        }
    }
}

