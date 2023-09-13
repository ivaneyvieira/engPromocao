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

    var filtroIcmsMa: String = "4"

    val icmsEntradaMa: Double?
        get() = icmsEntradaMa(filtroIcmsMa)

    val mvaMa: Double?
        get() = mvaMa(filtroIcmsMa)

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
    val query: String,
)