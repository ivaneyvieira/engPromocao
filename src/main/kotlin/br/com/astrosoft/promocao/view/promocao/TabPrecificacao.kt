package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao
import br.com.astrosoft.promocao.viewmodel.promocao.ITabPrecificacaoViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.TabPrecificacaoViewModel

class TabPrecificacao(viewModel: TabPrecificacaoViewModel) : TabAbstractPromocao<ITabPrecificacaoViewModel>(viewModel),
        ITabPrecificacaoViewModel{
  override fun tipo(): ETipoListaPromocao = ETipoListaPromocao.PRECIFICACAO
}