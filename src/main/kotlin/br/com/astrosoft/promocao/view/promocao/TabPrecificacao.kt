package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.shiftSelect
import br.com.astrosoft.promocao.model.beans.FiltroPrecificacao
import br.com.astrosoft.promocao.model.beans.Precificacao
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoClno
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoCodigo
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoCpmf
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoDescricao
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoFornecedor
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoTributacao
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoTypeno
import br.com.astrosoft.promocao.view.promocao.columns.PrecificacaoColumns.promocaoVendno
import br.com.astrosoft.promocao.viewmodel.promocao.ITabPrecificacaoViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.TabPrecificacaoViewModel
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.integerField
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.github.mvysny.karibudsl.v10.textField
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

    button("Mudar %") {
      onLeftClick {
        val itens = itensSelecionados()
        if (itens.isEmpty()) {
          showErro("Nenhum item selecionado")
        }
        else {
          val cpmf = itens.firstOrNull()?.cpmf
          val dialog = DialogCpmf(viewModel, cpmf)
          dialog.open()
        }
      }
    }
  }

  override fun Grid<Precificacao>.gridPanel() {
    setSelectionMode(Grid.SelectionMode.MULTI)
    this.shiftSelect()

    promocaoCodigo()
    promocaoDescricao()
    promocaoVendno()
    promocaoFornecedor()
    promocaoTypeno()
    promocaoClno()
    promocaoTributacao()
    promocaoCpmf()
  }

  override fun filtro(): FiltroPrecificacao {
    return FiltroPrecificacao(
      codigo = edtCodigo.value ?: 0,
      listVend = edtListVend.value?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList(),
      tributacao = edtTributacao.value ?: "",
      typeno = edtType.value ?: 0,
      clno = edtCl.value ?: 0,
                             )
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