package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.shiftSelect
import br.com.astrosoft.promocao.model.beans.FiltroPrecoPromocao
import br.com.astrosoft.promocao.model.beans.PrecoPromocao
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoCentroLucro
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoCodigo
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoDesconto
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoDescricao
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoFornecedor
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoPrecoPromocional
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoPrecoRef
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoTipoProduto
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoValidade
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoVendno
import br.com.astrosoft.promocao.viewmodel.promocao.*
import com.github.mvysny.karibudsl.v10.integerField
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.Grid.SelectionMode.MULTI
import com.vaadin.flow.component.grid.Grid.SelectionMode.SINGLE
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.data.value.ValueChangeMode.TIMEOUT

abstract class TabAbstractPromocao<T : ITabAbstractPromocaoViewModel>(open val viewModel: TabAbstractPromocaoViewModel<T>) :
        TabPanelGrid<PrecoPromocao>(PrecoPromocao::class), ITabAbstractPromocaoViewModel {
  private lateinit var edtCodigo: IntegerField
  private lateinit var edtVend: IntegerField
  private lateinit var edtCl: IntegerField
  private lateinit var edtType: IntegerField

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun listSelected(): List<PrecoPromocao> {
    return itensSelecionados()
  }

  override fun HorizontalLayout.toolBarConfig() {
    edtCodigo = integerField("Código") {
      this.valueChangeMode = TIMEOUT
      addValueChangeListener {
        viewModel.updateView()
      }
    }

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

    addAditionaisFields()
  }

  protected abstract fun HorizontalLayout.addAditionaisFields()

  override fun isAuthorized(user: IUser) = true

  override fun filtro() = FiltroPrecoPromocao(codigo = edtCodigo.value ?: 0,
                                              vendno = edtVend.value ?: 0,
                                              clno = edtCl.value ?: 0,
                                              typeno = edtType.value ?: 0,
                                              tipoLista = viewModel.tipoTab)

  override fun Grid<PrecoPromocao>.gridPanel() {
    when (viewModel) {
      is TabBaseViewModel        -> setSelectionMode(SINGLE)
      is TabPromocaoViewModel    -> {
        setSelectionMode(MULTI)
        this.shiftSelect()
      }
      is TabSemPromocaoViewModel -> {
        setSelectionMode(MULTI)
        this.shiftSelect()
      }
    }

    promocaoCodigo()
    promocaoDescricao()
    promocaoValidade()
    promocaoPrecoRef()
    promocaoDesconto()
    promocaoPrecoPromocional()
    promocaoVendno()
    promocaoFornecedor()
    promocaoTipoProduto()
    promocaoCentroLucro()
  }
}


