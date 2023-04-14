package br.com.astrosoft.promocao.viewmodel.garantia

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class GarantiaViewModel(view: IGarantiaView) : ViewModel<IGarantiaView>(view) {
  val tabBaseGarantia = TabBaseGarantiaViewModel(this)
  val tabValidadeGarantia = TabValidadeGarantiaViewModel(this)
  val tabValidadeEntrada = TabValidadeEntradaViewModel(this)
  val tabInserirGarantia = TabInserirGarantiaViewModel(this)

  override fun listTab() =
    listOf(view.tabBaseGarantia, view.tabInserirGarantia, view.tabValidadeGarantia, view.tabValidadeEntrada)
}

interface IGarantiaView : IView {
  val tabBaseGarantia: ITabBaseGarantiaViewModel
  val tabValidadeGarantia: ITabValidadeGarantiaViewModel
  val tabValidadeEntrada: ITabValidadeEntradaViewModel
  val tabInserirGarantia: ITabInserirGarantiaViewModel
}

