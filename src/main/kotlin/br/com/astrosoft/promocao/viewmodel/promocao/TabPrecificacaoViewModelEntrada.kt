package br.com.astrosoft.promocao.viewmodel.promocao

class TabPrecificacaoEntradaViewModel(viewModel: PromocaoViewModel) : TabPrecificacaoAbstractViewModel(viewModel) {
    override val subView
        get() = viewModel.view.tabPrecificacaoEntradaViewModel
}

