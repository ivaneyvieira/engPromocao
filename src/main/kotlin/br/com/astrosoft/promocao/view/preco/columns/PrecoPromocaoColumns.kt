package br.com.astrosoft.promocao.view.preco.columns

import br.com.astrosoft.framework.view.*
import br.com.astrosoft.promocao.model.beans.PrecoAlteracao
import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.github.mvysny.karibudsl.v10.isExpand
import com.vaadin.flow.component.grid.Grid

object NotaNddViewColumns {
  fun Grid<PrecoAlteracao>.precoCodigo() = addColumnString(PrecoAlteracao::codigo) {
    this.setHeader("Cod")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoDescricao() = addColumnString(PrecoAlteracao::descricao) {
    this.setHeader("Descrição")
    this.isResizable = true
  }

  fun Grid<PrecoAlteracao>.precoUsuario() = addColumnString(PrecoAlteracao::usuario) {
    this.setHeader("Usuário")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoVendno() = addColumnInt(PrecoAlteracao::vendno) {
    this.setHeader("Forn")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoFornecedor() = addColumnString(PrecoAlteracao::fornecedor) {
    this.setHeader("Abrev")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoCentroLucro() = addColumnInt(PrecoAlteracao::clno) {
    this.setHeader("Cent Lucro")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoTipoProduto() = addColumnInt(PrecoAlteracao::typeno) {
    this.setHeader("Tipo")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoDataAlteracao() = addColumnLocalDate(PrecoAlteracao::data) {
    this.setHeader("Data")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoHoraAlteracao() = addColumnLocalTime(PrecoAlteracao::time) {
    this.setHeader("Hora")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoValidade() = addColumnLocalDate(PrecoAlteracao::dataPromocao) {
    this.setHeader("Validade")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoPrecoPromocionalAnt() = addColumnDouble(PrecoAlteracao::promo_priceAnt) {
    this.setHeader("R$ Ant.")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoPrecoPromocional() = addColumnDouble(PrecoAlteracao::promo_price) {
    this.setHeader("R$ Promo")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoPrecoPromocionalM() = addColumnDouble(PrecoAlteracao::promo_priceM) {
    this.setHeader("R$ Promo M2")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoDesconto() = addColumnDouble(PrecoAlteracao::desconto) {
    this.setHeader("% Perc")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoMetroCaixa() = addColumnDouble(PrecoAlteracao::metroCaixa) {
    this.setHeader("M Piso")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoPrecoRef() = addColumnDouble(PrecoAlteracao::refprice) {
    this.setHeader("R$ Ref.")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoPrecoRefM() = addColumnDouble(PrecoAlteracao::refpriceM) {
    this.setHeader("R$ Ref M2")
    colunaFixa(100)
  }

  fun Grid<PrecoAlteracao>.precoPrecoRefAnt() = addColumnDouble(PrecoAlteracao::refpriceAnt) {
    this.setHeader("R$ Ant.")
    colunaFixa(100)
  }

  private fun @VaadinDsl Grid.Column<PrecoAlteracao>.colunaFixa(width: Int) {
    this.isAutoWidth = false
    this.isResizable = true
    this.isExpand = false
    this.width = "${width}px"
  }
}