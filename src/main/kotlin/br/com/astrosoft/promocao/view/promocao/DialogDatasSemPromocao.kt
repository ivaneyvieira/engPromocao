package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.view.localePtBr
import br.com.astrosoft.promocao.viewmodel.promocao.TabSemPromocaoViewModel
import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.orderedlayout.FlexComponent

class DialogDatasSemPromocao(val viewModel: TabSemPromocaoViewModel) : Dialog() {
    private lateinit var dataNew: DatePicker

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
                dataNew = datePicker("Data Nova") {
                    this.localePtBr()
                }
            }
            horizontalLayout {
                justifyContentMode = FlexComponent.JustifyContentMode.END
                button("Confirma") {
                    addThemeVariants(ButtonVariant.LUMO_PRIMARY)
                    onLeftClick {
                        viewModel.modificaData(dataNew = dataNew.value)
                        this@DialogDatasSemPromocao.close()
                    }
                }
                button("Cancela") {
                    onLeftClick {
                        this@DialogDatasSemPromocao.close()
                    }
                }
            }
        }
    }
}