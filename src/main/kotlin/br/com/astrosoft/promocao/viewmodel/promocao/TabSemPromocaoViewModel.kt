package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao.BASE

class TabSemPromocaoViewModel(viewModel: PromocaoViewModel) :
        TabAbstractPromocaoViewModel<ITabSemPromocaoViewModel>(viewModel) {
  override val subView
    get() = viewModel.view.tabSemPromocaoViewModel
  override val tipoTab
    get() = listOf(BASE)
}

interface ITabSemPromocaoViewModel : ITabAbstractPromocaoViewModel