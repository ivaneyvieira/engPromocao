package br.com.astrosoft.promocao.view.preco.columns

import br.com.astrosoft.framework.view.*
import br.com.astrosoft.promocao.model.beans.PrecoAlteracao
import com.vaadin.flow.component.grid.Grid

object NotaNddViewColumns {
  fun Grid<PrecoAlteracao>.precoCodigo() = addColumnString(PrecoAlteracao::codigo) {
    this.setHeader("Cod")
  }

  fun Grid<PrecoAlteracao>.precoDescricao() = addColumnString(PrecoAlteracao::descricao) {
    this.setHeader("Descrição")
  }

  fun Grid<PrecoAlteracao>.precoUsuario() = addColumnString(PrecoAlteracao::usuario) {
    this.setHeader("Usuário")
  }

  fun Grid<PrecoAlteracao>.precoVendno() = addColumnInt(PrecoAlteracao::vendno) {
    this.setHeader("Forn")
  }

  fun Grid<PrecoAlteracao>.precoFornecedor() = addColumnString(PrecoAlteracao::fornecedor) {
    this.setHeader("Abrev")
  }

  fun Grid<PrecoAlteracao>.precoCentroLucro() = addColumnInt(PrecoAlteracao::clno) {
    this.setHeader("Cent Lucro")
  }

  fun Grid<PrecoAlteracao>.precoTipoProduto() = addColumnInt(PrecoAlteracao::typeno) {
    this.setHeader("Tipo")
  }

  fun Grid<PrecoAlteracao>.precoDataAlteracao() = addColumnLocalDate(PrecoAlteracao::data) {
    this.setHeader("Data")
  }

  fun Grid<PrecoAlteracao>.precoHoraAlteracao() = addColumnLocalTime(PrecoAlteracao::time) {
    this.setHeader("Hora")
  }

  fun Grid<PrecoAlteracao>.precoValidadePromociaonalAtual() = addColumnLocalDate(PrecoAlteracao::validade) {
    this.setHeader("Validade")
  }

  fun Grid<PrecoAlteracao>.precoPrecoPromocionalAtual() = addColumnDouble(PrecoAlteracao::precoPromocional) {
    this.setHeader("R$ Promo")
  }

  fun Grid<PrecoAlteracao>.precoDesconto() = addColumnDouble(PrecoAlteracao::desconto) {
    this.setHeader("% Perc")
  }

  fun Grid<PrecoAlteracao>.precoPrecoRefHist() = addColumnDouble(PrecoAlteracao::precoNew) {
    this.setHeader("R$ Ref.")
  }

  fun Grid<PrecoAlteracao>.precoPrecoRefAtual() = addColumnDouble(PrecoAlteracao::refPrice) {
    this.setHeader("R$ Ref.")
  }

  fun Grid<PrecoAlteracao>.precoPrecoPromocionalHist() = addColumnDouble(PrecoAlteracao::precoNew) {
    this.setHeader("R$ Promo")
  }

  fun Grid<PrecoAlteracao>.precoValidadePromocionalHist() = addColumnLocalDate(PrecoAlteracao::dataPromocao) {
    this.setHeader("Validade")
  }
}