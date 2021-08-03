package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao
import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao.*
import br.com.astrosoft.promocao.viewmodel.promocao.ITabSemPromocaoViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.TabSemPromocaoViewModel

class TabSemPromocao(viewModel: TabSemPromocaoViewModel) : TabAbstractPromocao<ITabSemPromocaoViewModel>(viewModel),
        ITabSemPromocaoViewModel{
  override fun tipo() = listOf(BASE)
  override val label: String
    get() = "Sem Promocao"
}