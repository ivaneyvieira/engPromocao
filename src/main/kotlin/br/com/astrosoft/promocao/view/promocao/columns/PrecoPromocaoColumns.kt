package br.com.astrosoft.promocao.view.promocao.columns

import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.promocao.model.beans.PrecoPromocao
import com.vaadin.flow.component.grid.Grid

object NotaNddViewColumns {
  fun Grid<PrecoPromocao>.promocaoCodigo() = addColumnString(PrecoPromocao::codigo) {
    this.setHeader("Cod")
  }

  fun Grid<PrecoPromocao>.promocaoDescricao() = addColumnString(PrecoPromocao::descricao) {
    this.setHeader("Descrição")
  }

  fun Grid<PrecoPromocao>.promocaoVendno() = addColumnInt(PrecoPromocao::vendno) {
    this.setHeader("Forn")
  }

  fun Grid<PrecoPromocao>.promocaoFornecedor() = addColumnString(PrecoPromocao::fornecedor) {
    this.setHeader("Abrev")
  }

  fun Grid<PrecoPromocao>.promocaoCentroLucro() = addColumnInt(PrecoPromocao::clno) {
    this.setHeader("Cent Lucro")
  }

  fun Grid<PrecoPromocao>.promocaoTipoProduto() = addColumnInt(PrecoPromocao::typeno) {
    this.setHeader("Tipo")
  }

  fun Grid<PrecoPromocao>.promocaoValidade() = addColumnLocalDate(PrecoPromocao::validade) {
    this.setHeader("Validade")
  }

  fun Grid<PrecoPromocao>.promocaoPrecoPromocional() = addColumnDouble(PrecoPromocao::precoPromocional) {
    this.setHeader("R$ Promo")
  }

  fun Grid<PrecoPromocao>.promocaoDesconto() = addColumnDouble(PrecoPromocao::desconto) {
    this.setHeader("% Perc")
  }

  fun Grid<PrecoPromocao>.promocaoSaldo() = addColumnInt(PrecoPromocao::saldo) {
    this.setHeader("Saldo")
  }

  fun Grid<PrecoPromocao>.promocaoPrecoRef() = addColumnDouble(PrecoPromocao::refPrice) {
    this.setHeader("R$ Ref.")
  }

  fun Grid<PrecoPromocao>.promocaoOrigem() = addColumnString(PrecoPromocao::origemPromocao) {
    this.setHeader("Origem")
  }
}