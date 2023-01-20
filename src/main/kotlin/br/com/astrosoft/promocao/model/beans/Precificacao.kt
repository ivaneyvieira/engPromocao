package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.saci

class Precificacao(
  val prdno: String,
  val codigo: String,
  val descricao: String,
  var cpmf: Double,
  val vendno: Int,
  val fornecedor: String,
  val tributacao: String,
  val typeno: Int,
  val clno: Int,
  val mvap: Double?,
  val icmsp: Double?,
  var fcp: Double,
  val pcfabrica: Double?,
  val ipi: Double?,
  val embalagem: Double?,
  val retido: Double?,
  val creditoICMS: Double?,
  val frete: Double?,
  val custoContabil: Double?,
  val icms: Double?,
  val pis: Double?,
  val ir: Double?,
  val contrib: Double?,
  val fixa: Double?,
  val outras: Double?,
  val lucroLiq: Double?,
  val precoSug: Double?,
  val precoRef: Double?,
  ) {
  fun save() {
    saci.savePrecificacao(this)
  }

  companion object {
    fun findAll(filtro: FiltroPrecificacao) = saci.listaPrecificacao(filtro)
  }
}

data class FiltroPrecificacao(
  val codigo: Int,
  val listVend: List<Int>,
  val tributacao: String,
  val typeno: Int,
  val clno: Int,
  val marcadoPonto: Boolean,
                             )