package com.example.alien.excent.module

class ApplicationComponentHolder() {

    companion object {
        val TAG = ApplicationComponentHolder.javaClass.simpleName

        val INSTANCE = ApplicationComponentHolder()

        fun getInstance() : ApplicationComponentHolder {
            return INSTANCE
        }
    }

    private lateinit var component : ApplicationComponent

    fun getComponent() : ApplicationComponent {
        return component
    }

    fun setComponent(applicationComponent: ApplicationComponent) {
        this.component = applicationComponent
    }
}