package com.example.drawapp

import com.example.drawapp.base.BaseViewModel
import com.example.drawapp.base.Event
import com.example.drawapp.items.COLOR
import com.example.drawapp.items.SIZE
import com.example.drawapp.items.TOOLS

class CanvasViewModel : BaseViewModel<ViewState>() {
    override fun initialViewState(): ViewState =
        ViewState(
            colorList = enumValues<COLOR>().map { ToolItem.ColorModel(it.value) },
            toolsList = enumValues<TOOLS>().map { ToolItem.ToolModel(it) },
            isPaletteVisible = false,
            canvasViewState = CanvasViewState(color = COLOR.BLACK, size = SIZE.MEDIUM, tools = TOOLS.NORMAL),
            isToolsVisible = false,
        )

    init {
        processDataEvent(DataEvent.OnSetDefaultTools(tool = TOOLS.NORMAL, color = COLOR.BLACK))
    }

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

                        return ViewState(
                            colorList = previousState.colorList,
                            toolsList = toolsList,
                            isPaletteVisible = previousState.isPaletteVisible,
                            canvasViewState = CanvasViewState(color = previousState.canvasViewState.color, size = previousState.canvasViewState.size, tools = TOOLS.values()[event.index]),
                            isToolsVisible = previousState.isToolsVisible,
                        )
                    }
                }
            }

            is UiEvent.OnPaletteClicked -> {
                val selectedColor = COLOR.values()[event.index]

                val toolsList = previousState.toolsList.map {
                    if (it.type == TOOLS.PALETTE) {
                        it.copy(selectedColor = selectedColor)
                    } else {
                        it
                    }
                }

                return ViewState(
                    colorList = previousState.colorList,
                    toolsList = toolsList,
                    isPaletteVisible = previousState.isPaletteVisible,
                    canvasViewState = CanvasViewState(color = selectedColor, size = previousState.canvasViewState.size, tools = previousState.canvasViewState.tools),
                    isToolsVisible = previousState.isToolsVisible,
                )
            }

            is DataEvent.OnSetDefaultTools -> {
                val toolsList = previousState.toolsList.map { model ->
                    if (model.type == event.tool) {
                        model.copy(isSelected = true, selectedColor = event.color)
                    } else {
                        model.copy(isSelected = false)
                    }
                }

                return previousState.copy(
                    toolsList = toolsList
                )
            }

            else -> return null
        }
    }
}