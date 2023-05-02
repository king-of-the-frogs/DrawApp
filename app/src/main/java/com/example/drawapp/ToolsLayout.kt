package com.example.drawapp

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.namespace.R
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter


class ToolsLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private var onClick: (Int) -> Unit = {}

    private val adapterDelegate = ListDelegationAdapter(
        colorAdapterDelegate {
            onClick(it)
        },

        sizeAdapterDelegate {
            onClick(it)
        },

        toolsAdapterDelegate {
            onClick(it)
        }
    )

    private val toolsList: RecyclerView = RecyclerView(context).apply {
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        setAdapterAndCleanupOnDetachFromWindow(adapterDelegate)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addView(toolsList)
    }

    fun render(list: List<ToolItem>) {
        adapterDelegate.setData(list)
    }

    fun setOnClickListener(onClick: (Int) -> Unit) {
        this.onClick = onClick
    }
}