package br.com.astrosoft.promocao.view.preco

import br.com.astrosoft.framework.model.Config.user
import br.com.astrosoft.promocao.model.EtiquetaChave
import br.com.astrosoft.promocao.model.beans.PrecoAlteracao
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoAlteracao
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoCentroLucro
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoCodigo
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoDesconto
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoDescricao
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoFornecedor
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoPrecoAnterior
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoPrecoAtual
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoPrecoPromocional
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoPrecoRef
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoTipoProduto
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoValidade
import br.com.astrosoft.promocao.view.preco.columns.NotaNddViewColumns.precoVendno
import br.com.astrosoft.promocao.viewmodel.preco.ITabPromocaoAlteradoViewModel
import br.com.astrosoft.promocao.viewmodel.preco.TabPromocaoAlteradoViewModel
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class TabPromocaoPreco(viewModel: TabPromocaoAlteradoViewModel) : TabAbstractPreco<ITabPromocaoAlteradoViewModel>
  (viewModel),
        ITabPromocaoAlteradoViewModel {
  override val label: String
    get() = "Preço Promocional"

  override fun HorizontalLayout.addAditionaisFields() {
  }

  override fun Grid<PrecoAlteracao>.colunasGrid() {
    precoCodigo()
    precoDescricao()
    precoPrecoAnterior()
    precoPrecoAtual()
    precoAlteracao()
    precoDesconto()
    precoValidade()
    precoVendno()
    precoFornecedor()
    precoTipoProduto()
    precoCentroLucro()
  }
}