package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class PromocaoViewModel(view: IPromocaoView) : ViewModel<IPromocaoView>(view) {
  val tabBaseViewModel = TabBaseViewModel(this)
  val tabPromocaoViewModel = TabPromocaoViewModel(this)
  val tabSemPromocaoViewModel = TabSemPromocaoViewModel(this)

  override fun listTab() = listOf(view.tabBaseViewModel, view.tabSemPromocaoViewModel, view.tabPromocaoViewModel)
}

interface IPromocaoView : IView {
  val tabBaseViewModel: ITabBaseViewModel
  val tabPromocaoViewModel: ITabPromocaoViewModel
  val tabSemPromocaoViewModel: ITabSemPromocaoViewModel
}

