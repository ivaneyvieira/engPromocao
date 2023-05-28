package br.com.astrosoft.promocao.viewmodel.produto

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class ProdutoViewModel(view: IProdutoView) : ViewModel<IProdutoView>(view) {
    val tabEstoqueGeralViewModel = TabEstoqueGeralViewModel(this)
    val tabCadastroViewModel = TabCadastroProdutoViewModel(this)
    val tabEstoqueTotalViewModel = TabEstoqueTotalViewModel(this)
    val tabEstoqueAppViewModel = TabEstoqueAppViewModel(this)
    val tabEstoqueAppEntradaViewModel = TabEstoqueAppEntradaViewModel(this)

    override fun listTab() = listOf(
        view.tabEstoqueGeralViewModel,
        view.tabCadastroViewModel,
        view.tabEstoqueTotalViewModel,
        view.tabEstoqueAppViewModel,
        view.tabEstoqueAppEntradaViewModel,
    )
}

interface IProdutoView : IView {
    val tabEstoqueTotalViewModel: ITabEstoqueTotalViewModel
    val tabCadastroViewModel: ITabCadastroProdutoViewModel
    val tabEstoqueGeralViewModel: ITabEstoqueGeralViewModel
    val tabEstoqueAppViewModel: ITabEstoqueAppViewModel
    val tabEstoqueAppEntradaViewModel: ITabEstoqueAppEntradaViewModel
}

