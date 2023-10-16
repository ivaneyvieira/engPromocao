package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.promocao.model.beans.FiltroPrecoPromocao
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.viewmodel.promocao.ITab99ViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.Tab99ViewModel
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class Tab99(viewModel: Tab99ViewModel) : TabAbstractPromocao<ITab99ViewModel>(viewModel), ITab99ViewModel {
  override val label: String
    get() = "Preço 99"

  override fun filtro(): FiltroPrecoPromocao {
    return super.filtro().copy(decimal99 = "S")
  }

  override fun HorizontalLayout.addAditionaisFields() {
  }

  override fun isAuthorized(user: IUser) = (user as? UserSaci)?.promocao99 ?: false
}