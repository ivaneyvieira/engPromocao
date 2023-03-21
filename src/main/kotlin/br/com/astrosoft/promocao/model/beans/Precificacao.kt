package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.framework.model.BeanForm
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
  var mvap: Double?,
  var icmsp: Double?,
  var fcp: Double,
  var pcfabrica: Double?,
  var ipi: Double?,
  var embalagem: Double?,
  var retido: Double?,
  var creditoICMS: Double?,
  var frete: Double?,
  val custoContabil: Double?,
  var icms: Double?,
  var pis: Double?,
  var ir: Double?,
  var contrib: Double?,
  var fixa: Double?,
  var outras: Double?,
  val lucroLiq: Double?,
  val precoSug: Double?,
  val precoRef: Double?,
  val precoDif: Double?,
  val ncm: String,
  val rotulo: String?,
  ) {
  fun save() {
    saci.savePrecificacao(this)
  }

  companion object {
    fun findAll(filtro: FiltroPrecificacao) = saci.listaPrecificacao(filtro)
    fun updateItens(list: List<Precificacao>, bean: BeanForm) {
      saci.saveListPrecificacao(list, bean)
    }
  }
}

data class FiltroPrecificacao(
  val codigo: Int,
  val listVend: List<Int>,
  val tributacao: String,
  val typeno: String,
  val clno: Int,
  val marcaPonto: EMarcaPonto,
  val query: String,
                             )