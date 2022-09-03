package br.com.astrosoft.promocao.view.garantia.columns

import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.promocao.model.beans.ComparaValidade
import com.github.mvysny.karibudsl.v10.isExpand
import com.vaadin.flow.component.grid.Grid

object GarantiaDiferenca {
  fun Grid<ComparaValidade>.garantiaCodigo() = addColumnInt(ComparaValidade::codigo) {
    this.setHeader("Código")
    isExpand = false
  }

  fun Grid<ComparaValidade>.garantiaDescicao() = addColumnString(ComparaValidade::descricao) {
    this.setHeader("Descrição")
  }

  fun Grid<ComparaValidade>.garantiaGrade() = addColumnString(ComparaValidade::grade) {
    this.setHeader("Grade")
  }

  fun Grid<ComparaValidade>.garantiaLocalizacao() = addColumnString(ComparaValidade::localizacao) {
    this.setHeader("Loc")
  }

  fun Grid<ComparaValidade>.garantiaEstoque() = addColumnInt(ComparaValidade::estoque) {
    this.setHeader("Estoque")
    isExpand = false
  }

  fun Grid<ComparaValidade>.garantiaValidadeDesc() = addColumnInt(ComparaValidade::validade_descricao) {
    this.setHeader("Validade Desc.")
    isExpand = false
  }

  fun Grid<ComparaValidade>.garantiaValidadeCad() = addColumnInt(ComparaValidade::validade_cadastro) {
    this.setHeader("Validade Cad.")
    isExpand = false
  }

  fun Grid<ComparaValidade>.garantiaDiferenca() = addColumnInt(ComparaValidade::diferenca) {
    this.setHeader("Diferença")
    isExpand = false
  }

  fun Grid<ComparaValidade>.garantiaTipoDiferenca() = addColumnString(ComparaValidade::tipoString) {
    this.setHeader("Tipo")
  }
}