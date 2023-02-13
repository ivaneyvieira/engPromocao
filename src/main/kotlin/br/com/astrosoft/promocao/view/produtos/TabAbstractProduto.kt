package br.com.astrosoft.promocao.view.produtos

import br.com.astrosoft.framework.model.CampoNumber
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.promocao.model.beans.EInativo
import br.com.astrosoft.promocao.model.beans.EMarcaPonto
import br.com.astrosoft.promocao.model.beans.FiltroProduto
import br.com.astrosoft.promocao.model.beans.Produtos
import br.com.astrosoft.promocao.model.planilhas.PlanilhaProduto
import br.com.astrosoft.promocao.viewmodel.produto.ITabAbstractProdutoViewModel
import br.com.astrosoft.promocao.viewmodel.produto.TabAbstractProdutoViewModel
import com.github.mvysny.karibudsl.v10.select
import com.github.mvysny.karibudsl.v10.textField
import com.github.mvysny.kaributools.header2
import com.github.mvysny.kaributools.tooltip
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode.LAZY
import org.vaadin.stefan.LazyDownloadButton
import java.io.ByteArrayInputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class TabAbstractProduto<T : ITabAbstractProdutoViewModel>(open val viewModel: TabAbstractProdutoViewModel<T>,
                                                                    val showDatas: Boolean = true) :
        TabPanelGrid<Produtos>(Produtos::class), ITabAbstractProdutoViewModel {
  private lateinit var edtPesquisa: TextField
  private lateinit var cmbPontos: Select<EMarcaPonto>
  private lateinit var cmbInativo: Select<EInativo>

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun HorizontalLayout.toolBarConfig() {
    edtPesquisa = textField("Pesquisa") {
      this.valueChangeMode = LAZY
      this.valueChangeTimeout = 1500
      addValueChangeListener {
        viewModel.updateView()
      }
    }
    this.downloadExcel(planilha())

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

    cmbInativo = select("Inativo") {
      setItems(EInativo.values().toList())
      value = EInativo.TODOS
      this.setItemLabelGenerator {
        it.descricao
      }
      addValueChangeListener {
        viewModel.updateView()
      }
    }

    addAditionaisFields()
  }

  abstract fun planilha(): PlanilhaProduto

  private fun filename(): String {
    val sdf = DateTimeFormatter.ofPattern("yyMMddHHmmss")
    val textTime = LocalDateTime.now().format(sdf)
    return "produto$textTime.xlsx"
  }

  protected abstract fun HorizontalLayout.addAditionaisFields()

  override fun filtro() = FiltroProduto(
    pesquisa = edtPesquisa.value ?: "",
    marcaPonto = cmbPontos.value ?: EMarcaPonto.TODOS,
    todoEstoque = viewModel.todoEstoque(),
    inativo = cmbInativo.value ?: EInativo.TODOS,
                                       )

  override fun Grid<Produtos>.gridPanel() {
    setSelectionMode(Grid.SelectionMode.MULTI)
    this.colunasGrid()
    printColunas()
  }

  abstract fun Grid<Produtos>.colunasGrid()

  private fun printColunas() {
    val colList = gridPanel.columns.joinToString("\n") {column ->
      "CampoNumber(\"${column.header2}\") { ${column.key} ?: 0.00 },"
    }
    println(label)
    println(colList)
  }

  private fun HasComponents.downloadExcel(planilha: PlanilhaProduto) {
    val button = LazyDownloadButton(VaadinIcon.TABLE.create(), { filename() }, {
      val bytes = planilha.grava(itensSelecionados())
      ByteArrayInputStream(bytes)
    })
    button.addThemeVariants(ButtonVariant.LUMO_SMALL)
    button.tooltip = "Salva a planilha"
    add(button)
  }
}


