package br.com.astrosoft.promocao.view.garantia.columns

import br.com.astrosoft.framework.util.format
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.promocao.model.beans.VendaEntrada
import com.github.mvysny.karibudsl.v10.isExpand
import com.vaadin.flow.component.grid.Grid
import java.time.LocalDate

object VendasCol {
    fun Grid<VendaEntrada>.produtoLoja() = addColumnInt(VendaEntrada::loja) {
        this.setHeader("Loja")
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoCodigo() = addColumnInt(VendaEntrada::codigo) {
        this.setHeader("Código")
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoDescricao() = addColumnString(VendaEntrada::descricao) {
        this.setHeader("Descrição")
        isExpand = true
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoGrade() = addColumnString(VendaEntrada::grade) {
        this.setHeader("Grade")
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoLoc() = addColumnString(VendaEntrada::localizacao) {
        this.setHeader("Loc")
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoMesesVenc() = addColumnInt(VendaEntrada::mesesVencimento) {
        this.setHeader("M. Venc")
        isExpand = false
        isResizable = true
    }

    private fun headerMes(num: Int): String {
        val dataMes = LocalDate.now().withDayOfMonth(1).minusMonths(num.toLong())
        return dataMes.format("MM/yy")
    }

    fun Grid<VendaEntrada>.produtoVenda00() = addColumnInt(VendaEntrada::venda00) {
        this.setHeader(headerMes(0))
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoVenda01() = addColumnInt(VendaEntrada::venda01) {
        this.setHeader(headerMes(1))
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoVenda02() = addColumnInt(VendaEntrada::venda02) {
        this.setHeader(headerMes(2))
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoVenda03() = addColumnInt(VendaEntrada::venda03) {
        this.setHeader(headerMes(3))
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoVenda04() = addColumnInt(VendaEntrada::venda04) {
        this.setHeader(headerMes(4))
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoVenda05() = addColumnInt(VendaEntrada::venda05) {
        this.setHeader(headerMes(5))
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoVenda06() = addColumnInt(VendaEntrada::venda06) {
        this.setHeader(headerMes(6))
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoVenda07() = addColumnInt(VendaEntrada::venda07) {
        this.setHeader(headerMes(7))
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoVenda08() = addColumnInt(VendaEntrada::venda08) {
        this.setHeader(headerMes(8))
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoVenda09() = addColumnInt(VendaEntrada::venda09) {
        this.setHeader(headerMes(9))
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoVenda10() = addColumnInt(VendaEntrada::venda10) {
        this.setHeader(headerMes(10))
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoVenda11() = addColumnInt(VendaEntrada::venda11) {
        this.setHeader(headerMes(11))
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoVenda12() = addColumnInt(VendaEntrada::venda12) {
        this.setHeader(headerMes(12))
        isExpand = false
        isResizable = true
    }

    fun Grid<VendaEntrada>.produtoEstoque() = addColumnInt(VendaEntrada::estoque) {
        this.setHeader("Estoque")
        isExpand = false
        isResizable = true
    }
}