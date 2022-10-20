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
  val estJs: Double,
  val estDs: Double,
  val estMr: Double,
  val estMf: Double,
  val estPk: Double,
  val estTm: Double,
  val estoque: Double,
  val custo: Double,
  val pPromo: Double,
  val pRef: Double,
              ){
  companion object{
    fun find(filter : FiltroProduto) = saci.listaProdutos(filter)
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