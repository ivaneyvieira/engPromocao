package br.com.astrosoft.promocao.view.promocao.columns

import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.promocao.model.beans.Precificacao
import com.vaadin.flow.component.grid.Grid

object PrecificacaoColumns {
  fun Grid<Precificacao>.promocaoCodigo() = addColumnString(Precificacao::codigo) {
    this.setHeader("Cod")
  }

  fun Grid<Precificacao>.promocaoDescricao() = addColumnString(Precificacao::descricao) {
    this.setHeader("Descricao")
  }

  fun Grid<Precificacao>.promocaoVendno() = addColumnInt(Precificacao::vendno) {
    this.setHeader("Cod For")
  }

  fun Grid<Precificacao>.promocaoTypeno() = addColumnInt(Precificacao::typeno) {
    this.setHeader("Tipo")
  }

  fun Grid<Precificacao>.promocaoClno() = addColumnInt(Precificacao::clno) {
    this.setHeader("CL")
  }

  fun Grid<Precificacao>.promocaoFornecedor() = addColumnString(Precificacao::fornecedor) {
    this.setHeader("Fornecedor")
  }

  fun Grid<Precificacao>.promocaoCpmf() = addColumnDouble(Precificacao::cpmf) {
    this.setHeader("CPMF")
  }

  fun Grid<Precificacao>.promocaoFCP() = addColumnDouble(Precificacao::fcp) {
    this.setHeader("FCP")
  }

  fun Grid<Precificacao>.promocaoMva() = addColumnDouble(Precificacao::mvap) {
    this.setHeader("MVA")
  }

  fun Grid<Precificacao>.promocaoIcmsEnt() = addColumnDouble(Precificacao::icmsp) {
    this.setHeader("ICMS Ent")
  }

  fun Grid<Precificacao>.promocaoPFabrica() = addColumnDouble(Precificacao::pcfabrica, pattern = "#,##0.0000") {
    this.setHeader("P. Fab")
  }

  fun Grid<Precificacao>.promocaoIpi() = addColumnDouble(Precificacao::ipi) {
    this.setHeader("IPI")
  }

  fun Grid<Precificacao>.promocaoEmbalagem() = addColumnDouble(Precificacao::embalagem) {
    this.setHeader("Emb")
  }

  fun Grid<Precificacao>.promocaoRetido() = addColumnDouble(Precificacao::retido) {
    this.setHeader("IR ST")
  }

  fun Grid<Precificacao>.promocaoIcms() = addColumnDouble(Precificacao::creditoICMS) {
    this.setHeader("C. ICMS")
  }

  fun Grid<Precificacao>.promocaoFrete() = addColumnDouble(Precificacao::frete) {
    this.setHeader("Frete")
  }

  fun Grid<Precificacao>.promocaoContabil() = addColumnDouble(Precificacao::custoContabil, pattern = "#,##0.0000") {
    this.setHeader("C.Cont")
  }

  fun Grid<Precificacao>.promocaoTributacao() = addColumnString(Precificacao::tributacao) {
    this.setHeader("Trib")
  }
}