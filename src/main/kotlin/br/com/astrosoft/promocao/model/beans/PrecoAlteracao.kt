package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.DadosEtiquetaProduto
import br.com.astrosoft.promocao.model.saci
import java.time.LocalDate
import java.time.LocalTime

class PrecoAlteracao(
  val codigo: String,
  val descricao: String,
  val data: LocalDate?,
  val time: LocalTime?,
  val userno: Int?,
  val usuario: String?,
  val refprice: Double,
  val promo_price: Double?,
  val dataPromocao: LocalDate?,
  val desconto: Double?,
  val clno: Int,
  val centroLucro: String,
  val vendno: Int,
  val fornecedor: String,
  val typeno: Int,
  val tipoProduto: String,
                    ) {
  fun dadosEtiquetas(): List<DadosEtiquetaProduto> {
    return saci.produtoGrade(codigo).map {
      DadosEtiquetaProduto(codigo = codigo, grade = it.grade, descricao = descricao, barcode = it.barcode)
    }
  }

  companion object {
    fun precoAlterado(filtro: FiltroPrecoAlteracao) = saci.produtosPrecoAlteracao(filtro)
    fun promocaoAlterado(filtro: FiltroPrecoAlteracao) = saci.produtosPromocaoAlteracao(filtro)
    fun baseAlterado(filtro: FiltroPrecoAlteracao) = saci.produtosBaseAlteracao(filtro)
  }
}

data class FiltroPrecoAlteracao(
  val codigo: String,
  val vendno: Int,
  val clno: Int,
  val typeno: Int,
  val dataInicial: LocalDate?,
  val dataFinal: LocalDate?,
                               )