package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.localePtBr
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.viewmodel.promocao.ITabPromocaoViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.TabPromocaoViewModel
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.datePicker
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import java.time.LocalDate

class TabPromocao(override val viewModel: TabPromocaoViewModel) : TabAbstractPromocao<ITabPromocaoViewModel>(viewModel),
        ITabPromocaoViewModel {
  override val label: String
    get() = "Promoção"
  private lateinit var edtValidade: DatePicker

  override fun validade(): LocalDate? = edtValidade.value

  override fun HorizontalLayout.addAditionaisFields() {
    edtValidade = datePicker("Validade") {
      this.localePtBr()
    }
    button("Executa") {
      this.icon = VaadinIcon.ROCKET.create()

      onLeftClick {
        viewModel.prorrogaValidade()
      }
    }

    button("Mudar data") {
      onLeftClick {
        val itens = itensSelecionados()
        if (itens.isEmpty()) {
          showErro("Nenhum item selecionado")
        }
        else {
          val validade = itens.firstOrNull()?.validade
          val dialog = DialogDatas(viewModel, validade)
          dialog.open()
        }
      }
    }
  }

  override fun isAuthorized(user: IUser) = (user as? UserSaci)?.promocaoPromocao ?: false
}