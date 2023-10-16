package br.com.astrosoft.promocao.view.produtos

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.addColumnSeq
import br.com.astrosoft.promocao.model.beans.EEstoqueTotal
import br.com.astrosoft.promocao.model.beans.Produtos
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.model.planilhas.PlanilhaProduto
import br.com.astrosoft.promocao.model.planilhas.PlanilhaProdutoEstoqueApp
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_MF_App
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_MF_Dif
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_Unidade
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_abrev
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_cl
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_codBar
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_codigo
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_descricao
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_estoque
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_forn
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_grade
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_quantCompra
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_quantVenda
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_tipo
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.produto_tributacao
import br.com.astrosoft.promocao.viewmodel.produto.ITabEstoqueAppViewModel
import br.com.astrosoft.promocao.viewmodel.produto.TabEstoqueAppViewModel
import com.github.mvysny.karibudsl.v10.select
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.select.Select

class TabEstoqueAppProduto(viewModel: TabEstoqueAppViewModel) :
    TabAbstractProduto<ITabEstoqueAppViewModel>(viewModel, showDatas = false), ITabEstoqueAppViewModel {
    private lateinit var cmbEstoque: Select<EEstoqueTotal>
    override fun isAuthorized(user: IUser) = (user as? UserSaci)?.produtoApp ?: false
    //Falta configurar as prermissoes

    override val label: String
        get() = "Estoque App"

    override fun planilha(): PlanilhaProduto {
        return PlanilhaProdutoEstoqueApp()
    }

    override fun HorizontalLayout.addAditionaisFields() {
        cmbEstoque = select("Estoque Total") {
            setItems(EEstoqueTotal.values().toList())
            value = EEstoqueTotal.TODOS
            this.setItemLabelGenerator {
                it.descricao
            }
            addValueChangeListener {
                viewModel.updateView()
            }
        }
    }

    override fun Grid<Produtos>.colunasGrid() {
        this.setSelectionMode(Grid.SelectionMode.MULTI)
        addColumnSeq("Seq")
        produto_codigo()
        produto_descricao()
        produto_grade()
        produto_Unidade()
        produto_quantCompra()
        produto_quantVenda()
        produto_estoque()
        produto_MF_App()
        produto_MF_Dif()
        produto_forn()
        produto_abrev()
        produto_tributacao()
        produto_tipo()
        produto_cl()
        produto_codBar()
    }

    override fun estoqueTotal(): EEstoqueTotal {
        return cmbEstoque.value ?: EEstoqueTotal.TODOS
    }

    override fun filtro() = super.filtro().copy(loja = 4)
    override fun lojaEstoque() = 0
}