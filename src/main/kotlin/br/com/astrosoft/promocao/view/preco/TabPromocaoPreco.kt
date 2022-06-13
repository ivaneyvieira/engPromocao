package br.com.astrosoft.promocao.view.preco

import br.com.astrosoft.promocao.model.beans.PrecoAlteracao
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoCentroLucro
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoCodigo
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoDataAlteracao
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoDescricao
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoFornecedor
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoHoraAlteracao
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoPrecoPromocionalHist
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoTipoProduto
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoUsuario
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoValidadePromocionalHist
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoVendno
import br.com.astrosoft.promocao.viewmodel.preco.ITabPromocaoAlteradoViewModel
import br.com.astrosoft.promocao.viewmodel.preco.TabPromocaoAlteradoViewModel
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class TabPromocaoPreco(viewModel: TabPromocaoAlteradoViewModel) :
        TabAbstractPreco<ITabPromocaoAlteradoViewModel>(viewModel), ITabPromocaoAlteradoViewModel {
  override val label: String
    get() = "Preço Promocional"

  override fun HorizontalLayout.addAditionaisFields() {
  }

  override fun Grid<PrecoAlteracao>.colunasGrid() {
    precoCodigo()
    precoDescricao()
    precoPrecoPromocionalHist()
    precoValidadePromocionalHist()
    precoDataAlteracao()
    precoHoraAlteracao()
    precoUsuario()
    precoVendno()
    precoFornecedor()
    precoTipoProduto()
    precoCentroLucro()
  }
}