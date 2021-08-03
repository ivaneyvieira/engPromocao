package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.saci
import java.time.LocalDate

class PrecoPromocao(val codigo: String,
                    val descricao: String,
                    val validade: LocalDate?,
                    val refPrice: Double,
                    val desconto: Double?,
                    val precoPromocional: Double?,
                    val clno: Int,
                    val centroLucro: String,
                    val vendno: Int,
                    val fornecedor: String,
                    val typeno: Int,
                    val tipoProduto: String,
                    val saldo: Int,
                    val origemPromocao: String) {
  companion object {
    fun find(filtro: FiltroPrecoPromocao) = saci.produtosPromocao(filtro)
  }
}

data class FiltroPrecoPromocao(val vendno: Int,
                               val clno: Int,
                               val typeno: Int,
                               val tipoSaldo: ETipoSaldo,
                               val tipoLista: List<ETipoListaPromocao>)

enum class ETipoListaPromocao(val descricao: String) {
  BASE("Base"), PROMOCAO("Promoção")
}

enum class ETipoSaldo(val descricao: String) {
  SALDO("Com saldo"), ZERO("Sem Saldo"), TUDO("Tudo")
}