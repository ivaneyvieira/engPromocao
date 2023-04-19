package br.com.astrosoft.promocao.viewmodel.garantia

import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.promocao.model.beans.FiltroValidadeEntrada
import br.com.astrosoft.promocao.model.beans.ValidadeEntrada

class TabControleValidadeViewModel(val viewModel: GarantiaViewModel) {
  val subView
    get() = viewModel.view.tabControleValidade

  fun updateView() {
    val lista = ValidadeEntrada.findAll(subView.filtro())
    subView.updateGrid(lista)
  }
}

interface ITabControleValidadeViewModel : ITabView {
  fun updateGrid(itens: List<ValidadeEntrada>)
  fun listSelected(): List<ValidadeEntrada>
  fun filtro(): FiltroValidadeEntrada
}