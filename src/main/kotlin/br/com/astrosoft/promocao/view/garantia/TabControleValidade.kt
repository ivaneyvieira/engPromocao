package br.com.astrosoft.promocao.view.garantia

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.addColumnSeq
import br.com.astrosoft.framework.view.localePtBr
import br.com.astrosoft.promocao.model.beans.*
import br.com.astrosoft.promocao.model.planilhas.PlanilhaValidadeEntrada
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtValidade
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoCodigo
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoDataEntrada
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoDescricao
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoEntrada
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoEstoque
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoFabricacao
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoGrade
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoLoc
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoLoja
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoMesesFab
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoMesesVenc
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoNFEntrada
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoSaldo
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoSaldoDS
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoSaldoMF
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoSaldoMR
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoSaldoPK
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoSaldoTM
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoVencimento
import br.com.astrosoft.promocao.view.garantia.columns.ValidadeEntradaCol.produtoVenda
import br.com.astrosoft.promocao.viewmodel.garantia.ITabControleValidadeViewModel
import br.com.astrosoft.promocao.viewmodel.garantia.TabControleValidadeViewModel
import com.github.mvysny.karibudsl.v10.*
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
import com.vaadin.flow.data.value.ValueChangeMode
import org.vaadin.stefan.LazyDownloadButton
import java.io.ByteArrayInputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TabControleValidade(val viewModel: TabControleValidadeViewModel) :
  TabPanelGrid<ValidadeEntrada>(ValidadeEntrada::class),
  ITabControleValidadeViewModel {
  private lateinit var edtCl: IntegerField
  private lateinit var edtType: IntegerField
  private lateinit var edtTributacao: TextField
  private lateinit var edtListVend: TextField
  private lateinit var cmbEstoque: Select<EEstoqueTotal>
  private lateinit var edtNfe: TextField

  private lateinit var edtQuery: TextField
  private lateinit var cmbPontos: Select<EMarcaPonto>

  private lateinit var edtDiVenda: DatePicker
  private lateinit var edtDfVenda: DatePicker

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun listSelected(): List<ValidadeEntrada> {
    return itensSelecionados()
  }

  override fun HorizontalLayout.toolBarConfig() {
    flexLayout {
      this.isExpand = true
      this.flexWrap = FlexLayout.FlexWrap.WRAP
      this.alignContent = FlexLayout.ContentAlignment.SPACE_BETWEEN
      horizontalLayout {
        edtQuery = textField("Filtro") {
          this.valueChangeMode = ValueChangeMode.TIMEOUT
          this.valueChangeMode = ValueChangeMode.LAZY
          this.addValueChangeListener {
            viewModel.updateView()
          }
        }

        edtNfe = textField("NI/NFE") {
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

        cmbEstoque = select("Estoque Total") {
          setItems(EEstoqueTotal.values().toList())
          value = EEstoqueTotal.TODOS
          this.setItemLabelGenerator {
            it.descricao
          }
          addValueChangeListener {
            viewModel.updateView()
          }
        }

        edtListVend = textField("Fornecedores") {
          this.valueChangeMode = ValueChangeMode.LAZY
          this.width = "250px"
          addValueChangeListener {
            viewModel.updateView()
          }
        }

        edtTributacao = textField("Tributação") {
          this.valueChangeMode = ValueChangeMode.LAZY
          this.width = "80px"
          addValueChangeListener {
            viewModel.updateView()
          }
        }

        edtType = integerField("Tipo") {
          this.valueChangeMode = ValueChangeMode.LAZY
          addValueChangeListener {
            viewModel.updateView()
          }
        }

        edtCl = integerField("Centro de Lucro") {
          this.valueChangeMode = ValueChangeMode.LAZY
          addValueChangeListener {
            viewModel.updateView()
          }
        }

        downloadExcel()

        label(" ")
      }
      horizontalLayout {
        edtDiVenda = datePicker("Data Venda Inicial") {
          this.localePtBr()
          this.value = LocalDate.now().withDayOfMonth(1)

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
      }
    }
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
    FiltroValidadeEntrada(
      query = edtQuery.value,
      marca = cmbPontos.value,
      listVend = edtListVend.value?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList(),
      tributacao = edtTributacao.value ?: "",
      typeno = edtType.value ?: 0,
      clno = edtCl.value ?: 0,
      estoque = cmbEstoque.value ?: EEstoqueTotal.TODOS,
      diVenda = edtDiVenda.value ?: LocalDate.now(),
      dfVenda = edtDfVenda.value ?: LocalDate.now(),
      nfe = edtNfe.value,
    )

  override fun Grid<ValidadeEntrada>.gridPanel() {
    this.setSelectionMode(Grid.SelectionMode.MULTI)
    addColumnSeq("Seq")
    produtoLoja()
    produtoCodigo()
    produtoDescricao()
    produtoGrade()
    produtoLoc()
    produtoEstoque()
    produtoVenda()
    produtoSaldo()
    produtoMesesVenc()
    produtoSaldoDS()
    produtoSaldoMR()
    produtoSaldoMF()
    produtoSaldoPK()
    produtoSaldoTM()
    produtValidade()
    produtoMesesFab()
    produtoEntrada()
    produtoNFEntrada()
    produtoDataEntrada()
    produtoFabricacao()
    produtoVencimento()
  }
}


