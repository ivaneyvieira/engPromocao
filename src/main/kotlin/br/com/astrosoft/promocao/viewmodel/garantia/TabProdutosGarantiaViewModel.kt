package br.com.astrosoft.promocao.viewmodel.garantia

import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.framework.viewmodel.fail
import br.com.astrosoft.promocao.model.beans.FiltroGarantia
import br.com.astrosoft.promocao.model.beans.InfoModifica
import br.com.astrosoft.promocao.model.beans.ProdutoValidade

class TabInserirGarantiaViewModel(val viewModel: GarantiaViewModel) {
  val subView
    get() = viewModel.view.tabInserirGarantiaViewModel

  fun updateView() {
    val lista = ProdutoValidade.findAll(subView.filtro())
    subView.updateGrid(lista)
  }

  fun modificaValidade() = viewModel.exec {
    val seleciona = subView.listSelected()
    if (seleciona.isEmpty()) fail("Nenhum item selecionado")
    else {
      seleciona.forEach {
        it.modifica(subView.infoModifica())
      }
      updateView()
    }
  }
}

interface ITabInserirGarantiaViewModel : ITabView {
  fun filtro(): FiltroGarantia
  fun updateGrid(itens: List<ProdutoValidade>)
  fun listSelected(): List<ProdutoValidade>

  fun infoModifica(): InfoModifica
}