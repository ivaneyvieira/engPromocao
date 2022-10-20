package br.com.astrosoft.promocao.view.produtos.columns

import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.promocao.model.beans.Produtos
import com.vaadin.flow.component.grid.Grid

object ProdutosColumns {
  fun Grid<Produtos>.produto_codigo() = addColumnString(Produtos::codigo) {
    this.setHeader("Cód")
  }
  fun Grid<Produtos>.produto_descricao() = addColumnString(Produtos::descricao) {
    this.setHeader("Descrição")
  }
  fun Grid<Produtos>.produto_grade() = addColumnString(Produtos::grade) {
    this.setHeader("Grade")
  }
  fun Grid<Produtos>.produto_forn() = addColumnInt(Produtos::forn) {
    this.setHeader("Forn")
  }
  fun Grid<Produtos>.produto_abrev() = addColumnString(Produtos::abrev) {
    this.setHeader("Abrev")
  }
  fun Grid<Produtos>.produto_tipo() = addColumnInt(Produtos::tipo) {
    this.setHeader("Tipo")
  }
  fun Grid<Produtos>.produto_cl() = addColumnInt(Produtos::cl) {
    this.setHeader("CL")
  }
  fun Grid<Produtos>.produto_codBar() = addColumnString(Produtos::codBar) {
    this.setHeader("Cód. Barras")
  }
  fun Grid<Produtos>.produto_estJs() = addColumnDouble(Produtos::estJs) {
    this.setHeader("Est. JS")
  }
  fun Grid<Produtos>.produto_estDs() = addColumnDouble(Produtos::estDs) {
    this.setHeader("Est. DS")
  }
  fun Grid<Produtos>.produto_estMr() = addColumnDouble(Produtos::estMr) {
    this.setHeader("Est. MR")
  }
  fun Grid<Produtos>.produto_estMf() = addColumnDouble(Produtos::estMf) {
    this.setHeader("Est. MF")
  }
  fun Grid<Produtos>.produto_estPk() = addColumnDouble(Produtos::estPk) {
    this.setHeader("Est. PK")
  }
  fun Grid<Produtos>.produto_estTm() = addColumnDouble(Produtos::estTm) {
    this.setHeader("Est. TM")
  }
  fun Grid<Produtos>.produto_custo() = addColumnDouble(Produtos::custo) {
    this.setHeader("P. Custo")
  }
  fun Grid<Produtos>.produto_pPromo() = addColumnDouble(Produtos::pPromo) {
    this.setHeader("P. Promo")
  }
  fun Grid<Produtos>.produto_pRef() = addColumnDouble(Produtos::pRef) {
    this.setHeader("Cod")
  }
}