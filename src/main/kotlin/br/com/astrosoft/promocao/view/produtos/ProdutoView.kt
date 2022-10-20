package br.com.astrosoft.promocao.view.produtos

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.view.PromocaoLayout
import br.com.astrosoft.promocao.viewmodel.produto.IProdutoView
import br.com.astrosoft.promocao.viewmodel.produto.ProdutoViewModel
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(value = "produto", layout = PromocaoLayout::class)
@PageTitle("Produto")
@CssImport("./styles/gridTotal.css", themeFor = "vaadin-grid")
class ProdutoView : ViewLayout<ProdutoViewModel>(), IProdutoView {
  override val viewModel: ProdutoViewModel = ProdutoViewModel(this)
  override val tabBaseViewModel = TabBaseProduto(viewModel.tabBaseViewModel)

  override fun isAccept(user: IUser): Boolean {
    val userSaci = user as? UserSaci ?: return false
    return userSaci.menuProduto
  }

  init {
    addTabSheat(viewModel)
  }
}

