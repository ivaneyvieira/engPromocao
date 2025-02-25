package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.model.BeanForm
import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.addColumnSeq
import br.com.astrosoft.framework.view.shiftSelect
import br.com.astrosoft.promocao.model.beans.EMarcaPonto
import br.com.astrosoft.promocao.model.beans.FiltroPrecificacao
import br.com.astrosoft.promocao.model.beans.Precificacao
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.model.planilhas.PlanilhaPrecificacao
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoCS
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoClno
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoCodigo
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoCpmf
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoDescricao
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoDesp
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoFCP
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoIR
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoIcmsEnt
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoIcmsSai
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoLucro
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoMva
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoNcm
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoOut
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoPDif
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoPRef
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoPSug
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoPis
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoRotulo
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoTributacao
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoTypeno
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoVendno
import br.com.astrosoft.promocao.viewmodel.promocao.ITabPrecificacaoViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.TabPrecificacaoSaidaViewModel
import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.kaributools.tooltip
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.textfield.BigDecimalField
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode
import org.vaadin.stefan.LazyDownloadButton
import java.io.ByteArrayInputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TabPrecificacaoSaida(val viewModel: TabPrecificacaoSaidaViewModel) : TabPanelGrid<Precificacao>
  (Precificacao::class),
  ITabPrecificacaoViewModel {
  private lateinit var edtCodigo: IntegerField
  private lateinit var edtListVend: TextField
  private lateinit var edtType: TextField
  private lateinit var edtCl: IntegerField
  private lateinit var edtTributacao: TextField
  private lateinit var cmbPontos: Select<EMarcaPonto>
  private lateinit var edtQuery: TextField
  private lateinit var edtMva: BigDecimalField
  private lateinit var edtIcmsEnt: BigDecimalField

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

    edtMva = bigDecimalField("MVA") {
      this.valueChangeMode = ValueChangeMode.LAZY
      addValueChangeListener {
        viewModel.updateView()
      }
    }

    edtIcmsEnt = bigDecimalField("ICMS Ent") {
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
          val dialog = DialogPrecificacao(viewModel, BeanForm(), cardEntrada = false, cardSaida = true)
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
    promocaoIcmsSai()
    promocaoFCP()
    promocaoPis()
    promocaoIR()
    promocaoCS()
    promocaoCpmf()
    promocaoDesp()
    promocaoOut()
    promocaoLucro()
    promocaoPSug()
    promocaoPRef()
    promocaoPDif()
  }

  override fun filtro(): FiltroPrecificacao {
    return FiltroPrecificacao(
      codigo = edtCodigo.value ?: 0,
      listVend = edtListVend.value?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList(),
      tributacao = edtTributacao.value ?: "",
      typeno = edtType.value ?: "",
      clno = edtCl.value ?: 0,
      marcaPonto = cmbPontos.value ?: EMarcaPonto.TODOS,
      mvap = edtMva.value?.toDouble(),
      icmsEnt = edtIcmsEnt.value?.toDouble(),
      query = edtQuery.value ?: "",
    )
  }

  override fun listSelected(): List<Precificacao> {
    return itensSelecionados()
  }

  override fun isAuthorized(user: IUser) = (user as? UserSaci)?.promocaoPrecificacaoSaida ?: false

  override val label: String
    get() = "Precificação Saída"

  override fun updateComponent() {
    viewModel.updateView()
  }
}