package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.framework.model.Config
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
                    val origemPromocao: String,
                    val login: String) {
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

data class FiltroPrecoPromocao(val codigo: Int,
                               val vendno: Int,
                               val clno: Int,
                               val typeno: Int,
                               val tipoLista: List<ETipoListaPromocao>)

enum class ETipoListaPromocao {
  BASE, PROMOCAO
}
