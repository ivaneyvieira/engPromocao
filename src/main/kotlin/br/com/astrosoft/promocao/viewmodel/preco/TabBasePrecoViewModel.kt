package br.com.astrosoft.promocao.viewmodel.preco

import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao.BASE
import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao.PROMOCAO

class TabBasePrecoViewModel(viewModel: PrecoViewModel) : TabAbstractPrecoViewModel<ITabBasePrecoViewModel>(viewModel) {
  override val subView
    get() = viewModel.view.tabBaseViewModel
  override val tipoTab
    get() = listOf(BASE, PROMOCAO)
}

interface ITabBasePrecoViewModel : ITabAbstractPrecoViewModel