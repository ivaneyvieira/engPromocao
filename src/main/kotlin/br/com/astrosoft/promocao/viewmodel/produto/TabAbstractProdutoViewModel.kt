package br.com.astrosoft.promocao.viewmodel.produto

import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.promocao.model.beans.FiltroProduto
import br.com.astrosoft.promocao.model.beans.Loja
import br.com.astrosoft.promocao.model.beans.Produtos
import br.com.astrosoft.promocao.reports.ProdutoRelatorio
import br.com.astrosoft.promocao.reports.RelatorioProduto

sealed class TabAbstractProdutoViewModel<T : ITabAbstractProdutoViewModel>(val viewModel: ProdutoViewModel) {
  abstract val subView: T

  fun updateView() {
    val filtro = subView.filtro()
    val resultList = findPrecoAlteracao(filtro)
    subView.updateGrid(resultList)
  }

  abstract fun findPrecoAlteracao(filtro: FiltroProduto): List<Produtos>

  abstract fun todoEstoque(): Boolean
  fun allLojas() = Loja.allLojas()
  fun geraRelatorio() {
    val produtos = subView.produtosSelecionados()
    val filtro = subView.filtro()
    val report = RelatorioProduto(filtro.lojaEstoque)
    val listaProduto = produtos.map {
      ProdutoRelatorio(
        codigo = it.codigo,
        descricao = it.descricao,
        grade = it.grade,
        unidade = it.unidade,
        quant = when (filtro.lojaEstoque) {
          0 -> it.estoque
          2 -> it.DS_TT
          3 -> it.MR_TT
          4 -> it.MF_TT
          5 -> it.PK_TT
          8 -> it.TM_TT
          else -> 0
        }
      )
    }
    val file = report.processaRelatorio(listaProduto)
    viewModel.view.showReport(chave = "NotaImpresso${System.nanoTime()}", report = file)
  }
}

interface ITabAbstractProdutoViewModel : ITabView {
  fun filtro(): FiltroProduto
  fun updateGrid(itens: List<Produtos>)
  fun produtosSelecionados(): List<Produtos>
}