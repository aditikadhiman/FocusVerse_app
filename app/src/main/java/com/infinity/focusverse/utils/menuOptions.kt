package com.infinity.focusverse.utils

sealed class MenuOption(val title: String) {
    object Edit : MenuOption("Edit")
    object Delete : MenuOption("Delete")
    object Remove : MenuOption("Remove")
    object CheckAll : MenuOption("Check All")
    object UncheckAll : MenuOption("Uncheck All")
    object RemoveAll : MenuOption("Remove All")
    object AddToFocus : MenuOption("Add to Today's Focus")

    companion object {
        val todayFocusMenu = listOf(CheckAll, UncheckAll, RemoveAll)
        val itemMenu = listOf(Remove)
        val sectionMenu = listOf(Edit, Delete)
        val sectionTopMenu = listOf(RemoveAll)
        val pdfNoteMenu = listOf(AddToFocus, Edit, Remove)
        val videoMenu = listOf(AddToFocus, Remove)
        val subsectionMenu = listOf(Edit, Delete)
        val subsectionTopMenu = listOf(RemoveAll)

        fun fromTitle(title: String): MenuOption? = when (title) {
            Edit.title -> Edit
            Delete.title -> Delete
            Remove.title -> Remove
            CheckAll.title -> CheckAll
            UncheckAll.title -> UncheckAll
            RemoveAll.title -> RemoveAll
            AddToFocus.title -> AddToFocus
            else -> null
        }
    }
}
