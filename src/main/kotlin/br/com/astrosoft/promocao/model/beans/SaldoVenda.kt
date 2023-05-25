package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.framework.util.format
import java.time.LocalDate


val mapDate = 0.until(12).associateBy { num -> LocalDate.now().withDayOfMonth(1).minusMonths(num.toLong()).format("MM/yyyy") }


class SaldoVenda(
    val loja: Int,
    val codigo: Int,
    val grade: String,
    val date: LocalDate,
    val quant: Int,
){
val numMes
    get() = mapDate[date.withDayOfMonth(1).format("MM/yyyy")]
}