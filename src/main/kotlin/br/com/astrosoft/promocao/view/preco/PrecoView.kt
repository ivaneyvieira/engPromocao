package br.com.astrosoft.promocao.view.preco

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.view.PromocaoLayout
import br.com.astrosoft.promocao.view.preco.TabBasePreco
import br.com.astrosoft.promocao.view.preco.TabPrecoPreco
import br.com.astrosoft.promocao.view.preco.TabPromocaoPreco
import br.com.astrosoft.promocao.viewmodel.preco.IPrecoView
import br.com.astrosoft.promocao.viewmodel.preco.PrecoViewModel
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(value = "preco", layout = PromocaoLayout::class)
@PageTitle("Preco")
@CssImport("./styles/gridTotal.css", themeFor = "vaadin-grid")
class PrecoView : ViewLayout<PrecoViewModel>(), IPrecoView {
  override val viewModel: PrecoViewModel = PrecoViewModel(this)
  override val tabBaseViewModel = TabBasePreco(viewModel.tabBaseViewModel)
  override val tabPrecoViewModel = TabPrecoPreco(viewModel.tabPrecoViewModel)
  override val tabPromocaoViewModel = TabPromocaoPreco(viewModel.tabPromocaoViewModel)

  override fun isAccept(user: IUser): Boolean {
    val userSaci = user as? UserSaci ?: return false
    return userSaci.menuPreco
  }

  init {
    addTabSheat(viewModel)
  }
}

