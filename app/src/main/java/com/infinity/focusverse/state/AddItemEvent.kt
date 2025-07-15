package com.infinity.focusverse.state

import com.infinity.focusverse.state.addItem.DialogType

sealed class AddItemEvent {
    data class ShowDialog(val dialogType: DialogType) : AddItemEvent()
    data class InputChanged(val value: String) : AddItemEvent()
    object Dismiss : AddItemEvent()
    data class Submit(val sectionId: String, val subSectionId: String) : AddItemEvent()
}