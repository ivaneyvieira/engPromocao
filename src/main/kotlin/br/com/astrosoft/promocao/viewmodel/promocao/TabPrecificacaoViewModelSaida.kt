package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.framework.model.BeanForm
import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.promocao.model.beans.FiltroPrecificacao
import br.com.astrosoft.promocao.model.beans.Precificacao

class TabPrecificacaoSaidaViewModel(viewModel: PromocaoViewModel) : TabPrecificacaoAbstractViewModel(viewModel) {
  override val subView
    get() = viewModel.view.tabPrecificacaoSaidaViewModel
}

