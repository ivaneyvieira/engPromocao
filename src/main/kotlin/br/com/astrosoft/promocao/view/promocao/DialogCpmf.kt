package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.util.format
import br.com.astrosoft.promocao.viewmodel.promocao.TabPrecificacaoViewModel
import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.textfield.BigDecimalField
import com.vaadin.flow.component.textfield.TextFieldVariant
import com.vaadin.flow.data.value.ValueChangeMode

class DialogCpmf(val viewModel: TabPrecificacaoViewModel, val bean: BeanForm) : Dialog() {
  private lateinit var edtCpmf: BigDecimalField
  private lateinit var edtFcp: BigDecimalField

  init {
    element.setAttribute("aria-label", "Create new employee")
    isModal = true
    createDialogLayout()
  }

  fun cpmfChange(): Boolean = edtCpmf.value?.toDouble().format() != bean.cpmf.format()

  fun fcpChange(): Boolean = edtFcp.value?.toDouble().format() != bean.fcp.format()

  fun createDialogLayout() {
    verticalLayout {
      isPadding = false
      alignItems = FlexComponent.Alignment.STRETCH
      style.set("width", "300px").set("max-width", "100%")
      h2("Modificar Percentuais da Planilha de Precificação") {
        style.set("margin", "var(--lumo-space-m) 0 0 0").set("font-size", "1.5em").set("font-weight", "bold")
      }
      verticalLayout {
        isSpacing = false
        isPadding = false
        alignItems = FlexComponent.Alignment.STRETCH
        edtCpmf = bigDecimalField("CPMF Novo") {
          this.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT)
          val decimal = bean.cpmf?.toBigDecimal()
          decimal?.setScale(2)
          value = decimal

          this.valueChangeMode = ValueChangeMode.EAGER
          addValueChangeListener {
            if (!cpmfChange()) this.prefixComponent = VaadinIcon.CHECK.create()
            else this.prefixComponent = null
          }
        }
        edtFcp = bigDecimalField("FCP Novo") {
          this.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT)
          val decimal = bean.fcp?.toBigDecimal()
          decimal?.setScale(2)
          value = decimal

          this.valueChangeMode = ValueChangeMode.EAGER
          addValueChangeListener {
            if (!cpmfChange()) this.prefixComponent = VaadinIcon.CHECK.create()
            else this.prefixComponent = null
          }
        }
      }
      horizontalLayout {
        justifyContentMode = FlexComponent.JustifyContentMode.END
        button("Confirma") {
          addThemeVariants(ButtonVariant.LUMO_PRIMARY)
          onLeftClick {
            if (cpmfChange()) viewModel.modificaCpmf(cpmfNew = edtCpmf.value?.toDouble())
            if (fcpChange()) viewModel.modificaFcp(fcpNew = edtFcp.value?.toDouble())
            this@DialogCpmf.close()
          }
        }
        button("Cancela") {
          onLeftClick {
            this@DialogCpmf.close()
          }
        }
      }
    }
  }
}

data class BeanForm(val cpmf: Double?, val fcp: Double?)