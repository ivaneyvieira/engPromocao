package br.com.astrosoft.promocao.viewmodel.promocao

open class TabPrecificacaoViewModel(viewModel: PromocaoViewModel) : TabPrecificacaoAbstractViewModel(viewModel) {
  override val subView
    get() = viewModel.view.tabPrecificacaoViewModel
}

