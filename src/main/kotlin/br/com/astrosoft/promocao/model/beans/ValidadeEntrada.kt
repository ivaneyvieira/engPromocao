package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.estoque
import java.sql.Date
import java.time.LocalDate

class ValidadeEntrada(
  val loja: Int?,
  val codigo: Int?,
  val descricao: String?,
  val grade: String?,
  val validade: Int?,
  val nfEntrada: String?,
  val dataEntrada: LocalDate?,
  val dataFabricacao: LocalDate?,
  val mesesFabricacao: Int?,
  val vencimento: LocalDate?,
  val mesesVencimento: Int?,
  val entrada: Int?,
  val saldo: Int?,
  val estoque: Int?,
  val status: String?,
  val mfno: Int?,
  val typeno: Int?,
  val clno: Int?,
  val deptno: Int?,
  val groupno: Int?,
  val taxno: Int?,
  val saldoDS: Int?,
  val saldoMR: Int?,
  val saldoMF: Int?,
  val saldoPK: Int?,
  val saldoTM: Int?,
) {
  companion object {
    fun findAll(filtro: FiltroValidadeEntrada): List<ValidadeEntrada> {
      return estoque.consultaValidadeEntrada(filtro)
    }
  }
}
