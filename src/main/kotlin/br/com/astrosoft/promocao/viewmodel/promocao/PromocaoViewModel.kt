package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class PromocaoViewModel(view: IPromocaoView) : ViewModel<IPromocaoView>(view) {
  val tabBaseViewModel = TabBasePromocaoViewModel(this)
  val tab99ViewModel = Tab99ViewModel(this)
  val tabPromocaoViewModel = TabPromocaoViewModel(this)
  val tabSemPromocaoViewModel = TabSemPromocaoViewModel(this)

  override fun listTab() = listOf(view.tabBaseViewModel, view.tabSemPromocaoViewModel, view.tabPromocaoViewModel)
}

interface IPromocaoView : IView {
  val tabBaseViewModel: ITabBasePromocaoViewModel
  val tab99ViewModel: ITab99ViewModel
  val tabPromocaoViewModel: ITabPromocaoViewModel
  val tabSemPromocaoViewModel: ITabSemPromocaoViewModel
}

