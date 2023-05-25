package br.com.astrosoft.promocao.viewmodel.produto

import br.com.astrosoft.promocao.model.beans.FiltroProduto
import br.com.astrosoft.promocao.model.beans.Produtos

class TabEstoqueTotalViewModel(viewModel: ProdutoViewModel) :
    TabAbstractProdutoViewModel<ITabEstoqueTotalViewModel>(viewModel) {
    override val subView
        get() = viewModel.view.tabEstoqueTotalViewModel

    override fun findPrecoAlteracao(filtro: FiltroProduto): List<Produtos> {
        return Produtos.find(filtro, false)
    }

    override fun todoEstoque(): Boolean {
        return true
    }
}

interface ITabEstoqueTotalViewModel : ITabAbstractProdutoViewModel