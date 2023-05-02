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
            sizeList = enumValues<SIZE>().map { ToolItem.SizeModel(it.value) },
            isPaletteVisible = false,
            isBrushSizeChangerVisible = false,
            canvasViewState = CanvasViewState(color = COLOR.BLACK, size = SIZE.SMALL, tools = TOOLS.NORMAL),
            isToolsVisible = false,
        )

    init {
        processDataEvent(DataEvent.OnSetDefaultTools(tool = TOOLS.NORMAL, color = COLOR.BLACK, size = SIZE.SMALL))
    }

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            is UiEvent.OnToolbarClicked -> {
                return previousState.copy(
                    isToolsVisible = !previousState.isToolsVisible,
                    isPaletteVisible = false,
                    isBrushSizeChangerVisible = false
                )
            }

            is UiEvent.OnToolsClick -> {
                when (event.index) {
                    TOOLS.SIZE.ordinal -> {
                        return previousState.copy(isBrushSizeChangerVisible = !previousState.isBrushSizeChangerVisible)
                    }
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
                            sizeList = previousState.sizeList,
                            toolsList = toolsList,
                            isPaletteVisible = previousState.isPaletteVisible,
                            canvasViewState = CanvasViewState(color = previousState.canvasViewState.color, size = previousState.canvasViewState.size, tools = TOOLS.values()[event.index]),
                            isToolsVisible = previousState.isToolsVisible,
                            isBrushSizeChangerVisible = previousState.isBrushSizeChangerVisible
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
                    sizeList = previousState.sizeList,
                    toolsList = toolsList,
                    isPaletteVisible = previousState.isPaletteVisible,
                    canvasViewState = CanvasViewState(color = selectedColor, size = previousState.canvasViewState.size, tools = previousState.canvasViewState.tools),
                    isToolsVisible = previousState.isToolsVisible,
                    isBrushSizeChangerVisible = previousState.isBrushSizeChangerVisible
                )
            }

            is UiEvent.OnSizeClick -> {
                val selectedSize = SIZE.values()[event.index]

                val toolsList = previousState.toolsList.map {
                    if (it.type == TOOLS.SIZE) {
                        it.copy(selectedSize = selectedSize)
                    } else {
                        it
                    }
                }

                return ViewState(
                    colorList = previousState.colorList,
                    sizeList = previousState.sizeList,
                    toolsList = toolsList,
                    isPaletteVisible = previousState.isPaletteVisible,
                    canvasViewState = CanvasViewState(color = previousState.canvasViewState.color, size = selectedSize, tools = previousState.canvasViewState.tools),
                    isToolsVisible = previousState.isToolsVisible,
                    isBrushSizeChangerVisible = previousState.isBrushSizeChangerVisible
                )
            }

            is DataEvent.OnSetDefaultTools -> {
                val toolsList = previousState.toolsList.map { model ->
                    if (model.type == event.tool) {
                        model.copy(isSelected = true, selectedColor = event.color, selectedSize = event.size)
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