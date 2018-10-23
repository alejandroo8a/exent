package com.example.alien.excent.ui.navigation

interface Navigation {

    fun navigateToAction(uiAction: UiAction)

    fun navigateBack()

    fun finishActivity()
}