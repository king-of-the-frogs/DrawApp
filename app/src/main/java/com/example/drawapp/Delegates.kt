package com.example.drawapp


import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.Log
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
        val size: TextView = findViewById(R.id.ivSize)
        val sizeText: TextView = findViewById(R.id.tvSizeText)
        itemView.setOnClickListener {
            onClick(adapterPosition)
        }
        bind {  list ->
            when (item.size) {
                4 -> {
                    sizeText.text = 4.toString()
                    size.text = "4"
                }
                16 -> {
                    sizeText.text = 16.toString()
                    size.text = "16"
                }
                32 -> {
                    sizeText.text = 32.toString()
                    size.text = "32"
                }
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

        when (item.selectedTool) {
            TOOLS.NORMAL -> {
            }

            TOOLS.DASH -> {
            }

            TOOLS.SIZE -> {
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






