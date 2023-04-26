package br.com.astrosoft.promocao.viewmodel.garantia

import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.promocao.model.beans.ComparaValidade
import br.com.astrosoft.promocao.model.beans.FiltroValidade

class TabValidadeGarantiaViewModel(val viewModel: GarantiaViewModel) {
    val subView
        get() = viewModel.view.tabValidadeGarantia

    fun updateView() {
        val lista = ComparaValidade.consultaByTipo(subView.filtro())
        subView.updateGrid(lista)
    }
}

interface ITabValidadeGarantiaViewModel : ITabView {
    fun filtro(): FiltroValidade
    fun updateGrid(itens: List<ComparaValidade>)
    fun listSelected(): List<ComparaValidade>
}