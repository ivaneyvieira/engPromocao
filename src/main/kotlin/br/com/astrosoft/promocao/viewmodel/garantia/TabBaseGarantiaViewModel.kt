package br.com.astrosoft.promocao.viewmodel.garantia

import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.promocao.model.beans.ComparaValidade
import br.com.astrosoft.promocao.model.beans.FiltroValidade

class TabBaseGarantiaViewModel(val viewModel: GarantiaViewModel) {
  val subView
    get() = viewModel.view.tabBaseGarantiaViewModel

  fun updateView() {
    val lista = ComparaValidade.consultaByTipo(subView.filtro())
    subView.updateGrid(lista)
  }
}

interface ITabBaseGarantiaViewModel : ITabView {
  fun filtro(): FiltroValidade
  fun updateGrid(itens: List<ComparaValidade>)
  fun listSelected(): List<ComparaValidade>
}