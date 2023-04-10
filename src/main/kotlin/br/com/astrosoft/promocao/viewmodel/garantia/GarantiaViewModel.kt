package br.com.astrosoft.promocao.viewmodel.garantia

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class GarantiaViewModel(view: IGarantiaView) : ViewModel<IGarantiaView>(view) {
  val tabBaseGarantiaViewModel = TabBaseGarantiaViewModel(this)
  val tabValidadeGarantiaViewModel = TabValidadeGarantiaViewModel(this)
  val tabInserirGarantiaViewModel = TabInserirGarantiaViewModel(this)

  override fun listTab() =
    listOf(view.tabBaseGarantiaViewModel, view.tabInserirGarantiaViewModel, view.tabValidadeGarantiaViewModel)
}

interface IGarantiaView : IView {
  val tabBaseGarantiaViewModel: ITabBaseGarantiaViewModel
  val tabValidadeGarantiaViewModel: ITabValidadeGarantiaViewModel
  val tabInserirGarantiaViewModel: ITabInserirGarantiaViewModel
}

