package br.com.astrosoft.promocao.view.garantia

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.*
import br.com.astrosoft.promocao.model.beans.*
import br.com.astrosoft.promocao.model.planilhas.PlanilhaValidadeEntrada
import br.com.astrosoft.promocao.view.garantia.columns.EstoqueAppCol.estoqueAppCodigo
import br.com.astrosoft.promocao.view.garantia.columns.EstoqueAppCol.estoqueAppDescricao
import br.com.astrosoft.promocao.view.garantia.columns.EstoqueAppCol.estoqueAppGrade
import br.com.astrosoft.promocao.view.garantia.columns.EstoqueAppCol.estoqueAppLocalizacao
import br.com.astrosoft.promocao.view.garantia.columns.EstoqueAppCol.estoqueDataEntrada
import br.com.astrosoft.promocao.view.garantia.columns.EstoqueAppCol.estoqueEntrada
import br.com.astrosoft.promocao.view.garantia.columns.EstoqueAppCol.estoqueEstoqueApp
import br.com.astrosoft.promocao.view.garantia.columns.EstoqueAppCol.estoqueEstoqueLoja
import br.com.astrosoft.promocao.view.garantia.columns.EstoqueAppCol.estoqueEstoqueNerus
import br.com.astrosoft.promocao.view.garantia.columns.EstoqueAppCol.estoqueNotaEntrada
import br.com.astrosoft.promocao.view.garantia.columns.EstoqueAppCol.estoqueSaldo
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
import br.com.astrosoft.promocao.viewmodel.garantia.ITabEstoqueAppGarantiaViewModel
import br.com.astrosoft.promocao.viewmodel.garantia.TabEstoqueAppGarantiaViewModel
import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.kaributools.tooltip
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.checkbox.Checkbox
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TabEstoqueAppGarantia(val viewModel: TabEstoqueAppGarantiaViewModel) :
    TabPanelGrid<GarantiaEstoqueApp>(GarantiaEstoqueApp::class),
    ITabEstoqueAppGarantiaViewModel {
    private lateinit var edtCl: IntegerField
    private lateinit var edtType: IntegerField
    private lateinit var edtListVend: TextField
    private lateinit var cmbEstoque: Select<EEstoqueTotal>
    private lateinit var edtNfe: TextField

    private lateinit var edtQuery: TextField
    private lateinit var cmbPontos: Select<EMarcaPonto>
    private lateinit var edtCodigo: IntegerField
    private lateinit var chkGrade: Checkbox
    private lateinit var edtGrade: TextField

    override fun updateComponent() {
        viewModel.updateView()
    }

    override fun listSelected(): List<GarantiaEstoqueApp> {
        return itensSelecionados()
    }

    override fun HorizontalLayout.toolBarConfig() {
        flexLayout {
            this.isExpand = true
            this.flexWrap = FlexLayout.FlexWrap.WRAP
            this.alignContent = FlexLayout.ContentAlignment.SPACE_BETWEEN
            horizontalLayout {
                edtCodigo = integerField("") {
                    this.valueChangeMode = ValueChangeMode.TIMEOUT
                    this.valueChangeMode = ValueChangeMode.LAZY
                    this.addValueChangeListener {
                        viewModel.updateView()
                    }
                }

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

                chkGrade = checkBox("Grade") {
                    this.value = true
                    this.addValueChangeListener {
                        viewModel.updateView()
                    }
                }

                edtGrade = textField("Grade") {
                    this.valueChangeMode = ValueChangeMode.LAZY
                    this.width = "80px"
                    addValueChangeListener {
                        viewModel.updateView()
                    }
                }

                downloadExcel()
            }
        }
    }

    private fun HasComponents.downloadExcel() {
        val button = LazyDownloadButton(VaadinIcon.TABLE.create(), { filename() }, {
            val planilha = PlanilhaValidadeEntrada()
            val list = itensSelecionados()
            val bytes = planilha.grava(emptyList())
            ByteArrayInputStream(bytes)
        })
        button.addThemeVariants(ButtonVariant.LUMO_SMALL)
        button.tooltip = "Salva a planilha"
        add(button)
    }

    private fun filename(): String {
        val sdf = DateTimeFormatter.ofPattern("yyMMddHHmmss")
        val textTime = LocalDateTime.now().format(sdf)
        return "EstoqueAppGarantia$textTime.xlsx"
    }

    override fun isAuthorized(user: IUser) = (user as? UserSaci)?.garantiaEstoqueApp ?: false
    override val label: String
        get() = "Estoque App"

    override fun filtro() =
        FiltroEstoqueApp(
            query = edtQuery.value,
            marca = cmbPontos.value,
            listVend = edtListVend.value?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList(),
            typeno = edtType.value ?: 0,
            clno = edtCl.value ?: 0,
            estoque = cmbEstoque.value ?: EEstoqueTotal.TODOS,
            nfe = edtNfe.value,
            codigo =  edtCodigo.value ?: 0,
            temGrade = chkGrade.value ?: false,
            grade = edtGrade.value ?: ""
        )

    override fun Grid<GarantiaEstoqueApp>.gridPanel() {
        this.setSelectionMode(Grid.SelectionMode.MULTI)
        addColumnSeq("Seq")
        estoqueAppCodigo()
        estoqueAppDescricao()
        estoqueAppGrade()
        estoqueAppLocalizacao()
        estoqueEstoqueNerus()
        estoqueEstoqueLoja()
        estoqueEstoqueApp()
        estoqueSaldo()
        estoqueEntrada()
        estoqueNotaEntrada()
        estoqueDataEntrada()
    }
}


