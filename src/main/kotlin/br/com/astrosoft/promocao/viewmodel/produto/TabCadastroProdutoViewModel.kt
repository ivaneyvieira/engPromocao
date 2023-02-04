package br.com.astrosoft.promocao.viewmodel.produto

import br.com.astrosoft.promocao.model.beans.FiltroProduto
import br.com.astrosoft.promocao.model.beans.Produtos

class TabCadastroProdutoViewModel(viewModel: ProdutoViewModel) :
        TabAbstractProdutoViewModel<ITabCadastroProdutoViewModel>(viewModel) {
  override val subView
    get() = viewModel.view.tabCadastroViewModel

  override fun findPrecoAlteracao(filtro: FiltroProduto): List<Produtos> {
    return Produtos.find(filtro)
  }
}

interface ITabCadastroProdutoViewModel : ITabAbstractProdutoViewModel