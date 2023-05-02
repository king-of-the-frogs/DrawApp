package com.example.drawapp

import android.annotation.SuppressLint
import android.graphics.Color.BLACK
import android.graphics.PorterDuff
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.drawapp.base.Item
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
        val tvToolsText: TextView = findViewById(R.id.tvToolsText)
        bind { list ->
            tvToolsText.text = item.size.toString()
            itemView.setOnClickListener {
                onClick(adapterPosition)
            }
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