package br.com.astrosoft.promocao.viewmodel.preco

import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao
import br.com.astrosoft.promocao.model.beans.FiltroPrecoPromocao
import br.com.astrosoft.promocao.model.beans.PrecoPromocao

sealed class TabAbstractPrecoViewModel<T : ITabAbstractPrecoViewModel>(val viewModel: PrecoViewModel) {
  abstract val subView: T
  abstract val tipoTab: List<ETipoListaPromocao>

  fun updateView() {
    val filtro = subView.filtro()
    val resultList = PrecoPromocao.find(filtro)
    subView.updateGrid(resultList)
  }
}

interface ITabAbstractPrecoViewModel : ITabView {
  fun filtro(): FiltroPrecoPromocao
  fun updateGrid(itens: List<PrecoPromocao>)
  fun listSelected(): List<PrecoPromocao>
}