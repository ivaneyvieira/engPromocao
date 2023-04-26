package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao
import br.com.astrosoft.promocao.model.beans.FiltroPrecoPromocao
import br.com.astrosoft.promocao.model.beans.PrecoPromocao

sealed class TabAbstractPromocaoViewModel<T : ITabAbstractPromocaoViewModel>(val viewModel: PromocaoViewModel) {
    abstract val subView: T
    abstract val tipoTab: List<ETipoListaPromocao>

    fun updateView() {
        val filtro = subView.filtro()
        val resultList = PrecoPromocao.find(filtro)
        subView.updateGrid(resultList)
    }
}

interface ITabAbstractPromocaoViewModel : ITabView {
    fun filtro(): FiltroPrecoPromocao
    fun updateGrid(itens: List<PrecoPromocao>)
    fun listSelected(): List<PrecoPromocao>
}