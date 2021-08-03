package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao.PROMOCAO

class TabPromocaoViewModel(viewModel: PromocaoViewModel) :
        TabAbstractPromocaoViewModel<ITabPromocaoViewModel>(viewModel) {
  override val subView
    get() = viewModel.view.tabPromocaoViewModel
  override val tipoTab
    get() = listOf(PROMOCAO)
}

interface ITabPromocaoViewModel : ITabAbstractPromocaoViewModel