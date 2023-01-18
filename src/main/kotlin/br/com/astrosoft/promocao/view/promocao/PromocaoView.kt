package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.view.PromocaoLayout
import br.com.astrosoft.promocao.viewmodel.promocao.IPromocaoView
import br.com.astrosoft.promocao.viewmodel.promocao.PromocaoViewModel
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(value = "", layout = PromocaoLayout::class)
@PageTitle("Promoção")
@CssImport("./styles/gridTotal.css", themeFor = "vaadin-grid")
class PromocaoView : ViewLayout<PromocaoViewModel>(), IPromocaoView {
  override val viewModel: PromocaoViewModel = PromocaoViewModel(this)
  override val tabBaseViewModel = TabBasePromocao(viewModel.tabBaseViewModel)
  override val tab99ViewModel = Tab99(viewModel.tab99ViewModel)
  override val tabPromocaoViewModel = TabPromocao(viewModel.tabPromocaoViewModel)
  override val tabSemPromocaoViewModel = TabSemPromocao(viewModel.tabSemPromocaoViewModel)
  override val tabPrecificacaoViewModel = TabPrecificacao(viewModel.tabPrecificacaoViewModel)

  override fun isAccept(user: IUser): Boolean {
    val userSaci = user as? UserSaci ?: return false
    return userSaci.menuPromocao
  }

  init {
    addTabSheat(viewModel)
  }
}

