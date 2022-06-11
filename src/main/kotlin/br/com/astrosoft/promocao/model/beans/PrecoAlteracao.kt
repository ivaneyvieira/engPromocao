package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.DadosEtiquetaProduto
import br.com.astrosoft.promocao.model.saci
import java.time.LocalDate

class PrecoAlteracao(val codigo: String,
                     val descricao: String,
                     val alteracao: LocalDate?,
                     val precoNew: Double,
                     val precoOld: Double,
                     val clno: Int,
                     val centroLucro: String,
                     val vendno: Int,
                     val fornecedor: String,
                     val typeno: Int,
                     val tipoProduto: String,
                     val validade: LocalDate?,
                     val refPrice: Double,
                     val desconto: Double?,
                     val precoPromocional: Double?,) {
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

data class FiltroPrecoAlteracao(val codigo: Int,
                                val vendno: Int,
                                val clno: Int,
                                val typeno: Int,
                                val dataAlteracao: LocalDate)