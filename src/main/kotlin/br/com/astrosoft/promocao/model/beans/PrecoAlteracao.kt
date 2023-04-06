package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.DadosEtiquetaProduto
import br.com.astrosoft.promocao.model.saci
import java.time.LocalDate
import java.time.LocalTime
import kotlin.math.roundToInt

class PrecoAlteracao(
  val codigo: String,
  val descricao: String,
  val data: LocalDate?,
  val time: LocalTime?,
  val userno: Int?,
  val usuario: String?,
  val refprice: Double?,
  val refpriceAnt: Double?,
  val promo_price: Double?,
  val promo_priceAnt: Double?,
  val dataPromocao: LocalDate?,
  val desconto: Double?,
  val clno: Int,
  val centroLucro: String,
  val vendno: Int,
  val fornecedor: String,
  val refFornecedor: String,
  val typeno: Int,
  val tipoProduto: String,
  val precoEtiqueta: Double?
) {
  fun dadosEtiquetas(): List<DadosEtiquetaProduto> {
    return saci.produtoGrade(codigo).map {
      DadosEtiquetaProduto(
        codigo = codigo,
        grade = it.grade,
        descricao = descricao,
        barcode = it.barcode,
        ref = refFornecedor,
        data = data,
        metroCaixa = metroCaixa,
        preco = precoEtiqueta ?: 0.00
      )
    }
  }

  val metroCaixa: Double?
    get() = if (clno in 10000..19999) {
      val regexGrupo = "\\(([\\d,]+)\\)".toRegex()
      val numero = regexGrupo.find(descricao)?.groupValues?.getOrNull(1)
      if (numero == null) null
      else {
        val valor = numero.replace(',', '.')
        val result = if (valor.contains('.')) valor.toDoubleOrNull()
        else valor.toDoubleOrNull()?.div(100.00)
        result?.times(100)?.roundToInt()?.div(100.00)
      }
    } else {
      null
    }

  val promo_priceM: Double?
    get() = if (metroCaixa == null) null else promo_price?.div(metroCaixa ?: 1.00)

  val refpriceM: Double?
    get() = if (metroCaixa == null) null else refprice?.div(metroCaixa ?: 1.00)

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