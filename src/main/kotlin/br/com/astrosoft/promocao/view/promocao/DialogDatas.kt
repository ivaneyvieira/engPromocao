package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.view.localePtBr
import br.com.astrosoft.promocao.viewmodel.promocao.TabPromocaoViewModel
import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.orderedlayout.FlexComponent
import java.time.LocalDate

class DialogDatas(val viewModel: TabPromocaoViewModel, val validade : LocalDate?) : Dialog() {
  private lateinit var dataNew: DatePicker
  private lateinit var dataOld: DatePicker

  init {
    element.setAttribute("aria-label", "Create new employee")
    isModal = true
    createDialogLayout()
  }

  fun createDialogLayout() {
    verticalLayout {
      isPadding = false
      alignItems = FlexComponent.Alignment.STRETCH
      style.set("width", "300px").set("max-width", "100%")
      h2("Modificação de data de vencimento") {
        style.set("margin", "var(--lumo-space-m) 0 0 0").set("font-size", "1.5em").set("font-weight", "bold")
      }
      verticalLayout {
        isSpacing = false
        isPadding = false
        alignItems = FlexComponent.Alignment.STRETCH
        dataOld = datePicker("Data") {
          value = validade
          this.localePtBr()
        }
        dataNew = datePicker("Data Nova") {
          value = validade
          this.localePtBr()
        }
      }
      horizontalLayout {
        justifyContentMode = FlexComponent.JustifyContentMode.END
        button("Confirma") {
          addThemeVariants(ButtonVariant.LUMO_PRIMARY)
          onLeftClick {
            viewModel.modificaData(
              dataNew = dataNew.value,
              dataOld = dataOld.value
                                  )
            this@DialogDatas.close()
          }
        }
        button("Cancela") {
          onLeftClick {
            this@DialogDatas.close()
          }
        }
      }
    }
  }
}