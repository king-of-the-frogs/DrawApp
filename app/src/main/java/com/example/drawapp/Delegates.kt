package com.example.drawapp

import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import com.example.drawapp.base.Item
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
        itemView.setOnClickListener { onClick(adapterPosition) }

        bind { list ->
            color.setColorFilter(
                context.resources.getColor(item.color, null),
                PorterDuff.Mode.SRC_IN
            )
        }
    }

fun toolsAdapterDelegate(
    onToolsClick: (Int) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<ToolItem.ToolModel, Item>(
        R.layout.item_tools
    ) {
        val ivTool: ImageView = findViewById(R.id.ivTools)
        bind { list ->
            ivTool.setImageResource(item.type.value)

  //          if (tvToolsText.visibility == View.VISIBLE) {
 //               tvToolsText.visibility = View.GONE
  //          }

            when (item.type) {

                TOOLS.PALETTE -> {
                    ivTool.setColorFilter(
                        context.resources.getColor(item.selectedColor.value, null),
                        PorterDuff.Mode.SRC_IN
                    )
                }

//                TOOLS.SIZE -> {
 //                   tvToolsText.visibility = View.VISIBLE
 //                   tvToolsText.text = item.selectedSize.value.toString()
  //              }
                else -> {
                    if (item.isSelected) {
                        ivTool.setBackgroundResource(R.drawable.bg_selected)
                    } else {
                        ivTool.setBackgroundResource(android.R.color.transparent)
                    }
                }
            }
        }

        itemView.setOnClickListener {
            onToolsClick(adapterPosition)
        }
    }