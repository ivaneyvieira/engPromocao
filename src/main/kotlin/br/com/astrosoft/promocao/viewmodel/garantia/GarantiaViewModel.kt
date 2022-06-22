package br.com.astrosoft.promocao.viewmodel.garantia

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class GarantiaViewModel(view: IGarantiaView) : ViewModel<IGarantiaView>(view) {
  val tabBaseGarantiaViewModel = TabBaseGarantiaViewModel(this)
  val tabProdutoGarantiaViewModel = TabProdutoGarantiaViewModel(this)

  override fun listTab() = listOf(view.tabBaseGarantiaViewModel, view.tabProdutoGarantiaViewModel)
}

interface IGarantiaView : IView {
  val tabBaseGarantiaViewModel: ITabBaseGarantiaViewModel
  val tabProdutoGarantiaViewModel: ITabProdutoGarantiaViewModel
}

