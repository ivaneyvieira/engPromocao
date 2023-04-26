package br.com.astrosoft.promocao.view.garantia.columns

import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
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

    fun Grid<ComparaValidade>.garantiaVendno() = addColumnInt(ComparaValidade::vendno) {
        this.setHeader("Cod For")
    }

    fun Grid<ComparaValidade>.garantiaTypeno() = addColumnInt(ComparaValidade::typeno) {
        this.setHeader("Tipo")
    }

    fun Grid<ComparaValidade>.garantiaClno() = addColumnInt(ComparaValidade::clno) {
        this.setHeader("CL")
    }

    fun Grid<ComparaValidade>.garantiaLocalizacao() = addColumnString(ComparaValidade::localizacao) {
        this.setHeader("Loc")
    }

    fun Grid<ComparaValidade>.garantiaUltEnt() = addColumnLocalDate(ComparaValidade::ultimaEntrada) {
        this.setHeader("Data")
        isExpand = false
    }

    fun Grid<ComparaValidade>.garantiaUltQuant() = addColumnInt(ComparaValidade::qtty) {
        this.setHeader("Quant")
        isExpand = false
    }

    fun Grid<ComparaValidade>.garantiaEstoque() = addColumnInt(ComparaValidade::estoque) {
        this.setHeader("Estoque")
        isExpand = false
    }

    fun Grid<ComparaValidade>.garantiaEstoqueMF() = addColumnInt(ComparaValidade::estoqueMF) {
        this.setHeader("Estoque MF")
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

    fun Grid<ComparaValidade>.garantiaValidade() = addColumnInt(ComparaValidade::validade_cadastro) {
        this.setHeader("Validade")
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