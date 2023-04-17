package br.com.astrosoft.promocao.view.produtos

import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.localePtBr
import br.com.astrosoft.promocao.model.beans.*
import br.com.astrosoft.promocao.model.planilhas.PlanilhaProduto
import br.com.astrosoft.promocao.viewmodel.produto.ITabAbstractProdutoViewModel
import br.com.astrosoft.promocao.viewmodel.produto.TabAbstractProdutoViewModel
import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.kaributools.header2
import com.github.mvysny.kaributools.tooltip
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode.LAZY
import org.vaadin.stefan.LazyDownloadButton
import java.io.ByteArrayInputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class TabAbstractProduto<T : ITabAbstractProdutoViewModel>(
  open val viewModel: TabAbstractProdutoViewModel<T>,
  val showDatas: Boolean = true
) :
  TabPanelGrid<Produtos>(Produtos::class), ITabAbstractProdutoViewModel {
  private lateinit var edtPesquisa: TextField
  private lateinit var cmbPontos: Select<EMarcaPonto>
  private lateinit var edtListVend: TextField
  private lateinit var edtType: IntegerField
  private lateinit var edtCl: IntegerField
  private lateinit var edtTributacao: TextField

  private lateinit var edtDiVenda: DatePicker
  private lateinit var edtDfVenda: DatePicker
  private lateinit var edtDiCompra: DatePicker
  private lateinit var edtDfCompra: DatePicker

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun HorizontalLayout.toolBarConfig() {
    flexLayout {
      this.isExpand = true
      this.flexWrap = FlexLayout.FlexWrap.WRAP
      this.alignContent = FlexLayout.ContentAlignment.SPACE_BETWEEN

      horizontalLayout {
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

        edtListVend = textField("Fornecedores") {
          this.valueChangeMode = LAZY
          this.width = "250px"
          addValueChangeListener {
            viewModel.updateView()
          }
        }

        edtTributacao = textField("Tributação") {
          this.valueChangeMode = LAZY
          this.width = "80px"
          addValueChangeListener {
            viewModel.updateView()
          }
        }

        edtType = integerField("Tipo") {
          this.valueChangeMode = LAZY
          addValueChangeListener {
            viewModel.updateView()
          }
        }

        edtCl = integerField("Centro de Lucro") {
          this.valueChangeMode = LAZY
          addValueChangeListener {
            viewModel.updateView()
          }
        }

        addAditionaisFields()

        label(" ")
      }

      horizontalLayout {
        edtDiVenda = datePicker("Data Venda Inicial") {
          this.localePtBr()
          this.value = LocalDate.now()
          this.addValueChangeListener {
            viewModel.updateView()
          }
        }
        edtDfVenda = datePicker("Data Venda Final") {
          this.localePtBr()
          this.value = LocalDate.now()
          this.addValueChangeListener {
            viewModel.updateView()
          }
        }
        edtDiCompra = datePicker("Data Compra Inicial") {
          this.localePtBr()
          this.value = LocalDate.now()
          this.addValueChangeListener {
            viewModel.updateView()
          }
        }
        edtDfCompra = datePicker("Data Compra Final") {
          this.localePtBr()
          this.value = LocalDate.now()
          this.addValueChangeListener {
            viewModel.updateView()
          }
        }
      }
    }
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
    inativo = EInativo.NAO,
    codigo = 0,
    listVend = edtListVend.value?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList(),
    tributacao = edtTributacao.value ?: "",
    typeno = edtType.value ?: 0,
    clno = edtCl.value ?: 0,
    estoqueTotal = estoqueTotal(),
    diVenda = edtDiVenda.value,
    dfVenda = edtDfVenda.value,
    diCompra = edtDiCompra.value,
    dfCompra = edtDfCompra.value,
  )

  abstract fun estoqueTotal(): EEstoqueTotal

  override fun Grid<Produtos>.gridPanel() {
    setSelectionMode(Grid.SelectionMode.MULTI)
    this.colunasGrid()
    printColunas()
  }

  abstract fun Grid<Produtos>.colunasGrid()

  private fun printColunas() {
    val colList = gridPanel.columns.joinToString("\n") { column ->
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


