package br.com.astrosoft.promocao.viewmodel.produto

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class ProdutoViewModel(view: IProdutoView) : ViewModel<IProdutoView>(view) {
  val tabBaseViewModel = TabBaseProdutoViewModel(this)
  val tabCadastroViewModel = TabCadastroProdutoViewModel(this)

  override fun listTab() = listOf(view.tabBaseViewModel, view.tabCadastroViewModel)
}

interface IProdutoView : IView {
  val tabBaseViewModel: ITabBaseProdutoViewModel
  val tabCadastroViewModel: ITabCadastroProdutoViewModel
}

