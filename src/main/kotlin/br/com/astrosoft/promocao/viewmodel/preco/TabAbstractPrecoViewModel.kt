package br.com.astrosoft.promocao.viewmodel.preco

import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.promocao.model.beans.FiltroPrecoAlteracao
import br.com.astrosoft.promocao.model.beans.PrecoAlteracao

sealed class TabAbstractPrecoViewModel<T : ITabAbstractPrecoViewModel>(val viewModel: PrecoViewModel) {
  abstract val subView: T

  fun updateView() {
    val filtro = subView.filtro()
    val resultList = findPrecoAlteracao(filtro)
    subView.updateGrid(resultList)
  }

  abstract fun findPrecoAlteracao(filtro: FiltroPrecoAlteracao): List<PrecoAlteracao>
}

interface ITabAbstractPrecoViewModel : ITabView {
  fun filtro(): FiltroPrecoAlteracao
  fun updateGrid(itens: List<PrecoAlteracao>)
}