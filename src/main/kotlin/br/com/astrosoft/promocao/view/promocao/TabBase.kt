package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao.BASE
import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao.PROMOCAO
import br.com.astrosoft.promocao.viewmodel.promocao.ITabBaseViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.TabBaseViewModel

class TabBase(viewModel: TabBaseViewModel) : TabAbstractPromocao<ITabBaseViewModel>(viewModel), ITabBaseViewModel {
  override fun tipo() = listOf(BASE, PROMOCAO)
  override val label: String
    get() = "Base"
}