package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.addColumnSeq
import br.com.astrosoft.framework.view.shiftSelect
import br.com.astrosoft.promocao.model.beans.EMarcaPonto
import br.com.astrosoft.promocao.model.beans.FiltroPrecoPromocao
import br.com.astrosoft.promocao.model.beans.PrecoPromocao
import br.com.astrosoft.promocao.model.planilhas.PlanilhaPromocao
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoCentroLucro
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoCodigo
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoDesconto
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoDescricao
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoEstoque
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoFornecedor
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoPrecoPromocional
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoPrecoRef
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoTipoProduto
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoValidade
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoVendno
import br.com.astrosoft.promocao.viewmodel.promocao.*
import com.github.mvysny.karibudsl.v10.integerField
import com.github.mvysny.karibudsl.v10.select
import com.github.mvysny.karibudsl.v10.tooltip
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.Grid.SelectionMode.MULTI
import com.vaadin.flow.component.grid.Grid.SelectionMode.SINGLE
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.data.value.ValueChangeMode.TIMEOUT
import org.vaadin.stefan.LazyDownloadButton
import java.io.ByteArrayInputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class TabAbstractPromocao<T : ITabAbstractPromocaoViewModel>(open val viewModel: TabAbstractPromocaoViewModel<T>) :
        TabPanelGrid<PrecoPromocao>(PrecoPromocao::class), ITabAbstractPromocaoViewModel {
  private lateinit var edtCodigo: IntegerField
  private lateinit var edtVend: IntegerField
  private lateinit var edtCl: IntegerField
  private lateinit var edtType: IntegerField
  private lateinit var cmbPontos: Select<EMarcaPonto>

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun listSelected(): List<PrecoPromocao> {
    return itensSelecionados()
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

    this.downloadExcel()

    addAditionaisFields()
  }

  private fun HasComponents.downloadExcel() {
    val button = LazyDownloadButton(VaadinIcon.TABLE.create(), { filename() }, {
      val planilha = PlanilhaPromocao()
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

  override fun filtro() = FiltroPrecoPromocao(codigo = edtCodigo.value ?: 0,
                                              vendno = edtVend.value ?: 0,
                                              clno = edtCl.value ?: 0,
                                              typeno = edtType.value ?: 0,
                                              tipoLista = viewModel.tipoTab,
                                              decimal99 = "N",
                                              marcaPonto = cmbPontos.value ?: EMarcaPonto.TODOS)

  override fun Grid<PrecoPromocao>.gridPanel() {
    when (viewModel) {
      is TabBasePromocaoViewModel -> setSelectionMode(SINGLE)
      is TabPromocaoViewModel     -> {
        setSelectionMode(MULTI)
        this.shiftSelect()
      }

      is TabSemPromocaoViewModel  -> {
        setSelectionMode(MULTI)
        this.shiftSelect()
      }
    }

    addColumnSeq("Item")
    promocaoCodigo()
    promocaoDescricao()
    promocaoPrecoRef()
    promocaoEstoque()
    promocaoPrecoPromocional()
    promocaoDesconto()
    promocaoValidade()
    promocaoVendno()
    promocaoFornecedor()
    promocaoTipoProduto()
    promocaoCentroLucro()
  }
}