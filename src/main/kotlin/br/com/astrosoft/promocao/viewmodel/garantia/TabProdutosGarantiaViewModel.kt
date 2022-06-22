package br.com.astrosoft.promocao.viewmodel.garantia

import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.promocao.model.beans.ComparaValidade
import br.com.astrosoft.promocao.model.beans.ProdutoValidade

class TabProdutoGarantiaViewModel(val viewModel: GarantiaViewModel) {
  val subView
    get() = viewModel.view.tabProdutoGarantiaViewModel

  fun updateView() {
    val lista = ProdutoValidade.findAll(subView.filtro())
    subView.updateGrid(lista)
  }
}

interface ITabProdutoGarantiaViewModel : ITabView {
  fun filtro(): String
  fun updateGrid(itens: List<ProdutoValidade>)
  fun listSelected(): List<ProdutoValidade>
}