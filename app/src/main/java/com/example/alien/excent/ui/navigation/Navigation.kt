package com.example.alien.excent.ui.navigation

interface Navigation {

    fun navigateToAction(uiAction: UiAction)

    fun <T> navigateToAction(action: UiAction, argument: T, nameArgument: String = "")

    fun navigateBack()

    fun finishActivity()
}