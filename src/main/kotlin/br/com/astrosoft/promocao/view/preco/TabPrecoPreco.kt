package br.com.astrosoft.promocao.view.preco

import br.com.astrosoft.promocao.model.beans.PrecoAlteracao
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoCentroLucro
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoCodigo
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoDataAlteracao
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoDesconto
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoDescricao
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoFornecedor
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoHoraAlteracao
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoMetroCaixa
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoPrecoPromocional
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoPrecoRef
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoPrecoRefAnt
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoTipoProduto
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoUsuario
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoValidade
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
    precoDataAlteracao()
    precoHoraAlteracao()
    precoUsuario()
    precoVendno()
    precoFornecedor()
    precoTipoProduto()
    precoCentroLucro()
    precoMetroCaixa()
    precoPrecoRefAnt()
    precoPrecoRef()
    precoValidade()
    precoPrecoPromocional()
    precoDesconto()
  }
}