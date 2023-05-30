package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.estoque
import java.time.LocalDate

class GarantiaEstoqueApp(
    val loja: Int,
    val codigo: Int,
    val descricao: String,
    val grade: String,
    val localizacao: String?,
    val estoqueNerus: Int?,
    val estoqueApp: Int?,
    val saldo: Int?,
    val entrada: Int?,
    val nfEntrada: String?,
    val dataEntrada: LocalDate?,
) {
    companion object {
        fun findAll(filtro: FiltroEstoqueApp): List<GarantiaEstoqueApp> {
            val prdNota = PrdCodigo.findPrdNfe(filtro.nfe)
            val nfe = prdNota.firstOrNull()?.nfe ?: ""
            return estoque.consultaEstoqueApp(filtro.copy(nfe = nfe))
        }
    }
}

data class FiltroEstoqueApp(
    val query: String,
    val marca: EMarcaPonto,
    val listVend: List<Int>,
    val tributacao: String,
    val typeno: Int,
    val clno: Int,
    val estoque: EEstoqueTotal,
    val nfe: String,
)