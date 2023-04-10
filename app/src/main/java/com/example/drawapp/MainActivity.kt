package com.example.drawapp

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import com.example.namespace.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    companion object {
        private const val PALETTE_VIEW = 0
        private const val SIZE = 1
        private const val TOOLS_VIEW = 1
    }

    private val viewModel: CanvasViewModel by viewModel()

    private var toolsList: List<ToolsLayout> = listOf()

    private val paletteLayout: ToolsLayout by lazy { findViewById(R.id.paletteLayout) }
    private val toolsLayout: ToolsLayout by lazy { findViewById(R.id.toolLayout) }
    private val sizeLayout: ToolsLayout by lazy { findViewById(R.id.sizeLayout) }
    private val ivTools: ImageView by lazy { findViewById(R.id.ivTools) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolsList = listOf(paletteLayout,toolsLayout,sizeLayout)
        viewModel.viewState.observe(this, ::render)

        paletteLayout.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnPaletteClicked(it))
        }

        toolsLayout.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnToolsClick(it))
        }

        ivTools.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnToolbarClicked)
        }

        viewModel.processUiEvent(UiEvent.OnToolsClick(TOOLS.NORMAL.ordinal))
    }

    private fun render(viewState: ViewState) {

        with(toolsList[PALETTE_VIEW]) {
            render(viewState.colorList)
            isVisible = viewState.isPaletteVisible
        }

        with(toolsList[TOOLS_VIEW]) {
            render(viewState.toolsList)
            isVisible = viewState.isToolsVisible
        }
    }
}