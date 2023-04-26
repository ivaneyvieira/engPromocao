package br.com.astrosoft.promocao.viewmodel.promocao

class TabPrecificacaoSaidaViewModel(viewModel: PromocaoViewModel) : TabPrecificacaoAbstractViewModel(viewModel) {
    override val subView
        get() = viewModel.view.tabPrecificacaoSaidaViewModel
}

