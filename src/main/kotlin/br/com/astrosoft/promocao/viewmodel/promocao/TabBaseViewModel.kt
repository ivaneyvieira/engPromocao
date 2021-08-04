package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao.BASE
import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao.PROMOCAO

class TabBaseViewModel(viewModel: PromocaoViewModel) : TabAbstractPromocaoViewModel<ITabBaseViewModel>(viewModel) {
  override val subView
    get() = viewModel.view.tabBaseViewModel
  override val tipoTab
    get() = listOf(BASE, PROMOCAO)
}

interface ITabBaseViewModel : ITabAbstractPromocaoViewModel