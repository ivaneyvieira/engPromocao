package br.com.astrosoft.promocao.view.garantia.columns

import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.promocao.model.beans.ProdutoValidade
import com.github.mvysny.karibudsl.v10.isExpand
import com.vaadin.flow.component.grid.Grid

object ProdutoValidadeCol {
  fun Grid<ProdutoValidade>.garantiaCodigo() = addColumnString(ProdutoValidade::codigo) {
    this.setHeader("Código")
    isExpand = false
  }

  fun Grid<ProdutoValidade>.garantiaDescicao() = addColumnString(ProdutoValidade::descricao) {
    this.setHeader("Descrição")
  }

  fun Grid<ProdutoValidade>.garantiaClno() = addColumnInt(ProdutoValidade::clno) {
    this.setHeader("Cl")
    isExpand = false
  }

  fun Grid<ProdutoValidade>.garantiaCentroLucro() = addColumnString(ProdutoValidade::centroLucro) {
    this.setHeader("Centro de Lucro")
    isExpand = false
  }

  fun Grid<ProdutoValidade>.garantiaVendno() = addColumnInt(ProdutoValidade::vendno) {
    this.setHeader("Cod. Forn")
    isExpand = false
  }

  fun Grid<ProdutoValidade>.garantiaFornecedor() = addColumnString(ProdutoValidade::fornecedor) {
    this.setHeader("Fonecedor")
    isExpand = false
  }

  fun Grid<ProdutoValidade>.garantiaTipoNo() = addColumnInt(ProdutoValidade::typeno) {
    this.setHeader("Cod. Tipo")
    isExpand = false
  }

  fun Grid<ProdutoValidade>.garantiaTipoProduto() = addColumnString(ProdutoValidade::tipoProduto) {
    this.setHeader("Tipo")
    isExpand = false
  }

  fun Grid<ProdutoValidade>.garantiaTipoValidade() = addColumnString(ProdutoValidade::tipoValidade) {
    this.setHeader("Tipo Validade")
    isExpand = false
  }

  fun Grid<ProdutoValidade>.garantiaMeseValidade() = addColumnInt(ProdutoValidade::mesesValidade) {
    this.setHeader("Validade")
    isExpand = false
  }
}