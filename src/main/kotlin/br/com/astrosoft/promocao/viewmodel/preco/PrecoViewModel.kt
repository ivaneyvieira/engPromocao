package br.com.astrosoft.promocao.viewmodel.preco

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class PrecoViewModel(view: IPrecoView) : ViewModel<IPrecoView>(view) {
  val tabBaseViewModel = TabBaseAlteradoViewModel(this)
  val tabPrecoViewModel = TabPrecoAlteradoViewModel(this)
  val tabPromocaoViewModel = TabPromocaoAlteradoViewModel(this)

  override fun listTab() =
    listOf(view.tabBaseViewModel, view.tabPrecoViewModel, view.tabPromocaoViewModel)
}

interface IPrecoView : IView {
  val tabBaseViewModel: ITabBaseAlteradoViewModel
  val tabPrecoViewModel: ITabPrecoAlteradoViewModel
  val tabPromocaoViewModel: ITabPromocaoAlteradoViewModel
}

