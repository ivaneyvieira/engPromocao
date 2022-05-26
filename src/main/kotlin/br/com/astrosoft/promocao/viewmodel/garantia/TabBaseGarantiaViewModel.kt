package br.com.astrosoft.promocao.viewmodel.garantia

import br.com.astrosoft.Garantia.viewmodel.garantia.GarantiaViewModel
import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.promocao.model.beans.ComparaValidade
import br.com.astrosoft.promocao.model.beans.ETipoDiferencaGarantia
import br.com.astrosoft.promocao.model.beans.FiltroPrecoPromocao

class TabBaseGarantiaViewModel(val viewModel: GarantiaViewModel) {
  val subView
    get() = viewModel.view.tabBaseGarantiaViewModel

  fun updateView() {

  }
}

interface ITabBaseGarantiaViewModel : ITabView{
  fun filtro(): ETipoDiferencaGarantia
  fun updateGrid(itens: List<ComparaValidade>)
  fun listSelected(): List<ComparaValidade>
}