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

  fun Grid<Precificacao>.promocaoIcmsSai() = addColumnDouble(Precificacao::icms) {
    this.setHeader("ICM Sai")
  }

  fun Grid<Precificacao>.promocaoPis() = addColumnDouble(Precificacao::pis) {
    this.setHeader("Pis")
  }

  fun Grid<Precificacao>.promocaoIR() = addColumnDouble(Precificacao::ir) {
    this.setHeader("IR")
  }

  fun Grid<Precificacao>.promocaoCS() = addColumnDouble(Precificacao::contrib) {
    this.setHeader("CS")
  }

  fun Grid<Precificacao>.promocaoDesp() = addColumnDouble(Precificacao::fixa) {
    this.setHeader("Desp")
  }

  fun Grid<Precificacao>.promocaoOut() = addColumnDouble(Precificacao::outras) {
    this.setHeader("Out")
  }

  fun Grid<Precificacao>.promocaoLucro() = addColumnDouble(Precificacao::lucroLiq) {
    this.setHeader("Lucro")
  }

  fun Grid<Precificacao>.promocaoPSug() = addColumnDouble(Precificacao::precoSug) {
    this.setHeader("P.Sug.")
  }

  fun Grid<Precificacao>.promocaoPRef() = addColumnDouble(Precificacao::precoRef) {
    this.setHeader("P.Ref.")
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

  fun Grid<Precificacao>.promocaoPFabrica() = addColumnDouble(Precificacao::pcfabrica) {
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

  fun Grid<Precificacao>.promocaoContabil() = addColumnDouble(Precificacao::custoContabil) {
    this.setHeader("C.Cont")
  }

  fun Grid<Precificacao>.promocaoTributacao() = addColumnString(Precificacao::tributacao) {
    this.setHeader("Trib")
  }
}