package br.com.astrosoft.promocao.viewmodel.produto

import br.com.astrosoft.promocao.model.beans.FiltroProduto
import br.com.astrosoft.promocao.model.beans.Produtos

class TabEstoqueGeralViewModel(viewModel: ProdutoViewModel) :
    TabAbstractProdutoViewModel<ITabEstoqueGeralViewModel>(viewModel) {
    override val subView
        get() = viewModel.view.tabEstoqueGeralViewModel

    override fun findPrecoAlteracao(filtro: FiltroProduto): List<Produtos> {
        return Produtos.find(filtro, false)
    }

    override fun todoEstoque(): Boolean {
        return true
    }
}

interface ITabEstoqueGeralViewModel : ITabAbstractProdutoViewModel