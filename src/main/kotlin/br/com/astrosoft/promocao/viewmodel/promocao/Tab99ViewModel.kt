package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao.BASE
import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao.PROMOCAO

class Tab99ViewModel(viewModel: PromocaoViewModel) : TabAbstractPromocaoViewModel<ITab99ViewModel>(viewModel) {
    override val subView
        get() = viewModel.view.tab99ViewModel
    override val tipoTab
        get() = listOf(BASE, PROMOCAO)
}

interface ITab99ViewModel : ITabAbstractPromocaoViewModel