package br.com.astrosoft.promocao.view.preco

import br.com.astrosoft.framework.model.Config
import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.localePtBr
import br.com.astrosoft.promocao.model.EtiquetaChave
import br.com.astrosoft.promocao.model.beans.FiltroPrecoAlteracao
import br.com.astrosoft.promocao.model.beans.PrecoAlteracao
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.model.planilhas.PlanilhaAlteracao
import br.com.astrosoft.promocao.viewmodel.preco.ITabAbstractPrecoViewModel
import br.com.astrosoft.promocao.viewmodel.preco.TabAbstractPrecoViewModel
import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode.TIMEOUT
import org.vaadin.stefan.LazyDownloadButton
import java.io.ByteArrayInputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class TabAbstractPreco<T : ITabAbstractPrecoViewModel>(open val viewModel: TabAbstractPrecoViewModel<T>,
                                                                val showDatas: Boolean = true) :
        TabPanelGrid<PrecoAlteracao>(PrecoAlteracao::class), ITabAbstractPrecoViewModel {
  private lateinit var edtCodigo: TextField
  private lateinit var edtVend: IntegerField
  private lateinit var edtCl: IntegerField
  private lateinit var edtType: IntegerField
  private lateinit var edtDataI: DatePicker
  private lateinit var edtDataF: DatePicker

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun HorizontalLayout.toolBarConfig() {
    edtCodigo = textField("Código") {
      this.valueChangeMode = TIMEOUT
      addValueChangeListener {
        viewModel.updateView()
      }
    }

    edtVend = integerField("Fornecedor") {
      this.valueChangeMode = TIMEOUT
      addValueChangeListener {
        viewModel.updateView()
      }
    }

    edtCl = integerField("Centro de Lucro") {
      this.valueChangeMode = TIMEOUT
      addValueChangeListener {
        viewModel.updateView()
      }
    }

    edtType = integerField("Tipo de produto") {
      this.valueChangeMode = TIMEOUT
      addValueChangeListener {
        viewModel.updateView()
      }
    }

    edtDataI = datePicker("Data Inicial") {
      localePtBr()
      this.isVisible = showDatas
      value = LocalDate.now().minusDays(1)
      addValueChangeListener {
        viewModel.updateView()
      }
    }
    edtDataF = datePicker("Data Final") {
      localePtBr()
      this.isVisible = showDatas
      value = LocalDate.now()
      addValueChangeListener {
        viewModel.updateView()
      }
    }

    this.downloadExcel()

    button("Etiqueta") {
      icon = VaadinIcon.PRINT.create()
      onLeftClick {
        val itens = itensSelecionados()
        if (itens.isEmpty()) {
          showErro("Nenhum item selecionado")
        }
        else {
          val dados = itens.flatMap {
            it.dadosEtiquetas()
          }
          val userSaci = Config.user as? UserSaci
          val impressora = userSaci?.impressora
          if (impressora != null) {
            EtiquetaChave.printPreviewProdutos(impressora, dados)
          }
        }
      }
    }

    addAditionaisFields()
  }

  private fun HasComponents.downloadExcel() {
    val button = LazyDownloadButton(VaadinIcon.TABLE.create(), { filename() }, {
      val planilha = PlanilhaAlteracao()
      val bytes = planilha.grava(listBeans())
      ByteArrayInputStream(bytes)
    })
    button.addThemeVariants(ButtonVariant.LUMO_SMALL)
    button.tooltip = "Salva a planilha"
    add(button)
  }

  private fun filename(): String {
    val sdf = DateTimeFormatter.ofPattern("yyMMddHHmmss")
    val textTime = LocalDateTime.now().format(sdf)
    val filename = "promocao$textTime.xlsx"
    return filename
  }

  protected abstract fun HorizontalLayout.addAditionaisFields()

  override fun filtro() = FiltroPrecoAlteracao(codigo = edtCodigo.value ?: "",
                                               vendno = edtVend.value ?: 0,
                                               clno = edtCl.value ?: 0,
                                               typeno = edtType.value ?: 0,
                                               dataInicial = edtDataI.value,
                                               dataFinal = edtDataF.value)

  override fun Grid<PrecoAlteracao>.gridPanel() {
    setSelectionMode(Grid.SelectionMode.MULTI)
    this.colunasGrid()
  }

  abstract fun Grid<PrecoAlteracao>.colunasGrid()
}


