package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.framework.util.parserDate
import br.com.astrosoft.promocao.model.saci
import java.time.LocalDate

class Produtos(
  val prdno: String,
  val codigo: Int,
  val descricao: String,
  val grade: String,
  val forn: Int,
  val abrev: String,
  val tipo: Int,
  val cl: Int,
  val codBar: String,
  val DS_VA: Int,
  val DS_AT: Int,
  val DS_TT: Int,
  val MR_VA: Int,
  val MR_AT: Int,
  val MR_TT: Int,
  val MF_VA: Int,
  val MF_AT: Int,
  val MF_TT: Int,
  val PK_VA: Int,
  val PK_AT: Int,
  val PK_TT: Int,
  val TM_VA: Int,
  val TM_AT: Int,
  val TM_TT: Int,
  val estoque: Int,
              ) {
  companion object {
    fun find(filter: FiltroProduto) = saci.listaProdutos(filter)
  }
}

data class FiltroProduto(val pesquisa: String) {
  val pesquisaNumero: Int?
    get() = pesquisa.toIntOrNull()

  val pesquisaData: LocalDate?
    get() = pesquisa.parserDate()

  val pesquisaString: String?
    get() = if (pesquisa.matches("^[0-9]$".toRegex())) null else pesquisa
}