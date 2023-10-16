package br.com.astrosoft.promocao.viewmodel.garantia

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class GarantiaViewModel(view: IGarantiaView) : ViewModel<IGarantiaView>(view) {
  val tabBaseGarantia = TabBaseGarantiaViewModel(this)
  val tabValidadeGarantia = TabValidadeGarantiaViewModel(this)
  val tabControleValidade = TabControleValidadeViewModel(this)
  val tabEstoqueAppGarantia = TabEstoqueAppGarantiaViewModel(this)
  val tabVenda = TabVendaViewModel(this)
  val tabInserirGarantia = TabInserirGarantiaViewModel(this)

  override fun listTab() =
    listOf(
      view.tabBaseGarantia,
      view.tabInserirGarantia,
      view.tabValidadeGarantia,
      view.tabControleValidade,
      view.tabVenda,
      view.tabEstoqueAppGarantia,
    )
}

interface IGarantiaView : IView {
  val tabBaseGarantia: ITabBaseGarantiaViewModel
  val tabValidadeGarantia: ITabValidadeGarantiaViewModel
  val tabControleValidade: ITabControleValidadeViewModel
  val tabVenda: ITabVendaViewModel
  val tabInserirGarantia: ITabInserirGarantiaViewModel
  val tabEstoqueAppGarantia: ITabEstoqueAppGarantiaViewModel
}

