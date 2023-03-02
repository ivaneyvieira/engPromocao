package br.com.astrosoft.promocao.view.produtos

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.addColumnSeq
import br.com.astrosoft.promocao.model.beans.EEstoqueTotal
import br.com.astrosoft.promocao.model.beans.Produtos
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.model.planilhas.PlanilhaProduto
import br.com.astrosoft.promocao.model.planilhas.PlanilhaProdutoCadastro
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_Emb
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_FLinha
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_Ncm
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_PesoBruto
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_RefForn
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_Site
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_TGar
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_Trib
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_UGar
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_Unidade
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_abrev
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_cl
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_codBar
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_codigo
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_descricao
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_forn
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_grade
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_tipo
import br.com.astrosoft.promocao.viewmodel.produto.ITabCadastroProdutoViewModel
import br.com.astrosoft.promocao.viewmodel.produto.TabCadastroProdutoViewModel
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class TabCadastroProduto(viewModel: TabCadastroProdutoViewModel) :
        TabAbstractProduto<ITabCadastroProdutoViewModel>(viewModel, showDatas = false), ITabCadastroProdutoViewModel {
  override fun isAuthorized(user: IUser) = (user as? UserSaci)?.produtoCadastro ?: false

  override val label: String
    get() = "Cadastro"

  override fun planilha(): PlanilhaProduto {
    return PlanilhaProdutoCadastro()
  }

  override fun HorizontalLayout.addAditionaisFields() {
  }

  override fun estoqueTotal(): EEstoqueTotal {
    return EEstoqueTotal.TODOS
  }

  override fun Grid<Produtos>.colunasGrid() {
    this.setSelectionMode(Grid.SelectionMode.MULTI)
    addColumnSeq("Seq")
    produto_codigo()
    produto_descricao()
    produto_grade()
    produto_Unidade()
    produto_FLinha()
    produto_Trib()
    produto_forn()
    produto_abrev()
    produto_tipo()
    produto_cl()
    produto_codBar()

    produto_RefForn()
    produto_PesoBruto()
    produto_UGar()
    produto_TGar()
    produto_Emb()
    produto_Ncm()
    produto_Site()
  }
}