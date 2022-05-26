package br.com.astrosoft.Garantia.viewmodel.garantia

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel
import br.com.astrosoft.promocao.viewmodel.garantia.ITabBaseGarantiaViewModel
import br.com.astrosoft.promocao.viewmodel.garantia.TabBaseGarantiaViewModel

class GarantiaViewModel(view: IGarantiaView) : ViewModel<IGarantiaView>(view) {
  val tabBaseGarantiaViewModel = TabBaseGarantiaViewModel(this)

  override fun listTab() = listOf(view.tabBaseGarantiaViewModel)
}

interface IGarantiaView : IView {
  val tabBaseGarantiaViewModel: ITabBaseGarantiaViewModel
}

