package br.com.astrosoft.preco.view.preco.columns

import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.promocao.model.beans.PrecoPromocao
import com.vaadin.flow.component.grid.Grid

object NotaNddViewColumns {
  fun Grid<PrecoPromocao>.precoCodigo() = addColumnString(PrecoPromocao::codigo) {
    this.setHeader("Cod")
  }

  fun Grid<PrecoPromocao>.precoDescricao() = addColumnString(PrecoPromocao::descricao) {
    this.setHeader("Descrição")
  }

  fun Grid<PrecoPromocao>.precoVendno() = addColumnInt(PrecoPromocao::vendno) {
    this.setHeader("Forn")
  }

  fun Grid<PrecoPromocao>.precoFornecedor() = addColumnString(PrecoPromocao::fornecedor) {
    this.setHeader("Abrev")
  }

  fun Grid<PrecoPromocao>.precoCentroLucro() = addColumnInt(PrecoPromocao::clno) {
    this.setHeader("Cent Lucro")
  }

  fun Grid<PrecoPromocao>.precoTipoProduto() = addColumnInt(PrecoPromocao::typeno) {
    this.setHeader("Tipo")
  }

  fun Grid<PrecoPromocao>.precoValidade() = addColumnLocalDate(PrecoPromocao::validade) {
    this.setHeader("Validade")
  }

  fun Grid<PrecoPromocao>.precoPrecoPromocional() = addColumnDouble(PrecoPromocao::precoPromocional) {
    this.setHeader("R$ Promo")
  }

  fun Grid<PrecoPromocao>.precoDesconto() = addColumnDouble(PrecoPromocao::desconto) {
    this.setHeader("% Perc")
  }

  fun Grid<PrecoPromocao>.precoPrecoRef() = addColumnDouble(PrecoPromocao::refPrice) {
    this.setHeader("R$ Ref.")
  }

  fun Grid<PrecoPromocao>.precoOrigem() = addColumnString(PrecoPromocao::origemPromocao) {
    this.setHeader("Origem")
  }
}