package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.framework.model.BeanForm
import br.com.astrosoft.promocao.model.saci
import kotlin.math.absoluteValue

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
  var freteICMS: Double?,
  val precoCusto: Double?,
  val cfinanceiro: Double?,
  val impostos: String?,
) {
  val impostoList = impostos?.split("\\") ?: emptyList()

  fun icmsEntradaMa(icms: String): Double? {
    val imposto = impostoList.firstOrNull { linha ->
      val parte = linha.split(" +".toRegex())
      parte.getOrNull(0) == "ICMS" &&
          parte.getOrNull(1) == "ENTRADA" &&
          parte.getOrNull(2) == icms &&
          parte.getOrNull(3) == "MVA"

    } ?: return null
    return imposto.split(" +".toRegex()).getOrNull(2)?.replace(',', '.')?.toDoubleOrNull()
  }

  val mvaMa00: Double?
    get() = mvaMa("0")

  val mvaMa04: Double?
    get() = mvaMa("4")

  val mvaMa07: Double?
    get() = mvaMa("7")

  val mvaMa12: Double?
    get() = mvaMa("12")

  fun mvaMa(icms: String): Double? {
    val imposto = impostoList.firstOrNull { linha ->
      val parte = linha.split(" +".toRegex())
      parte.getOrNull(0) == "ICMS" &&
          parte.getOrNull(1) == "ENTRADA" &&
          parte.getOrNull(2) == icms &&
          parte.getOrNull(3) == "MVA"

    } ?: return null
    return imposto.split(" +".toRegex()).getOrNull(4)?.replace(',', '.')?.toDoubleOrNull()
  }

  fun mvaMaOrig(): Double? {
    val mvaOri = impostoList.firstOrNull { linha ->
      val parte = linha.split(" +".toRegex())
      parte.getOrNull(0) == "MVA" &&
          parte.getOrNull(1) == "ORIGINAL"

    } ?: return null
    return mvaOri.split(" +".toRegex()).getOrNull(2)?.replace(',', '.')?.toDoubleOrNull()
  }

  fun ncmMa(): String? {
    val ncmMa = impostoList.firstOrNull { linha ->
      val parte = linha.split(" +".toRegex())
      parte.getOrNull(0) == "TIMON" &&
          parte.getOrNull(1) == "-" &&
          parte.getOrNull(2) == "MA" &&
          parte.getOrNull(3) == "NCM"

    } ?: return null
    return ncmMa.split(" +".toRegex()).getOrNull(4)
  }

  val ncmMa: String?
    get() = ncmMa()

  val mvaMaOrig: Double?
    get() = mvaMaOrig()

  val diferencaCusto
    get() = (custoContabil ?: 0.00) - (precoCusto ?: 0.00)
  val freteICMSCalc: Double?
    get() {
      val freteCalc = frete ?: return null
      val icmsCalc = icmsp ?: return null
      val calc = freteCalc * (icmsCalc / 100)
      return calc.absoluteValue
    }

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
  val mvap: Double? = null,
  val icmsEnt: Double? = null,
  val ncm: String = "",
  val query: String,
)