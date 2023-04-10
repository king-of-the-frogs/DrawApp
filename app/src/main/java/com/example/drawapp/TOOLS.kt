package com.example.drawapp

import androidx.annotation.DrawableRes
import com.example.namespace.R

enum class TOOLS(
    @DrawableRes
    val value: Int
) {
    NORMAL (R.drawable.ic_baseline_remove_24),
    DASH (R.drawable.ic_baseline_dehaze_24),
    SIZE (R.drawable.ic_baseline_close_fullscreen_24),
    PALETTE(R.drawable.ic_baseline_palette_24)

}