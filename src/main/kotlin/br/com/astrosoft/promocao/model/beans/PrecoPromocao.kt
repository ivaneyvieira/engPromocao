package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.framework.model.Config
import br.com.astrosoft.promocao.model.saci
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
                    val origemPromocao: String) {
  companion object {
    fun find(filtro: FiltroPrecoPromocao) = saci.produtosPromocao(filtro)

    fun marcaDesconto(list: List<PrecoPromocao>, desconto: Double, validade: LocalDate) {
      val login = Config.user?.login ?: "ADM"
      saci.apagaMarcasPromocao(login)

      val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
      val marca = LocalDateTime.now().format(formatter) + Config.user?.login
      list.forEach { precoPromocao ->
        saci.executaPromocao(precoPromocao.codigo, desconto, validade, marca)
      }
    }

    fun desfazDesconto() {
      val login = Config.user?.login ?: "ADM"
      saci.desfazerPromocao(login)
    }
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
