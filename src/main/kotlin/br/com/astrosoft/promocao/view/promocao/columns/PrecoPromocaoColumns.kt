package br.com.astrosoft.promocao.view.promocao.columns

import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.promocao.model.beans.PrecoPromocao
import com.vaadin.flow.component.grid.Grid

object NotaNddViewColumns {
  fun Grid<PrecoPromocao>.promocaoCodigo() = addColumnString(PrecoPromocao::codigo) {
    this.setHeader("Código")
  }

  fun Grid<PrecoPromocao>.promocaoDescricao() = addColumnString(PrecoPromocao::descricao) {
    this.setHeader("Descrição")
  }

  fun Grid<PrecoPromocao>.promocaoFornecedor() = addColumnString(PrecoPromocao::fornecedor) {
    this.setHeader("Fornecedor")
  }

  fun Grid<PrecoPromocao>.promocaoCentroLucro() = addColumnString(PrecoPromocao::centroLucro) {
    this.setHeader("Centro de Lucro")
  }

  fun Grid<PrecoPromocao>.promocaoTipoProduto() = addColumnString(PrecoPromocao::tipoProduto) {
    this.setHeader("Tipo")
  }

  fun Grid<PrecoPromocao>.promocaoPrecoVista() = addColumnDouble(PrecoPromocao::preco) {
    this.setHeader("Preço a Vista")
  }

  fun Grid<PrecoPromocao>.promocaoValidade() = addColumnLocalDate(PrecoPromocao::validade) {
    this.setHeader("Validade")
  }

  fun Grid<PrecoPromocao>.promocaoPrecificacao() = addColumnDouble(PrecoPromocao::precoPromocional) {
    this.setHeader("Preço Promocional")
  }

  fun Grid<PrecoPromocao>.promocaoPrecoRef() = addColumnDouble(PrecoPromocao::refPrice) {
    this.setHeader("Preço Ref")
  }

  fun Grid<PrecoPromocao>.promocaoPrecoPromo() = addColumnDouble(PrecoPromocao::promoPrice) {
    this.setHeader("Preço Promocional")
  }

  fun Grid<PrecoPromocao>.promocaoOrigem() = addColumnString(PrecoPromocao::origemPromocao) {
    this.setHeader("Origem")
  }

  fun Grid<PrecoPromocao>.promocaoNumPromocao() = addColumnInt(PrecoPromocao::promono) {
    this.setHeader("Num Promoção")
  }

  fun Grid<PrecoPromocao>.promocaoPromoDescricao() = addColumnString(PrecoPromocao::descricaoPromocao) {
    this.setHeader("Promoção")
  }

  fun Grid<PrecoPromocao>.promocaoTipoPromo() = addColumnString(PrecoPromocao::tipoPromocao) {
    this.setHeader("Tipo Promoção")
  }

  fun Grid<PrecoPromocao>.promocaoDesconto() = addColumnDouble(PrecoPromocao::percentual) {
    this.setHeader("% Desconto")
  }
}