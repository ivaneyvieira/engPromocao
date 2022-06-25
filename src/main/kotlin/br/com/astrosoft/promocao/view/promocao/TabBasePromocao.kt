package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.viewmodel.promocao.ITabBasePromocaoViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.TabBasePromocaoViewModel
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class TabBasePromocao(viewModel: TabBasePromocaoViewModel) : TabAbstractPromocao<ITabBasePromocaoViewModel>(viewModel),
        ITabBasePromocaoViewModel {
  override val label: String
    get() = "Base"

  override fun HorizontalLayout.addAditionaisFields() {
  }

  override fun isAuthorized(user: IUser) = (user as? UserSaci)?.promocaoBase ?: false
}