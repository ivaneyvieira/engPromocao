package br.com.astrosoft.promocao.view.garantia.columns

import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.promocao.model.beans.GarantiaEstoqueApp
import com.github.mvysny.karibudsl.v10.isExpand
import com.vaadin.flow.component.grid.Grid
import java.time.LocalDate

object EstoqueAppCol {
    fun Grid<GarantiaEstoqueApp>.estoqueAppCodigo() = addColumnInt(GarantiaEstoqueApp::codigo) {
        this.setHeader("Código")
        isExpand = false
        isResizable = true
    }

    fun Grid<GarantiaEstoqueApp>.estoqueAppDescricao() = addColumnString(GarantiaEstoqueApp::descricao) {
        this.setHeader("Descrição")
        isResizable = true
    }

    fun Grid<GarantiaEstoqueApp>.estoqueAppGrade() = addColumnString(GarantiaEstoqueApp::grade) {
        this.setHeader("Grade")
        isResizable = true
    }

    fun Grid<GarantiaEstoqueApp>.estoqueAppLocalizacao() = addColumnString(GarantiaEstoqueApp::localizacao) {
        this.setHeader("Localização")
        isResizable = true
    }

    fun Grid<GarantiaEstoqueApp>.estoqueEstoqueNerus() = addColumnInt(GarantiaEstoqueApp::estoqueNerus) {
        this.setHeader("Est Nerus")
        isResizable = true
    }

    fun Grid<GarantiaEstoqueApp>.estoqueEstoqueApp() = addColumnInt(GarantiaEstoqueApp::estoqueApp) {
        this.setHeader("Est App")
        isResizable = true
    }

    fun Grid<GarantiaEstoqueApp>.estoqueSaldo() = addColumnInt(GarantiaEstoqueApp::saldo) {
        this.setHeader("Saldo")
        isResizable = true
    }

    fun Grid<GarantiaEstoqueApp>.estoqueEntrada() = addColumnInt(GarantiaEstoqueApp::entrada) {
        this.setHeader("Entrada")
        isResizable = true
    }

    fun Grid<GarantiaEstoqueApp>.estoqueNotaEntrada() = addColumnString(GarantiaEstoqueApp::nfEntrada) {
        this.setHeader("NF Entrada")
        isResizable = true
    }

    fun Grid<GarantiaEstoqueApp>.estoqueDataEntrada() = addColumnLocalDate(GarantiaEstoqueApp::dataEntrada) {
        this.setHeader("Data Entrada")
        isResizable = true
    }
}