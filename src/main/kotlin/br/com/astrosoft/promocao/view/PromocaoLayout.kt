package br.com.astrosoft.promocao.view

import br.com.astrosoft.framework.view.MainLayout
import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.page.Push
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo

@Theme(value = Lumo::class, variant = Lumo.DARK)
@Push
@JsModule("./styles/shared-styles.js")
class PromocaoLayout : MainLayout() {
  override fun Tabs.menuConfig() {
//    menuRoute(FORM, "Promoção", Promoção::class)

  }
}