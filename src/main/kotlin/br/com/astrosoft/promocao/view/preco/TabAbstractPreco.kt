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
import com.vaadin.flow.data.value.ValueChangeMode.TIMEOUT
import org.vaadin.stefan.LazyDownloadButton
import java.io.ByteArrayInputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class TabAbstractPreco<T : ITabAbstractPrecoViewModel>(open val viewModel: TabAbstractPrecoViewModel<T>) :
        TabPanelGrid<PrecoAlteracao>(PrecoAlteracao::class), ITabAbstractPrecoViewModel {
  private lateinit var edtCodigo: IntegerField
  private lateinit var edtVend: IntegerField
  private lateinit var edtCl: IntegerField
  private lateinit var edtType: IntegerField
  private lateinit var edtData: DatePicker

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun HorizontalLayout.toolBarConfig() {
    edtCodigo = integerField("Código") {
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

    edtData = datePicker("Validade") {
      localePtBr()
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
        if(itens.isEmpty()){
          showErro("Nenhum item selecionado")
        }else {
          val dados = itens.flatMap {
            it.dadosEtiquetas()
          }
          val userSaci = Config.user as? UserSaci
          val impressora = userSaci?.impressora
          if(impressora != null) {
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

  override fun isAuthorized(user: IUser) = true

  override fun filtro() = FiltroPrecoAlteracao(codigo = edtCodigo.value ?: 0,
                                              vendno = edtVend.value ?: 0,
                                              clno = edtCl.value ?: 0,
                                              typeno = edtType.value ?: 0,
                                              dataAlteracao = edtData.value)

  override fun Grid<PrecoAlteracao>.gridPanel() {
    setSelectionMode(Grid.SelectionMode.MULTI)
    this.colunasGrid()
  }

  abstract fun Grid<PrecoAlteracao>.colunasGrid()
}


