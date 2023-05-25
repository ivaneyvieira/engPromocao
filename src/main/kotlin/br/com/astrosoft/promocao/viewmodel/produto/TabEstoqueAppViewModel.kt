package br.com.astrosoft.promocao.viewmodel.produto

import br.com.astrosoft.promocao.model.beans.FiltroProduto
import br.com.astrosoft.promocao.model.beans.Produtos

class TabEstoqueAppViewModel(viewModel: ProdutoViewModel) :
    TabAbstractProdutoViewModel<ITabEstoqueAppViewModel>(viewModel) {
    override val subView
        get() = viewModel.view.tabEstoqueAppViewModel

    override fun findPrecoAlteracao(filtro: FiltroProduto): List<Produtos> {
        return Produtos.find(filtro, true)
    }

    override fun todoEstoque(): Boolean {
        return true
    }
}

interface ITabEstoqueAppViewModel : ITabAbstractProdutoViewModel