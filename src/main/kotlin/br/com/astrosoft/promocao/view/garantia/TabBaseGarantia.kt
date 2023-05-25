package br.com.astrosoft.promocao.view.garantia

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.addColumnSeq
import br.com.astrosoft.promocao.model.beans.ComparaValidade
import br.com.astrosoft.promocao.model.beans.ETipoDiferencaGarantia
import br.com.astrosoft.promocao.model.beans.FiltroValidade
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.model.planilhas.PlanilhaBaseValidade
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaCodigo
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaDescicao
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaDiferenca
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaEstoque
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaGrade
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaLocalizacao
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaTipoDiferenca
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaValidadeCad
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaValidadeDesc
import br.com.astrosoft.promocao.viewmodel.garantia.ITabBaseGarantiaViewModel
import br.com.astrosoft.promocao.viewmodel.garantia.TabBaseGarantiaViewModel
import com.github.mvysny.karibudsl.v10.integerField
import com.github.mvysny.karibudsl.v10.select
import com.github.mvysny.karibudsl.v10.textField
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

class TabBaseGarantia(val viewModel: TabBaseGarantiaViewModel) : TabPanelGrid<ComparaValidade>(ComparaValidade::class),
    ITabBaseGarantiaViewModel {
    private lateinit var edtCl: IntegerField
    private lateinit var edtType: IntegerField
    private lateinit var edtTributacao: TextField
    private lateinit var edtListVend: TextField
    private lateinit var cmbTipoGarantia: Select<ETipoDiferencaGarantia>
    private lateinit var edtQuery: TextField

    override fun updateComponent() {
        viewModel.updateView()
    }

    override fun listSelected(): List<ComparaValidade> {
        return itensSelecionados()
    }

    override fun HorizontalLayout.toolBarConfig() {
        cmbTipoGarantia = select("Tipo Diferença") {
            this.setItems(ETipoDiferencaGarantia.values().toList())
            this.value = ETipoDiferencaGarantia.TODOS
            this.width = "250px"
            this.setItemLabelGenerator {
                it.descricao
            }

            this.addValueChangeListener {
                viewModel.updateView()
            }
        }

        edtQuery = textField("Filtro") {
            this.valueChangeMode = ValueChangeMode.TIMEOUT
            this.addValueChangeListener {
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
    }

    private fun HasComponents.downloadExcel() {
        val button = LazyDownloadButton(VaadinIcon.TABLE.create(), { filename() }, {
            val planilha = PlanilhaBaseValidade()
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

    override fun isAuthorized(user: IUser) = (user as? UserSaci)?.garantiaBase ?: false
    override val label: String
        get() = "Base"

    override fun filtro() =
        FiltroValidade(
            tipo = cmbTipoGarantia.value ?: ETipoDiferencaGarantia.TODOS,
            query = edtQuery.value,
            listVend = edtListVend.value?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList(),
            tributacao = edtTributacao.value ?: "",
            typeno = edtType.value ?: 0,
            clno = edtCl.value ?: 0,
        )

    override fun Grid<ComparaValidade>.gridPanel() {
        this.setSelectionMode(Grid.SelectionMode.MULTI)
        addColumnSeq("Seq")
        garantiaCodigo()
        garantiaDescicao()
        garantiaGrade()
        garantiaLocalizacao()
        garantiaEstoque()
        garantiaValidadeDesc()
        garantiaValidadeCad()
        garantiaDiferenca()
        garantiaTipoDiferenca()
    }
}


