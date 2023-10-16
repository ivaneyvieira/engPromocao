package br.com.astrosoft.promocao.viewmodel.garantia

import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.promocao.model.beans.FiltroEstoqueApp
import br.com.astrosoft.promocao.model.beans.GarantiaEstoqueApp

class TabEstoqueAppGarantiaViewModel(val viewModel: GarantiaViewModel) {
  val subView
    get() = viewModel.view.tabEstoqueAppGarantia

  fun updateView() {
    val lista = GarantiaEstoqueApp.findAll(subView.filtro())
    subView.updateGrid(lista)
  }
}

interface ITabEstoqueAppGarantiaViewModel : ITabView {
  fun updateGrid(itens: List<GarantiaEstoqueApp>)
  fun listSelected(): List<GarantiaEstoqueApp>
  fun filtro(): FiltroEstoqueApp
}