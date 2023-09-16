package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.model.BeanForm
import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.util.format
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.addColumnSeq
import br.com.astrosoft.framework.view.shiftSelect
import br.com.astrosoft.promocao.model.beans.EMarcaPonto
import br.com.astrosoft.promocao.model.beans.FiltroPrecificacao
import br.com.astrosoft.promocao.model.beans.Precificacao
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.model.planilhas.PlanilhaPrecificacao
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoCFinanceiro
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoClno
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoCodigo
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoContabil
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoDescricao
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoDiferenca
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoEmbalagem
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoFrete
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoFreteIcms
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoFreteIcmsCalc
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoIcms
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoIcmsEnt
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoIpi
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoMva
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoMvaMa04
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoMvaMa07
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoMvaMa12
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoMvaMaOriginal
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoNcm
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoPFabrica
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoPrecoCusto
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoRetido
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoRotulo
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoTributacao
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoTypeno
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoVendno
import br.com.astrosoft.promocao.viewmodel.promocao.ITabPrecificacaoViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.TabPrecificacaoEntradaMaViewModel
import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.kaributools.tooltip
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode
import org.vaadin.stefan.LazyDownloadButton
import java.io.ByteArrayInputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TabPrecificacaoEntradaMa(val viewModel: TabPrecificacaoEntradaMaViewModel) : TabPanelGrid<Precificacao>
    (Precificacao::class), ITabPrecificacaoViewModel {
    private lateinit var edtCodigo: IntegerField
    private lateinit var edtListVend: TextField
    private lateinit var edtType: TextField
    private lateinit var edtCl: IntegerField
    private lateinit var edtTributacao: TextField
    private lateinit var cmbPontos: Select<EMarcaPonto>
    private lateinit var edtQuery: TextField

    override fun HorizontalLayout.toolBarConfig() {
        edtQuery = textField("Pesquisa") {
            this.valueChangeMode = ValueChangeMode.LAZY
            addValueChangeListener {
                viewModel.updateView()
            }
        }

        edtCodigo = integerField("Código") {
            this.valueChangeMode = ValueChangeMode.LAZY
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

        edtType = textField("Tipo") {
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

        button("Mudar %") {
            onLeftClick {
                val itens = itensSelecionados()
                if (itens.isEmpty()) {
                    showErro("Nenhum item selecionado")
                } else {
                    val dialog = DialogPrecificacao(viewModel, BeanForm(), cardEntrada = true, cardSaida = false)
                    dialog.open()
                }
            }
        }

        downloadExcel()
    }

    private fun HasComponents.downloadExcel() {
        val button = LazyDownloadButton(VaadinIcon.TABLE.create(), { filename() }, {
            val planilha = PlanilhaPrecificacao()
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

    override fun Grid<Precificacao>.gridPanel() {
        setSelectionMode(Grid.SelectionMode.MULTI)
        this.shiftSelect()

        addColumnSeq("Seq")
        promocaoCodigo()
        promocaoDescricao()
        promocaoVendno() //promocaoFornecedor()
        promocaoTypeno()
        promocaoClno()
        promocaoNcm()
        promocaoRotulo()
        promocaoTributacao()
        promocaoMva()
        promocaoIcmsEnt()
        promocaoMvaMaOriginal()
        promocaoMvaMa04()
        promocaoMvaMa07()
        promocaoMvaMa12()
        promocaoPFabrica()
        promocaoIpi()
        promocaoEmbalagem()
        promocaoRetido()
        promocaoIcms()
        promocaoFrete()
        promocaoFreteIcmsCalc().apply {
            this.setClassNameGenerator {
                if (it.freteICMS.format() != it.freteICMSCalc.format()) "marcaDiferenca" else null
            }
        }
        promocaoFreteIcms().apply {
            this.setClassNameGenerator {
                if (it.freteICMS.format() != it.freteICMSCalc.format()) "marcaDiferenca" else null
            }
        }
        promocaoContabil().apply {
            this.setClassNameGenerator {
                if (it.custoContabil.format() != it.precoCusto.format()) "marcaDiferenca" else null
            }
        }
        promocaoCFinanceiro()
        promocaoPrecoCusto().apply {
            this.setClassNameGenerator {
                if (it.custoContabil.format() != it.precoCusto.format()) "marcaDiferenca" else null
            }
        }
        promocaoDiferenca().apply {
            this.setClassNameGenerator {
                if (it.custoContabil.format() != it.precoCusto.format()) "marcaDiferenca" else null
            }
        }
    }

    override fun filtro(): FiltroPrecificacao {
        return FiltroPrecificacao(
            codigo = edtCodigo.value ?: 0,
            listVend = edtListVend.value?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList(),
            tributacao = edtTributacao.value ?: "",
            typeno = edtType.value ?: "",
            clno = edtCl.value ?: 0,
            marcaPonto = cmbPontos.value ?: EMarcaPonto.TODOS,
            query = edtQuery.value ?: "",
        )
    }

    override fun listSelected(): List<Precificacao> {
        return itensSelecionados()
    }

    override fun isAuthorized(user: IUser) = (user as? UserSaci)?.promocaoPrecificacaoEntradaMa ?: false

    override val label: String
        get() = "Prec Ent MA"

    override fun updateComponent() {
        viewModel.updateView()
    }
}