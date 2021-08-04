package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.promocao.viewmodel.promocao.ITabPromocaoViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.TabPromocaoViewModel
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class TabPromocao(viewModel: TabPromocaoViewModel) : TabAbstractPromocao<ITabPromocaoViewModel>(viewModel),
        ITabPromocaoViewModel {
  override val label: String
    get() = "Promoção"

  override fun HorizontalLayout.addAditionaisFields() {

  }
}