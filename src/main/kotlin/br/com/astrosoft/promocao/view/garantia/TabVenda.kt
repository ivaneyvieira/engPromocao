package br.com.astrosoft.promocao.view.garantia

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.addColumnSeq
import br.com.astrosoft.promocao.model.beans.*
import br.com.astrosoft.promocao.model.planilhas.PlanilhaGarantiaVenda
import br.com.astrosoft.promocao.model.planilhas.PlanilhaValidadeEntrada
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoCodigo
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoDescricao
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoEstoque
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoGrade
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoLoc
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoLoja
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoMesesVenc
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoVenda00
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoVenda01
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoVenda02
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoVenda03
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoVenda04
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoVenda05
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoVenda06
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoVenda07
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoVenda08
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoVenda09
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoVenda10
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoVenda11
import br.com.astrosoft.promocao.view.garantia.columns.VendasCol.produtoVenda12
import br.com.astrosoft.promocao.viewmodel.garantia.ITabVendaViewModel
import br.com.astrosoft.promocao.viewmodel.garantia.TabVendaViewModel
import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.kaributools.tooltip
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
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

class TabVenda(val viewModel: TabVendaViewModel) :
    TabPanelGrid<VendaEntrada>(VendaEntrada::class),
    ITabVendaViewModel {
    private lateinit var edtCl: IntegerField
    private lateinit var edtType: IntegerField
    private lateinit var edtTributacao: TextField
    private lateinit var edtListVend: TextField
    private lateinit var cmbEstoque: Select<EEstoqueTotal>
    private lateinit var edtNfe: TextField

    private lateinit var edtQuery: TextField
    private lateinit var cmbPontos: Select<EMarcaPonto>

    override fun updateComponent() {
        viewModel.updateView()
    }

    override fun listSelected(): List<VendaEntrada> {
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
        }
    }

    private fun HasComponents.downloadExcel() {
        val button = LazyDownloadButton(VaadinIcon.TABLE.create(), { filename() }, {
            val planilha = PlanilhaGarantiaVenda()
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

    override fun isAuthorized(user: IUser) = (user as? UserSaci)?.garantiaVenda ?: false
    override val label: String
        get() = "Venda"

    override fun filtro() =
        FiltroValidadeEntrada(
            query = edtQuery.value,
            marca = cmbPontos.value,
            listVend = edtListVend.value?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList(),
            tributacao = edtTributacao.value ?: "",
            typeno = edtType.value ?: 0,
            clno = edtCl.value ?: 0,
            estoque = cmbEstoque.value ?: EEstoqueTotal.TODOS,
            diVenda = LocalDate.now().withDayOfMonth(1).minusMonths(12).withDayOfMonth(1),
            dfVenda = LocalDate.now(),
            nfe = edtNfe.value,
        )

    override fun Grid<VendaEntrada>.gridPanel() {
        this.setSelectionMode(Grid.SelectionMode.MULTI)
        this.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS)
        addColumnSeq("Seq")
        produtoLoja()
        produtoCodigo()
        produtoDescricao()
        produtoGrade()
        produtoLoc()
        produtoEstoque()
        produtoMesesVenc()

        produtoVenda00()
        produtoVenda01()
        produtoVenda02()
        produtoVenda03()
        produtoVenda04()
        produtoVenda05()
        produtoVenda06()
        produtoVenda07()
        produtoVenda08()
        produtoVenda09()
        produtoVenda10()
        produtoVenda11()
        produtoVenda12()
    }
}


