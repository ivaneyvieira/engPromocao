package br.com.astrosoft.promocao.viewmodel.promocao

class TabPrecificacaoEntradaMaViewModel(viewModel: PromocaoViewModel) : TabPrecificacaoAbstractViewModel(viewModel) {
  override val subView
    get() = viewModel.view.tabPrecificacaoEntradaMaViewModel
}

