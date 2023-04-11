package br.com.astrosoft.promocao.view.garantia

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.addColumnSeq
import br.com.astrosoft.promocao.model.beans.*
import br.com.astrosoft.promocao.model.planilhas.PlanilhaPrecificacao
import br.com.astrosoft.promocao.model.planilhas.PlanilhaValidade
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaClno
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaCodigo
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaDescicao
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaEstoque
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaEstoqueMF
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaGrade
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaLocalizacao
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaTypeno
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaUltEnt
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaUltQuant
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaValidade
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaVendno
import br.com.astrosoft.promocao.viewmodel.garantia.ITabValidadeGarantiaViewModel
import br.com.astrosoft.promocao.viewmodel.garantia.TabValidadeGarantiaViewModel
import com.github.mvysny.karibudsl.v10.select
import com.github.mvysny.karibudsl.v10.textField
import com.github.mvysny.kaributools.tooltip
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode
import org.vaadin.stefan.LazyDownloadButton
import java.io.ByteArrayInputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TabValidadeGarantia(val viewModel: TabValidadeGarantiaViewModel) :
  TabPanelGrid<ComparaValidade>(ComparaValidade::class),
  ITabValidadeGarantiaViewModel {
  private lateinit var edtQuery: TextField
  private lateinit var cmbPontos: Select<EMarcaPonto>

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun listSelected(): List<ComparaValidade> {
    return itensSelecionados()
  }

  override fun HorizontalLayout.toolBarConfig() {
    edtQuery = textField("Filtro") {
      this.valueChangeMode = ValueChangeMode.TIMEOUT
      this.addValueChangeListener {
        viewModel.updateView()
      }
    }
    cmbPontos = select("Caracteres Especiais") {
      setItems(EMarcaPonto.values().toList())
      value = EMarcaPonto.TODOS
      this.setItemLabelGenerator {
        it.descricao
      }

      addValueChangeListener {
        viewModel.updateView()
      }
    }

    downloadExcel()
  }

  private fun HasComponents.downloadExcel() {
    val button = LazyDownloadButton(VaadinIcon.TABLE.create(), { filename() }, {
      val planilha = PlanilhaValidade()
      val list = itensSelecionados()
      val bytes = planilha.grava(list)
      ByteArrayInputStream(bytes)
    })
    button.addThemeVariants(ButtonVariant.LUMO_SMALL)
    button.tooltip = "Salva a planilha"
    add(button)
  }

  private fun filename(): String {
    val sdf = DateTimeFormatter.ofPattern("yyMMddHHmmss")
    val textTime = LocalDateTime.now().format(sdf)
    return "precificacao$textTime.xlsx"
  }

  override fun isAuthorized(user: IUser) = (user as? UserSaci)?.garantiaValidade ?: false
  override val label: String
    get() = "Validade"

  override fun filtro() =
    FiltroValidade(tipo = ETipoDiferencaGarantia.TODOS, query = edtQuery.value, marca = cmbPontos.value)

  override fun Grid<ComparaValidade>.gridPanel() {
    this.setSelectionMode(Grid.SelectionMode.MULTI)
    addColumnSeq("Seq")
    garantiaCodigo()
    garantiaDescicao()
    garantiaGrade()
    garantiaVendno()
    garantiaTypeno()
    garantiaClno()
    garantiaLocalizacao()
    garantiaEstoqueMF()
    garantiaEstoque()
    garantiaUltEnt()
    garantiaUltQuant()
    garantiaValidade()
  }
}


