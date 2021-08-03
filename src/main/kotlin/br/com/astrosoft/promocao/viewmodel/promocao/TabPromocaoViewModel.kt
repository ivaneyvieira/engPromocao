package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao

class TabPrecificacaoViewModel(viewModel: PromocaoViewModel) :
        TabAbstractPromocaoViewModel<ITabPrecificacaoViewModel>(viewModel) {
  override val subView
    get() = viewModel.view.tabPrecificacaoViewModel
  override val tipoTab: ETipoListaPromocao
    get() = ETipoListaPromocao.PRECIFICACAO
}

interface ITabPrecificacaoViewModel : ITabAbstractPromocaoViewModel