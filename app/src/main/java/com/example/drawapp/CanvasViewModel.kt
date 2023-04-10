package com.example.drawapp

import com.example.drawapp.base.BaseViewModel
import com.example.drawapp.base.Event

class CanvasViewModel : BaseViewModel<ViewState>() {
    override fun initialViewState(): ViewState =
        ViewState(
            colorList = enumValues<COLOR>().map { ToolItem.ColorModel(it.value) },
            toolsList = enumValues<TOOLS>().map { ToolItem.ToolModel(it) },
            isPaletteVisible = false,
            canvasViewState = CanvasViewState(color = COLOR.BLACK, size = SIZE.MEDIUM, tools = TOOLS.NORMAL),
            isToolsVisible = false,
        )


    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            is UiEvent.OnToolbarClicked -> {
                return previousState.copy(
                    isToolsVisible = !previousState.isToolsVisible,
                    isPaletteVisible = false
                )
            }

            is UiEvent.OnToolsClick -> {
                when (event.index) {
                    TOOLS.PALETTE.ordinal -> {
                        return previousState.copy(isPaletteVisible = !previousState.isPaletteVisible)
                    }
                    else -> {

                        val toolsList = previousState.toolsList.mapIndexed() { index, model ->
                            if (index == event.index) {
                                model.copy(isSelected = true)
                            }
                            else {
                                model.copy(isSelected = false)
                            }
                        }

                        return previousState.copy(
                            toolsList = toolsList
                        )
                    }
                }
            }

            else -> return null
        }
    }
}