package br.com.astrosoft.promocao.viewmodel.produto

import br.com.astrosoft.promocao.model.beans.FiltroProduto
import br.com.astrosoft.promocao.model.beans.Produtos

class TabBaseProdutoViewModel(viewModel: ProdutoViewModel) :
        TabAbstractProdutoViewModel<ITabBaseProdutoViewModel>(viewModel) {
  override val subView
    get() = viewModel.view.tabBaseViewModel

  override fun findPrecoAlteracao(filtro: FiltroProduto): List<Produtos> {
    return Produtos.find(filtro)
  }
}

interface ITabBaseProdutoViewModel : ITabAbstractProdutoViewModel