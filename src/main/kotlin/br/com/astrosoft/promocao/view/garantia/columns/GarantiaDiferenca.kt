package br.com.astrosoft.promocao.view.garantia.columns

import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.promocao.model.beans.ComparaValidade
import com.vaadin.flow.component.grid.Grid

object GarantiaDiferenca {
  fun Grid<ComparaValidade>.garantiaCodigo() = addColumnString(ComparaValidade::codigo) {
    this.setHeader("Código")
  }

  fun Grid<ComparaValidade>.garantiaDescicao() = addColumnString(ComparaValidade::descricao) {
    this.setHeader("Descrição")
  }

  fun Grid<ComparaValidade>.garantiaValidadeDesc() = addColumnInt(ComparaValidade::validade_descricao) {
    this.setHeader("Validade Desc.")
  }

  fun Grid<ComparaValidade>.garantiaValidadeCad() = addColumnInt(ComparaValidade::validade_cadastro) {
    this.setHeader("Validade Cad.")
  }

  fun Grid<ComparaValidade>.garantiaDiferenca() = addColumnInt(ComparaValidade::diferenca) {
    this.setHeader("Diferença")
  }

  fun Grid<ComparaValidade>.garantiaTipoDiferenca() = addColumnString(ComparaValidade::tipoString) {
    this.setHeader("Tipo")
  }
}