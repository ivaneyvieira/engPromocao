package br.com.astrosoft.promocao.view.garantia.columns

import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.promocao.model.beans.ProdutoValidade
import com.github.mvysny.karibudsl.v10.isExpand
import com.vaadin.flow.component.grid.Grid

object ProdutoValidadeCol {
  fun Grid<ProdutoValidade>.produtoCodigo() = addColumnString(ProdutoValidade::codigo) {
    this.setHeader("Código")
    isExpand = false
    isResizable = true
  }

  fun Grid<ProdutoValidade>.produtoDescricao() = addColumnString(ProdutoValidade::descricao) {
    this.setHeader("Descrição")
    isResizable = true
  }

  fun Grid<ProdutoValidade>.produtoEstoque() = addColumnInt(ProdutoValidade::estoque) {
    this.setHeader("Estoque")
    isResizable = true
  }

  fun Grid<ProdutoValidade>.produtoClno() = addColumnInt(ProdutoValidade::clno) {
    this.setHeader("Cl")
    isExpand = false
    isResizable = true
  }

  fun Grid<ProdutoValidade>.produtoCentroLucro() = addColumnString(ProdutoValidade::centroLucro) {
    this.setHeader("Centro de Lucro")
    isExpand = false
    isResizable = true
  }

  fun Grid<ProdutoValidade>.produtoVendno() = addColumnInt(ProdutoValidade::vendno) {
    this.setHeader("Cod. Forn")
    isExpand = false
    isResizable = true
  }

  fun Grid<ProdutoValidade>.produtoFornecedor() = addColumnString(ProdutoValidade::fornecedor) {
    this.setHeader("Fonecedor")
    isExpand = false
    isResizable = true
  }

  fun Grid<ProdutoValidade>.produtoTipoNo() = addColumnInt(ProdutoValidade::typeno) {
    this.setHeader("Cod. Tipo")
    isExpand = false
    isResizable = true
  }

  fun Grid<ProdutoValidade>.produtoTipoProduto() = addColumnString(ProdutoValidade::tipoProduto) {
    this.setHeader("Tipo")
    isExpand = false
    isResizable = true
  }

  fun Grid<ProdutoValidade>.produtoTipoValidade() = addColumnString(ProdutoValidade::tipoValidade) {
    this.setHeader("Tempo")
    isExpand = false
    isResizable = true
  }

  fun Grid<ProdutoValidade>.produtoMeseValidade() = addColumnInt(ProdutoValidade::mesesValidade) {
    this.setHeader("Validade")
    isExpand = false
    isResizable = true
  }

  fun Grid<ProdutoValidade>.produtoDescricaoCompleta1() = addColumnString(ProdutoValidade::descricaoCompleta1) {
    this.setHeader("Descricao Completa Linha 1")
    isExpand = false
    isResizable = true
  }

  fun Grid<ProdutoValidade>.produtoDescricaoCompleta2() = addColumnString(ProdutoValidade::descricaoCompleta2) {
    this.setHeader("Descricao Completa Linha 2")
    isExpand = false
    isResizable = true
  }
}