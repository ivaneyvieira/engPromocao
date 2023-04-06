package br.com.astrosoft.promocao.view.preco

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.promocao.model.beans.PrecoAlteracao
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoCentroLucro
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoCodigo
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoDesconto
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoDescricao
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoFornecedor
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoMetroCaixa
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoPrecoPromocional
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoPrecoPromocionalM
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoPrecoRef
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoPrecoRefM
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoTipoProduto
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoValidade
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoVendno
import br.com.astrosoft.promocao.viewmodel.preco.ITabBaseAlteradoViewModel
import br.com.astrosoft.promocao.viewmodel.preco.TabBaseAlteradoViewModel
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class TabBasePreco(viewModel: TabBaseAlteradoViewModel) :
  TabAbstractPreco<ITabBaseAlteradoViewModel>(viewModel, showDatas = false), ITabBaseAlteradoViewModel {
  override fun isAuthorized(user: IUser) = (user as? UserSaci)?.precoBase ?: false

  override val label: String
    get() = "Base"

  override fun HorizontalLayout.addAditionaisFields() {
  }

  override fun Grid<PrecoAlteracao>.colunasGrid() {
    precoCodigo()
    precoDescricao()
    precoVendno()
    precoFornecedor()
    precoTipoProduto()
    precoCentroLucro()
    precoMetroCaixa()
    precoPrecoRef()
    precoPrecoRefM()
    precoValidade()
    precoPrecoPromocional()
    precoPrecoPromocionalM()
    precoDesconto()
  }
}