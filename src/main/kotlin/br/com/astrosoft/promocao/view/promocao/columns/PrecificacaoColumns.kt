package br.com.astrosoft.promocao.view.promocao.columns

import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.promocao.model.beans.Precificacao
import br.com.astrosoft.promocao.model.beans.PrecoPromocao
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
  fun Grid<Precificacao>.promocaoMva() = addColumnDouble(Precificacao::mvap) {
    this.setHeader("MVA")
  }
  fun Grid<Precificacao>.promocaoIcmsEnt() = addColumnDouble(Precificacao::icmsp) {
    this.setHeader("ICMS Ent")
  }
  fun Grid<Precificacao>.promocaoTributacao() = addColumnString(Precificacao::tributacao) {
    this.setHeader("Trib")
  }
}