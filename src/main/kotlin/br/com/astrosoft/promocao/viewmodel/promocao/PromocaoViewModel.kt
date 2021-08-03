package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class PromocaoViewModel(view: IPromocaoView) : ViewModel<IPromocaoView>(view) {
  val tabBaseViewModel = TabBaseViewModel(this)
  val tabPromocaoViewModel = TabPromocaoViewModel(this)

  override fun listTab() = listOf(view.tabBaseViewModel, view.tabPromocaoViewModel)
}

interface IPromocaoView : IView {
  val tabBaseViewModel: ITabBaseViewModel
  val tabPromocaoViewModel: ITabPromocaoViewModel
}

