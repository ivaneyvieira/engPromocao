package br.com.astrosoft.promocao.viewmodel.produto

import br.com.astrosoft.promocao.model.beans.FiltroProduto
import br.com.astrosoft.promocao.model.beans.Produtos

class TabEstoqueAppEntradaViewModel(viewModel: ProdutoViewModel) :
  TabAbstractProdutoViewModel<ITabEstoqueAppEntradaViewModel>(viewModel) {
  override val subView
    get() = viewModel.view.tabEstoqueAppEntradaViewModel

  override fun findPrecoAlteracao(filtro: FiltroProduto): List<Produtos> {
    return Produtos.find(filtro, true)
  }

  override fun todoEstoque(): Boolean {
    return true
  }
}

interface ITabEstoqueAppEntradaViewModel : ITabAbstractProdutoViewModel