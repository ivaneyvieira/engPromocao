package br.com.astrosoft.promocao.viewmodel.preco

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class PrecoViewModel(view: IPrecoView) : ViewModel<IPrecoView>(view) {
  val tabBaseViewModel = TabBasePrecoViewModel(this)


  override fun listTab() =
    listOf(view.tabBaseViewModel)
}

interface IPrecoView : IView {
  val tabBaseViewModel: ITabBasePrecoViewModel
}

