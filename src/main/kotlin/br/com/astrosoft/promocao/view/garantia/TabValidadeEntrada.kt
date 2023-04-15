package br.com.astrosoft.promocao.view.garantia

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.addColumnSeq
import br.com.astrosoft.promocao.model.beans.EMarcaPonto
import br.com.astrosoft.promocao.model.beans.FiltroValidadeEntrada
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.model.beans.ValidadeEntrada
import br.com.astrosoft.promocao.model.planilhas.PlanilhaValidadeEntrada
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtValidade
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoCodigo
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoDataEntrada
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoDescricao
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoEntrada
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoEstoque
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoFabricacao
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoGrade
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoLoja
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoMesesFab
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoMesesVenc
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoNFEntrada
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoSaldo
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoVencimento
import br.com.astrosoft.promocao.viewmodel.garantia.ITabValidadeEntradaViewModel
import br.com.astrosoft.promocao.viewmodel.garantia.TabValidadeEntradaViewModel
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

class TabValidadeEntrada(val viewModel: TabValidadeEntradaViewModel) :
  TabPanelGrid<ValidadeEntrada>(ValidadeEntrada::class),
  ITabValidadeEntradaViewModel {
  private lateinit var edtQuery: TextField
  private lateinit var cmbPontos: Select<EMarcaPonto>

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun listSelected(): List<ValidadeEntrada> {
    return itensSelecionados()
  }

  override fun HorizontalLayout.toolBarConfig() {
    edtQuery = textField("Filtro") {
      this.valueChangeMode = ValueChangeMode.TIMEOUT
      this.valueChangeMode = ValueChangeMode.LAZY
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
      val planilha = PlanilhaValidadeEntrada()
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
    return "controleValidade$textTime.xlsx"
  }

  override fun isAuthorized(user: IUser) = (user as? UserSaci)?.garantiaEntrada ?: false
  override val label: String
    get() = "Controle Validade"

  override fun filtro() =
    FiltroValidadeEntrada(query = edtQuery.value, marca = cmbPontos.value)

  override fun Grid<ValidadeEntrada>.gridPanel() {
    this.setSelectionMode(Grid.SelectionMode.MULTI)
    addColumnSeq("Seq")
    produtoLoja()
    produtoCodigo()
    produtoDescricao()
    produtoGrade()
    produtValidade()
    produtoNFEntrada()
    produtoDataEntrada()
    produtoFabricacao()
    produtoMesesFab()
    produtoVencimento()
    produtoMesesVenc()
    produtoEntrada()
    produtoSaldo()
    produtoEstoque()
  }
}


