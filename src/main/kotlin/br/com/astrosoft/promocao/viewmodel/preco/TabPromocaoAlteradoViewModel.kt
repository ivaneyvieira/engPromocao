package br.com.astrosoft.promocao.viewmodel.preco

import br.com.astrosoft.promocao.model.beans.FiltroPrecoAlteracao
import br.com.astrosoft.promocao.model.beans.PrecoAlteracao

class TabPromocaoAlteradoViewModel(viewModel: PrecoViewModel) :
    TabAbstractPrecoViewModel<ITabPromocaoAlteradoViewModel>(viewModel) {
    override val subView
        get() = viewModel.view.tabPromocaoViewModel

    override fun findPrecoAlteracao(filtro: FiltroPrecoAlteracao): List<PrecoAlteracao> {
        return PrecoAlteracao.promocaoAlterado(filtro)
    }
}

interface ITabPromocaoAlteradoViewModel : ITabAbstractPrecoViewModel