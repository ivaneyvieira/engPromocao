package br.com.astrosoft.framework.view

import br.com.astrosoft.framework.viewmodel.ITabView
import com.github.mvysny.karibudsl.v10.TabSheet
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant

fun TabSheet.tabPanel(tabPanel: ITabView) {
    this.tab {
        tabPanel.createTabComponent()
    }.apply {
        val button = Button(tabPanel.label) {
            tabPanel.updateComponent()
        }
        button.addThemeVariants(ButtonVariant.LUMO_SMALL)
        this.addComponentAsFirst(button)
    }
}



