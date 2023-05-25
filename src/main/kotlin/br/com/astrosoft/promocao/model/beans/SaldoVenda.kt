package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.framework.util.format
import java.time.LocalDate


val mapDate =
    0.until(13).associateBy { num -> LocalDate.now().withDayOfMonth(1).minusMonths(num.toLong()).format("MM/yyyy") }

class SaldoVenda(
    val loja: Int,
    val codigo: Int,
    val grade: String,
    val date: LocalDate,
    val quant: Int,
) {
    val numMes
        get() = mapDate[date.format("MM/yyyy")] ?: -1

    fun chaveNota(): ChaveProcuto {
        return ChaveProcuto(loja, codigo, grade)
    }
}

data class ChaveProcuto(
    val loja: Int?,
    val codigo: Int?,
    val grade: String?,
)