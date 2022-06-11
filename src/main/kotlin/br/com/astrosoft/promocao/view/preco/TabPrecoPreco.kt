package br.com.astrosoft.promocao.view.preco

import br.com.astrosoft.promocao.model.beans.PrecoAlteracao
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoAlteracao
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoCentroLucro
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoCodigo
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoDescricao
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoFornecedor
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoPrecoAnterior
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoPrecoAtual
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoTipoProduto
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoVendno
import br.com.astrosoft.promocao.viewmodel.preco.ITabPrecoAlteradoViewModel
import br.com.astrosoft.promocao.viewmodel.preco.TabPrecoAlteradoViewModel
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class TabPrecoPreco(viewModel: TabPrecoAlteradoViewModel) : TabAbstractPreco<ITabPrecoAlteradoViewModel>(viewModel),
        ITabPrecoAlteradoViewModel {
  override val label: String
    get() = "Preço Referência"

  override fun HorizontalLayout.addAditionaisFields() {
  }

  override fun Grid<PrecoAlteracao>.colunasGrid() {
    precoCodigo()
    precoDescricao()
    precoPrecoAnterior()
    precoPrecoAtual()
    precoAlteracao()
    precoVendno()
    precoFornecedor()
    precoTipoProduto()
    precoCentroLucro()
  }
}