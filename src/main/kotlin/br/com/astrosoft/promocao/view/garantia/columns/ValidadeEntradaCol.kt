package br.com.astrosoft.promocao.view.garantia.columns

import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.promocao.model.beans.ValidadeEntrada
import com.github.mvysny.karibudsl.v10.isExpand
import com.vaadin.flow.component.grid.Grid

object ValidadeEntradaCol {
    fun Grid<ValidadeEntrada>.produtoLoja() = addColumnInt(ValidadeEntrada::loja) {
        this.setHeader("Loja")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoCodigo() = addColumnInt(ValidadeEntrada::codigo) {
        this.setHeader("Código")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoDescricao() = addColumnString(ValidadeEntrada::descricao) {
        this.setHeader("Descrição")
        isExpand = true
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoGrade() = addColumnString(ValidadeEntrada::grade) {
        this.setHeader("Grade")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoLoc() = addColumnString(ValidadeEntrada::localizacao) {
        this.setHeader("Loc")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtValidade() = addColumnInt(ValidadeEntrada::validade) {
        this.setHeader("Val")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoNFEntrada() = addColumnString(ValidadeEntrada::nfEntrada) {
        this.setHeader("NF Entrada")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoDataEntrada() =
        addColumnLocalDate(ValidadeEntrada::dataEntrada, formatPattern = "dd/MM/yy") {
            this.setHeader("Data Entrada")
            isExpand = false
            isResizable = true
        }

    fun Grid<ValidadeEntrada>.produtoFabricacao() =
        addColumnLocalDate(ValidadeEntrada::dataFabricacao, formatPattern = "MM/yy") {
            this.setHeader("Fab")
            isExpand = false
            isResizable = true
        }

    fun Grid<ValidadeEntrada>.produtoMesesFab() = addColumnInt(ValidadeEntrada::mesesFabricacao) {
        this.setHeader("M. Fab")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoVencimento() =
        addColumnLocalDate(ValidadeEntrada::vencimento, formatPattern = "MM/yy") {
            this.setHeader("Venc")
            isExpand = false
            isResizable = true
        }

    fun Grid<ValidadeEntrada>.produtoMesesVenc() = addColumnInt(ValidadeEntrada::mesesVencimento) {
        this.setHeader("M. Venc")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoEntrada() = addColumnInt(ValidadeEntrada::entrada) {
        this.setHeader("Entrada")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoSaldo() = addColumnInt(ValidadeEntrada::saldo) {
        this.setHeader("Saldo")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoSaldoDS() = addColumnInt(ValidadeEntrada::saldoDS) {
        this.setHeader("DS")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoSaldoMR() = addColumnInt(ValidadeEntrada::saldoMR) {
        this.setHeader("MR")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoSaldoMF() = addColumnInt(ValidadeEntrada::saldoMF) {
        this.setHeader("MF")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoSaldoPK() = addColumnInt(ValidadeEntrada::saldoPK) {
        this.setHeader("PK")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoSaldoTM() = addColumnInt(ValidadeEntrada::saldoTM) {
        this.setHeader("TM")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoEstoque() = addColumnInt(ValidadeEntrada::estoque) {
        this.setHeader("Estoque")
        isExpand = false
        isResizable = true
    }

    fun Grid<ValidadeEntrada>.produtoVenda() = addColumnInt(ValidadeEntrada::totalVenda) {
        this.setHeader("Venda")
        isExpand = false
        isResizable = true
    }
}