package br.com.astrosoft.promocao.view.produtos

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.addColumnSeq
import br.com.astrosoft.promocao.model.beans.Produtos
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_DS_TT
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_MF_TT
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_MR_TT
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_PK_TT
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_TM_TT
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_abrev
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_cl
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_codBar
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_codigo
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_descricao
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_estoque
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_forn
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_grade
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_tipo
import br.com.astrosoft.promocao.viewmodel.produto.ITabEstoqueTotalViewModel
import br.com.astrosoft.promocao.viewmodel.produto.TabEstoqueTotalViewModel
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class TabEstoqueTotalProduto(viewModel: TabEstoqueTotalViewModel) :
        TabAbstractProduto<ITabEstoqueTotalViewModel>(viewModel, showDatas = false), ITabEstoqueTotalViewModel {
  override fun isAuthorized(user: IUser) = (user as? UserSaci)?.produtoBase ?: false

  override val label: String
    get() = "Estoque Total"

  override fun HorizontalLayout.addAditionaisFields() {
  }

  override fun Grid<Produtos>.colunasGrid() {
    this.setSelectionMode(Grid.SelectionMode.MULTI)
    addColumnSeq("Seq")
    produto_codigo()
    produto_descricao()
    produto_grade()
    produto_estoque()
    produto_forn()
    produto_abrev()
    produto_tipo()
    produto_cl()
    produto_codBar()

    produto_DS_TT()
    produto_MR_TT()
    produto_MF_TT()
    produto_PK_TT()
    produto_TM_TT()
  }
}