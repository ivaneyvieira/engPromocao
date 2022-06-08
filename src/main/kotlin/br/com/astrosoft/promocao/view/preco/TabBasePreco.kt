package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.promocao.viewmodel.preco.ITabBasePrecoViewModel
import br.com.astrosoft.promocao.viewmodel.preco.TabBasePrecoViewModel
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class TabBasePreco(viewModel: TabBasePrecoViewModel) : TabAbstractPreco<ITabBasePrecoViewModel>(viewModel),
        ITabBasePrecoViewModel {
  override val label: String
    get() = "Base"

  override fun HorizontalLayout.addAditionaisFields() {
  }
}