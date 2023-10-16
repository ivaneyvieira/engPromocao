package br.com.astrosoft.promocao.viewmodel.produto

import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.promocao.model.beans.FiltroProduto
import br.com.astrosoft.promocao.model.beans.Loja
import br.com.astrosoft.promocao.model.beans.Produtos

sealed class TabAbstractProdutoViewModel<T : ITabAbstractProdutoViewModel>(val viewModel: ProdutoViewModel) {
    abstract val subView: T

    fun updateView() {
        val filtro = subView.filtro()
        val resultList = findPrecoAlteracao(filtro)
        subView.updateGrid(resultList)
    }

    abstract fun findPrecoAlteracao(filtro: FiltroProduto): List<Produtos>

    abstract fun todoEstoque(): Boolean
    fun allLojas() = Loja.allLojas()
}

interface ITabAbstractProdutoViewModel : ITabView {
    fun filtro(): FiltroProduto
    fun updateGrid(itens: List<Produtos>)
}