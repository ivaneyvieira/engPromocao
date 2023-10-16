package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.saci
import java.time.LocalDate
import java.time.LocalDateTime

class QuerySaldoData {
  private var filtro = FiltroSaldoData(null, null)
  private var mapSaldo: Map<ChaveProduto, List<SaldoVenda>>? = null
  private var nextUpdate = LocalDateTime.now().plusMinutes(5)

  fun update(diDate: LocalDate?, dfDate: LocalDate?) {
    update(FiltroSaldoData(diDate, dfDate))
  }

  fun update(filtro: FiltroSaldoData) {
    if (this.filtro != filtro) {
      this.filtro = filtro
      execute()
    } else {
      if (LocalDateTime.now() > nextUpdate) {
        execute()
        nextUpdate = LocalDateTime.now().plusMinutes(5)
      }
    }
  }

  private fun execute() {
    val mapSaldo: Map<ChaveProduto, List<SaldoVenda>> = saci.saldoData(filtro.diDate, filtro.dfDate).groupBy {
      it.chaveNota()
    }
    this@QuerySaldoData.mapSaldo = mapSaldo
  }

  fun findVenda(loja: Int?, codigo: Int?, grade: String?): Map<Int, Int> {
    return mapSaldo?.get(ChaveProduto(loja, codigo, grade)).orEmpty().groupBy { it.numMes }.mapValues { values ->
      values.value.sumOf { it.quant }
    }
  }
}

data class FiltroSaldoData(val diDate: LocalDate?, val dfDate: LocalDate?)