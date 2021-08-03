package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao
import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao.*
import br.com.astrosoft.promocao.model.beans.FiltroPrecoPromocao
import br.com.astrosoft.promocao.model.beans.PrecoPromocao
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoCentroLucro
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoCodigo
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoDescricao
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoFornecedor
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoPrecificacao
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoPrecoPromo
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoPrecoVista
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoPromoDescricao
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoTipoProduto
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoValidade
import br.com.astrosoft.promocao.viewmodel.promocao.ITabAbstractPromocaoViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.TabAbstractPromocaoViewModel
import com.github.mvysny.karibudsl.v10.integerField
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.Grid.SelectionMode.MULTI
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.data.value.ValueChangeMode.TIMEOUT

abstract class TabAbstractPromocao<T : ITabAbstractPromocaoViewModel>(val viewModel: TabAbstractPromocaoViewModel<T>) :
        TabPanelGrid<PrecoPromocao>(PrecoPromocao::class), ITabAbstractPromocaoViewModel {
  private lateinit var edtVend: IntegerField
  private lateinit var edtCl: IntegerField
  private lateinit var edtType: IntegerField

  override fun updateComponent() {
    viewModel.updateView()
  }

  override val label: String
    get() = viewModel.tipoTab.descricao

  abstract fun tipo(): ETipoListaPromocao

  override fun HorizontalLayout.toolBarConfig() {
    edtVend = integerField("Fornecedor") {
      this.valueChangeMode = TIMEOUT
      addValueChangeListener {
        viewModel.updateView()
      }
    }

    edtCl = integerField("Centro de Lucro") {
      this.valueChangeMode = TIMEOUT
      addValueChangeListener {
        viewModel.updateView()
      }
    }

    edtType = integerField("Tipo de produto") {
      this.valueChangeMode = TIMEOUT
      addValueChangeListener {
        viewModel.updateView()
      }
    }
  }

  override fun isAuthorized(user: IUser) = true

  override fun filtro() = FiltroPrecoPromocao(vendno = edtVend.value ?: 0,
                                              clno = edtCl.value ?: 0,
                                              typeno = edtType.value ?: 0,
                                              tipoLista = viewModel.tipoTab)

  override fun Grid<PrecoPromocao>.gridPanel() {
    setSelectionMode(MULTI) // addColumnButton(VaadinIcon.FILE_TABLE, "Notas", "Notas") { fornecedor ->
    //   DlgNotaPainelNddSaci(viewModel).showDialogNota(fornecedor)
    // }

    promocaoCodigo()
    promocaoDescricao()
    promocaoPrecoVista()

    when (tipo()) {
      BASE         -> {
        promocaoFornecedor()
        promocaoCentroLucro()
        promocaoTipoProduto()
      }
      PRECIFICACAO -> {
        promocaoPrecificacao()
        promocaoValidade()
      }
      PROMOCAO     -> {
        promocaoPrecificacao()
        promocaoValidade()
        promocaoPromoDescricao()
      }
    }
  }
}
