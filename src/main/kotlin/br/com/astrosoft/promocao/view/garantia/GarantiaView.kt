package br.com.astrosoft.promocao.view.garantia

import br.com.astrosoft.Garantia.viewmodel.garantia.GarantiaViewModel
import br.com.astrosoft.Garantia.viewmodel.garantia.IGarantiaView
import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.view.PromocaoLayout
import br.com.astrosoft.promocao.view.promocao.TabBase
import br.com.astrosoft.promocao.view.promocao.TabPromocao
import br.com.astrosoft.promocao.view.promocao.TabSemPromocao
import br.com.astrosoft.promocao.viewmodel.promocao.IPromocaoView
import br.com.astrosoft.promocao.viewmodel.promocao.PromocaoViewModel
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(value = "", layout = PromocaoLayout::class)
@PageTitle("Promoção")
@CssImport("./styles/gridTotal.css", themeFor = "vaadin-grid")
class GarantiaView : ViewLayout<GarantiaViewModel>(), IGarantiaView {
  override val viewModel: GarantiaViewModel = GarantiaViewModel(this)
  override val tabBaseGarantiaViewModel = TabBaseGarantia(viewModel.tabBaseGarantiaViewModel)

  override fun isAccept(user: IUser): Boolean {
    val userSaci = user as? UserSaci ?: return false
    return userSaci.menuGarantia
  }

  init {
    addTabSheat(viewModel)
  }
}

