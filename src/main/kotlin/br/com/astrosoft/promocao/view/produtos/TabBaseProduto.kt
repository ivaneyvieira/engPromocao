package br.com.astrosoft.promocao.view.produtos

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.promocao.model.beans.Produtos
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_abrev
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_cl
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_codBar
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_codigo
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_custo
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_descricao
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_estDs
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_estJs
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_estMf
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_estMr
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_estPk
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_estTm
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_forn
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_grade
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_pPromo
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_pRef
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_tipo
import br.com.astrosoft.promocao.viewmodel.produto.ITabBaseProdutoViewModel
import br.com.astrosoft.promocao.viewmodel.produto.TabBaseProdutoViewModel
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class TabBaseProduto(viewModel: TabBaseProdutoViewModel) :
        TabAbstractProduto<ITabBaseProdutoViewModel>(viewModel, showDatas = false), ITabBaseProdutoViewModel {
  override fun isAuthorized(user: IUser) = (user as? UserSaci)?.produtoBase ?: false

  override val label: String
    get() = "Base"

  override fun HorizontalLayout.addAditionaisFields() {
  }

  override fun Grid<Produtos>.colunasGrid() {
    produto_codigo()
    produto_descricao()
    produto_grade()
    produto_forn()
    produto_abrev()
    produto_tipo()
    produto_cl()
    produto_codBar()
    produto_estJs()
    produto_estDs()
    produto_estMr()
    produto_estMf()
    produto_estPk()
    produto_estTm()
    produto_custo()
    produto_pPromo()
    produto_pRef()
  }
}