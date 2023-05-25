package br.com.astrosoft.promocao.viewmodel.garantia

import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.promocao.model.beans.FiltroValidadeEntrada
import br.com.astrosoft.promocao.model.beans.ValidadeEntrada
import br.com.astrosoft.promocao.model.beans.VendaEntrada

class TabVendaViewModel(val viewModel: GarantiaViewModel) {
    val subView
        get() = viewModel.view.tabVenda

    fun updateView() {
        val lista = VendaEntrada.findAll(subView.filtro())
        subView.updateGrid(lista)
    }
}

interface ITabVendaViewModel : ITabView {
    fun updateGrid(itens: List<VendaEntrada>)
    fun listSelected(): List<VendaEntrada>
    fun filtro(): FiltroValidadeEntrada
}