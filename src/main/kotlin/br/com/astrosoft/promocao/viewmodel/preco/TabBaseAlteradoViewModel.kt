package br.com.astrosoft.promocao.viewmodel.preco

import br.com.astrosoft.promocao.model.beans.FiltroPrecoAlteracao
import br.com.astrosoft.promocao.model.beans.PrecoAlteracao

class TabBaseAlteradoViewModel(viewModel: PrecoViewModel) :
        TabAbstractPrecoViewModel<ITabBaseAlteradoViewModel>(viewModel) {
  override val subView
    get() = viewModel.view.tabBaseViewModel

  override fun findPrecoAlteracao(filtro: FiltroPrecoAlteracao): List<PrecoAlteracao> {
    return PrecoAlteracao.baseAlterado(filtro)
  }
}

interface ITabBaseAlteradoViewModel : ITabAbstractPrecoViewModel