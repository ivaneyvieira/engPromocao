package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.promocao.viewmodel.promocao.ITabBaseViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.TabBaseViewModel
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class TabBase(viewModel: TabBaseViewModel) : TabAbstractPromocao<ITabBaseViewModel>(viewModel), ITabBaseViewModel {
  override val label: String
    get() = "Base"

  override fun HorizontalLayout.addAditionaisFields() {

  }
}