package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.saci

class Precificacao(
  val prdno: String,
  val codigo: String,
  val descricao: String,
  val cpmf: Double,
  val vendno: Int,
  val fornecedor: String,
  val tributacao: String,
                  ) {
  fun save() {
    saci.savePrecificacao(this)
  }

  companion object {
    fun findAll(filtro: FiltroPrecificacao) = saci.listaPrecificacao(filtro)
  }
}

data class FiltroPrecificacao(val codigo: Int, val vendno: Int, val tributacao: String)