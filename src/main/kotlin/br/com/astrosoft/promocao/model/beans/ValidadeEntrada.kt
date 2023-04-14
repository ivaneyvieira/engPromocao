package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.estoque
import java.sql.Date
import java.time.LocalDate

class ValidadeEntrada(
  val loja: Int,
  val codigo: Int,
  val descricao: String,
  val grade: String,
  val validade: Int? = null,
  val nfEntrada: String? = null,
  val dataEntrada: LocalDate? = null,
  val dataFabricacao: LocalDate? = null,
  val mesesFabricacao: Int? = null,
  val vencimento: LocalDate? = null,
  val mesesVencimento: Int? = null,
  val entrada: Int? = null,
  val saldo: Int? = null,
  val estoque: Int? = null,
  val status: String
) {
  companion object {
    fun findAll(filtro: FiltroValidadeEntrada): List<ValidadeEntrada> {
      return estoque.consultaValidadeEntrada(filtro)
    }
  }
}
