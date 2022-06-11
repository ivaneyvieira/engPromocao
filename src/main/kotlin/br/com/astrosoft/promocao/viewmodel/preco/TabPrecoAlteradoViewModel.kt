package br.com.astrosoft.promocao.viewmodel.preco

import br.com.astrosoft.promocao.model.beans.FiltroPrecoAlteracao
import br.com.astrosoft.promocao.model.beans.PrecoAlteracao

class TabPrecoAlteradoViewModel(viewModel: PrecoViewModel) : TabAbstractPrecoViewModel<ITabPrecoAlteradoViewModel>
  (viewModel) {
  override val subView
    get() = viewModel.view.tabPrecoViewModel

  override fun findPrecoAlteracao(filtro: FiltroPrecoAlteracao): List<PrecoAlteracao> {
    return PrecoAlteracao.precoAlterado(filtro)
  }
}

interface ITabPrecoAlteradoViewModel : ITabAbstractPrecoViewModel