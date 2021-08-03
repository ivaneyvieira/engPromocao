package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao
import br.com.astrosoft.promocao.viewmodel.promocao.ITabBaseViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.TabBaseViewModel

class TabBase(viewModel: TabBaseViewModel) : TabAbstractPromocao<ITabBaseViewModel>(viewModel), ITabBaseViewModel {
  override fun tipo(): ETipoListaPromocao  = ETipoListaPromocao.BASE
}