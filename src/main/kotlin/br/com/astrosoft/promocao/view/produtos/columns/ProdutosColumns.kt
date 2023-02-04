package br.com.astrosoft.promocao.view.produtos.columns

import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.promocao.model.beans.Produtos
import br.com.astrosoft.promocao.view.produtos.columns.ProdutosColumns.config
import com.github.mvysny.karibudsl.v10.isExpand
import com.vaadin.flow.component.grid.Grid

object ProdutosColumns {
  fun Grid<Produtos>.produto_codigo() = addColumnInt(Produtos::codigo) {
    this.setHeader("Cód")
    this.config()
  }

  fun Grid<Produtos>.produto_descricao() = addColumnString(Produtos::descricao) {
    this.setHeader("Descrição")
    this.isResizable = true
  }

  fun Grid<Produtos>.produto_grade() = addColumnString(Produtos::grade) {
    this.setHeader("Grade")
    this.config()
  }

  fun Grid<Produtos>.produto_forn() = addColumnInt(Produtos::forn) {
    this.setHeader("Forn")
    this.config()
  }

  fun Grid<Produtos>.produto_abrev() = addColumnString(Produtos::abrev) {
    this.setHeader("Abrev")
    this.config()
  }

  fun Grid<Produtos>.produto_tipo() = addColumnInt(Produtos::tipo) {
    this.setHeader("Tipo")
    this.config()
  }

  fun Grid<Produtos>.produto_cl() = addColumnInt(Produtos::cl) {
    this.setHeader("CL")
    this.config()
  }

  fun Grid<Produtos>.produto_codBar() = addColumnString(Produtos::codBar) {
    this.setHeader("Cód. Barras")
    this.config()
  }

  fun Grid<Produtos>.produto_DS_VA() = addColumnInt(Produtos::DS_VA) {
    this.setHeader("DS VA")
    this.config()
  }

  fun Grid<Produtos>.produto_DS_AT() = addColumnInt(Produtos::DS_AT) {
    this.setHeader("DS AT")
    this.config()
  }

  fun Grid<Produtos>.produto_DS_TT() = addColumnInt(Produtos::DS_TT) {
    this.setHeader("DS TT")
    this.config()
  }

  fun Grid<Produtos>.produto_MR_VA() = addColumnInt(Produtos::MR_VA) {
    this.setHeader("MR VA")
    this.config()
  }

  fun Grid<Produtos>.produto_MR_AT() = addColumnInt(Produtos::MR_AT) {
    this.setHeader("MR AT")
    this.config()
  }

  fun Grid<Produtos>.produto_MR_TT() = addColumnInt(Produtos::MR_TT) {
    this.setHeader("MR TT")
    this.config()
  }

  fun Grid<Produtos>.produto_MF_VA() = addColumnInt(Produtos::MF_VA) {
    this.setHeader("MF VA")
    this.config()
  }

  fun Grid<Produtos>.produto_MF_AT() = addColumnInt(Produtos::MF_AT) {
    this.setHeader("MF AT")
    this.config()
  }

  fun Grid<Produtos>.produto_MF_TT() = addColumnInt(Produtos::MF_TT) {
    this.setHeader("MF TT")
    this.config()
  }

  fun Grid<Produtos>.produto_PK_VA() = addColumnInt(Produtos::PK_VA) {
    this.setHeader("PK VA")
    this.config()
  }

  fun Grid<Produtos>.produto_PK_AT() = addColumnInt(Produtos::PK_AT) {
    this.setHeader("PK AT")
    this.config()
  }

  fun Grid<Produtos>.produto_PK_TT() = addColumnInt(Produtos::PK_TT) {
    this.setHeader("PK TT")
    this.config()
  }

  fun Grid<Produtos>.produto_TM_VA() = addColumnInt(Produtos::TM_VA) {
    this.setHeader("TM VA")
    this.config()
  }

  fun Grid<Produtos>.produto_TM_AT() = addColumnInt(Produtos::TM_AT) {
    this.setHeader("TM AT")
    this.config()
  }

  fun Grid<Produtos>.produto_TM_TT() = addColumnInt(Produtos::TM_TT) {
    this.setHeader("TM TT")
    this.config()
  }

  fun Grid<Produtos>.produto_estoque() = addColumnInt(Produtos::estoque) {
    this.setHeader("Total")
    this.config()
  }

  fun Grid<Produtos>.produto_Trib() = addColumnString(Produtos::trib) {
    this.setHeader("Trib")
    this.config()
  }

  fun Grid<Produtos>.produto_RefForn() = addColumnString(Produtos::refForn) {
    this.setHeader("Ref Forn")
    this.config()
  }

  fun Grid<Produtos>.produto_PesoBruto() = addColumnDouble(Produtos::pesoBruto) {
    this.setHeader("P. Bruto")
    this.config()
  }

  fun Grid<Produtos>.produto_UGar() = addColumnString(Produtos::uGar) {
    this.setHeader("U. Gar")
    this.config()
  }

  fun Grid<Produtos>.produto_TGar() = addColumnInt(Produtos::tGar) {
    this.setHeader("T Gar")
    this.config()
  }

  fun Grid<Produtos>.produto_Emb() = addColumnDouble(Produtos::emb) {
    this.setHeader("Emb")
    this.config()
  }

  fun Grid<Produtos>.produto_Ncm() = addColumnString(Produtos::ncm) {
    this.setHeader("NCM")
    this.config()
  }

  fun Grid<Produtos>.produto_Site() = addColumnString(Produtos::site) {
    this.setHeader("Site")
    this.config()
  }

  fun Grid<Produtos>.produto_Unidade() = addColumnString(Produtos::unidade) {
    this.setHeader("UN")
    this.config()
  }

  private fun Grid.Column<Produtos>.config() {
    this.isExpand = false
    this.isAutoWidth = true
    this.isResizable = true
  }
}