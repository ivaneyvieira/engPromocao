package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.saci
import java.time.LocalDate

class PrecoPromocao(
  val codigo: String,
  val descricao: String,
  val clno: Int,
  val centroLucro: String,
  val vendno: Int,
  val fornecedor: String,
  val typeno: Int,
  val tipoProduto: String,
  val preco: Double?,
  val validade: LocalDate?,
  val precoPromocional: Double?,
  val origemPromocao: String?,
  val refPrice: Double?,
  val promoPrice: Double?,
  val promono: Int?,
  val descricaoPromocao: String?,
  val tipoPromocao: String?,
  val percentual: Double?,
                   ) {
  companion object {
    fun find(filtro: FiltroPrecoPromocao) = saci.produtosPromocao(filtro)
  }
}

data class FiltroPrecoPromocao(val vendno: Int, val clno: Int, val typeno: Int, val tipoLista: ETipoListaPromocao)

enum class ETipoListaPromocao(val descricao: String) {
  BASE("Base"), PROMOCAO("Promoção"), PRECIFICACAO("Precificação")
}