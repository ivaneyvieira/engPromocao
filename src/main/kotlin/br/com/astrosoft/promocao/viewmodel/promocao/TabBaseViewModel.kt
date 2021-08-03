package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao

class TabBaseViewModel(viewModel: PromocaoViewModel) :
        TabAbstractPromocaoViewModel<ITabBaseViewModel>(viewModel) {
  override val subView
    get() = viewModel.view.tabBaseViewModel
  override val tipoTab: ETipoListaPromocao
    get() = ETipoListaPromocao.BASE
}

interface ITabBaseViewModel : ITabAbstractPromocaoViewModel