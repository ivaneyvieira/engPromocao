package br.com.astrosoft.promocao.view

import br.com.astrosoft.framework.model.Config
import br.com.astrosoft.framework.view.MainLayout
import br.com.astrosoft.promocao.view.promocao.PromocaoView
import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.page.Push
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo

@Theme(value = Lumo::class, variant = Lumo.DARK)
@Push
@JsModule("./styles/shared-styles.js")
class PromocaoLayout : MainLayout() {
  override fun Tabs.menuConfig() {
    menuRoute(VaadinIcon.FORM, "Promoção", PromocaoView::class)
    menuRoute(VaadinIcon.USER, "Usuário", UsuarioView::class, Config.isAdmin)
  }
}