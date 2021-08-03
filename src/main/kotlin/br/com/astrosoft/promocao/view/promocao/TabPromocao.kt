package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao
import br.com.astrosoft.promocao.viewmodel.promocao.ITabPromocaoViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.TabPromocaoViewModel

class TabPromocao(viewModel: TabPromocaoViewModel) : TabAbstractPromocao<ITabPromocaoViewModel>(viewModel),
        ITabPromocaoViewModel {
  override fun tipo(): ETipoListaPromocao = ETipoListaPromocao.PROMOCAO
}