package br.com.astrosoft.promocao.view.preco.columns

import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.promocao.model.beans.PrecoAlteracao
import com.vaadin.flow.component.grid.Grid

object NotaNddViewColumns {
  fun Grid<PrecoAlteracao>.precoCodigo() = addColumnString(PrecoAlteracao::codigo) {
    this.setHeader("Cod")
  }

  fun Grid<PrecoAlteracao>.precoDescricao() = addColumnString(PrecoAlteracao::descricao) {
    this.setHeader("Descrição")
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

  fun Grid<PrecoAlteracao>.precoAlteracao() = addColumnLocalDate(PrecoAlteracao::alteracao) {
    this.setHeader("Alteração")
  }

  fun Grid<PrecoAlteracao>.precoValidade() = addColumnLocalDate(PrecoAlteracao::validade) {
    this.setHeader("Validade")
  }

  fun Grid<PrecoAlteracao>.precoPrecoPromocional() = addColumnDouble(PrecoAlteracao::precoPromocional) {
    this.setHeader("R$ Promo")
  }

  fun Grid<PrecoAlteracao>.precoDesconto() = addColumnDouble(PrecoAlteracao::desconto) {
    this.setHeader("% Perc")
  }

  fun Grid<PrecoAlteracao>.precoPrecoRef() = addColumnDouble(PrecoAlteracao::refPrice) {
    this.setHeader("R$ Ref.")
  }

  fun Grid<PrecoAlteracao>.precoPrecoAnterior() = addColumnDouble(PrecoAlteracao::precoOld) {
    this.setHeader("R$ Anterior")
  }

  fun Grid<PrecoAlteracao>.precoPrecoAtual() = addColumnDouble(PrecoAlteracao::precoNew) {
    this.setHeader("R$ Atual")
  }
}