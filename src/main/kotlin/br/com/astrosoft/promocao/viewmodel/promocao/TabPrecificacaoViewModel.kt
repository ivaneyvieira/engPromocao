package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao

class TabPromocaoViewModel(viewModel: PromocaoViewModel) :
        TabAbstractPromocaoViewModel<ITabPromocaoViewModel>(viewModel) {
  override val subView
    get() = viewModel.view.tabPromocaoViewModel
  override val tipoTab: ETipoListaPromocao
    get() = ETipoListaPromocao.PROMOCAO
}

interface ITabPromocaoViewModel : ITabAbstractPromocaoViewModel