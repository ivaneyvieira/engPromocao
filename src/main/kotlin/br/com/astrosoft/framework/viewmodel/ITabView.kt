package br.com.astrosoft.framework.viewmodel

import br.com.astrosoft.framework.model.IUser
import com.vaadin.flow.component.Component

interface ITabView {
    fun isAuthorized(user: IUser): Boolean
    val label: String
    fun updateComponent()
    fun createTabComponent(): Component
}
