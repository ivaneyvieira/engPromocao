package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.model.BeanForm
import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.addColumnSeq
import br.com.astrosoft.framework.view.shiftSelect
import br.com.astrosoft.promocao.model.beans.FiltroPrecificacao
import br.com.astrosoft.promocao.model.beans.Precificacao
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoCS
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoClno
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoCodigo
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoContabil
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoCpmf
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoDescricao
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoDesp
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoEmbalagem
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoFCP
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoFrete
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoIR
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoIcms
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoIcmsEnt
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoIcmsSai
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoIpi
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoLucro
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoMva
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoOut
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoPFabrica
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoPRef
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoPSug
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoPis
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoRetido
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoTributacao
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoTypeno
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoVendno
import br.com.astrosoft.promocao.viewmodel.promocao.ITabPrecificacaoViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.TabPrecificacaoViewModel
import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.checkbox.Checkbox
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode

class TabPrecificacao(val viewModel: TabPrecificacaoViewModel) : TabPanelGrid<Precificacao>(Precificacao::class),
        ITabPrecificacaoViewModel {
  private lateinit var edtCodigo: IntegerField
  private lateinit var edtListVend: TextField
  private lateinit var edtType: IntegerField
  private lateinit var edtCl: IntegerField
  private lateinit var edtTributacao: TextField
  private lateinit var edtMarcaPonto: Checkbox
  override fun HorizontalLayout.toolBarConfig() {
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

    edtMarcaPonto = checkBox("Marca Ponto") {
      addValueChangeListener {
        viewModel.updateView()
      }
    }

    button("Mudar %") {
      onLeftClick {
        val itens = itensSelecionados()
        if (itens.isEmpty()) {
          showErro("Nenhum item selecionado")
        }
        else {
          val cpmf = itens.firstOrNull()?.cpmf
          val fcp = itens.firstOrNull()?.fcp
          val dialog = DialogPrecificacao(viewModel, BeanForm())
          dialog.open()
        }
      }
    }
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
    promocaoTributacao()
    promocaoMva()
    promocaoIcmsEnt()
    promocaoPFabrica()
    promocaoIpi()
    promocaoEmbalagem()
    promocaoRetido()
    promocaoIcms()
    promocaoFrete()
    promocaoContabil()
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
  }

  override fun filtro(): FiltroPrecificacao {
    return FiltroPrecificacao(codigo = edtCodigo.value ?: 0,
                              listVend = edtListVend.value?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList(),
                              tributacao = edtTributacao.value ?: "",
                              typeno = edtType.value ?: 0,
                              clno = edtCl.value ?: 0,
                              marcadoPonto = edtMarcaPonto.value ?: false)
  }

  override fun listSelected(): List<Precificacao> {
    return itensSelecionados()
  }

  override fun isAuthorized(user: IUser) = (user as? UserSaci)?.promocaoPrecificacao ?: false

  override val label: String
    get() = "Precificação"

  override fun updateComponent() {
    viewModel.updateView()
  }
}