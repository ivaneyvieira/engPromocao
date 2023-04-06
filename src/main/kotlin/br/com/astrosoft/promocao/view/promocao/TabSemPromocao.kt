package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.localePtBr
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.viewmodel.promocao.ITabSemPromocaoViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.TabSemPromocaoViewModel
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.datePicker
import com.github.mvysny.karibudsl.v10.numberField
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.NumberField
import com.vaadin.flow.component.textfield.TextFieldVariant
import java.time.LocalDate

class TabSemPromocao(override val viewModel: TabSemPromocaoViewModel) :
  TabAbstractPromocao<ITabSemPromocaoViewModel>(viewModel), ITabSemPromocaoViewModel {
  private lateinit var edtValidade: DatePicker
  private lateinit var edtDesconto: NumberField

  override fun desconto(): Double? = edtDesconto.value

  override fun validade(): LocalDate? = edtValidade.value

  override val label: String
    get() = "Sem Promocao"

  override fun HorizontalLayout.addAditionaisFields() {
    edtDesconto = numberField("% Desconto") {
      addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT)
      min = 0.00
      max = 100.00
      value = 0.00
    }
    edtValidade = datePicker("Validade") {
      this.localePtBr()
    }
    button("Executa") {
      this.icon = VaadinIcon.ROCKET.create()

      onLeftClick {
        viewModel.executaDesconto()
      }
    }

    button("Mudar data") {
      onLeftClick {
        val itens = itensSelecionados()
        if (itens.isEmpty()) {
          showErro("Nenhum item selecionado")
        } else {
          val dialog = DialogDatasSemPromocao(viewModel)
          dialog.open()
        }
      }
    }
  }

  override fun isAuthorized(user: IUser) = (user as? UserSaci)?.promocaoSemPromocao ?: false
}