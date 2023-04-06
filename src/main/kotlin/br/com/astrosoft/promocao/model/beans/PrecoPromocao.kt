package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.framework.model.Config
import br.com.astrosoft.promocao.model.DadosEtiquetaProduto
import br.com.astrosoft.promocao.model.saci
import java.time.LocalDate
import kotlin.math.roundToInt

class PrecoPromocao(
  val codigo: String,
  val descricao: String,
  val validade: LocalDate?,
  val refPrice: Double,
  val desconto: Double?,
  val precoPromocional: Double?,
  val clno: Int,
  val centroLucro: String,
  val vendno: Int,
  val fornecedor: String,
  val refFornecedor: String,
  val typeno: Int,
  val tipoProduto: String,
  val origemPromocao: String,
  val login: String, val estoque: Int?,
) {

  fun dadosEtiquetas(): List<DadosEtiquetaProduto> {
    return saci.produtoGrade(codigo).map {
      DadosEtiquetaProduto(
        codigo = codigo,
        grade = it.grade,
        descricao = descricao,
        barcode = it.barcode,
        ref = refFornecedor,
        data = LocalDate.now(),
        metroCaixa = metroCaixa,
        preco = precoEtiqueta ?: 0.00
      )
    }
  }

  val precoEtiqueta: Double?
    get() = if (precoPromocional == 0.00) refPrice else precoPromocional

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

  companion object {
    fun find(filtro: FiltroPrecoPromocao) = saci.produtosPromocao(filtro)

    fun prorrogaDesconto(list: List<PrecoPromocao>, validade: LocalDate) {
      val login = Config.user?.login ?: "ADM"

      list.forEach { precoPromocao ->
        saci.prorrogaPromocao(precoPromocao.codigo, validade, login)
      }
    }

    fun executaDesconto(list: List<PrecoPromocao>, desconto: Double, validade: LocalDate) {
      val login = Config.user?.login ?: "ADM"

      list.forEach { precoPromocao ->
        saci.executaPromocao(precoPromocao.codigo, desconto, validade, login)
      }
    }

    fun desfazDesconto(list: List<PrecoPromocao>) {
      val login = Config.user?.login ?: "ADM"
      list.forEach { precoPromocao ->
        saci.desfazPromocao(precoPromocao.codigo, login)
      }
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as PrecoPromocao

    if (codigo != other.codigo) return false

    return true
  }

  override fun hashCode(): Int {
    return codigo.hashCode()
  }
}

data class FiltroPrecoPromocao(
  val codigo: Int,
  val vendno: Int,
  val clno: Int,
  val typeno: Int,
  val decimal99: String,
  val tipoLista: List<ETipoListaPromocao>,
  val marcaPonto: EMarcaPonto
)

enum class ETipoListaPromocao {
  BASE, PROMOCAO
}
