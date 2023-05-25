package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.estoque
import br.com.astrosoft.promocao.model.saci

class VendaEntrada(
    val loja: Int?,
    val codigo: Int?,
    val descricao: String?,
    val grade: String?,
    val mesesVencimento: Int?,
    val estoque: Int?,
    val status: String?,
    val mfno: Int?,
    val typeno: Int?,
    val clno: Int?,
    val deptno: Int?,
    val groupno: Int?,
    val taxno: Int?,
    val venda00: Int?,
    val venda01: Int?,
    val venda02: Int?,
    val venda03: Int?,
    val venda04: Int?,
    val venda05: Int?,
    val venda06: Int?,
    val venda07: Int?,
    val venda08: Int?,
    val venda09: Int?,
    val venda10: Int?,
    val venda11: Int?,
    val venda12: Int?,
    val localizacao: String?,
) {
    companion object {
        fun findAll(filtro: FiltroValidadeEntrada): List<VendaEntrada> {
            val listVenda = saci.saldoData(filtro.diVenda, filtro.dfVenda)

            val listaValidade = estoque.consultaValidadeEntrada(filtro)
            val prdNota = PrdCodigo.findPrdNfe(filtro.nfe)
            return listaValidade.asSequence().filter { venda ->
                when {
                    filtro.nfe.isBlank() -> true
                    prdNota.isEmpty() -> false
                    else -> prdNota.any { prd ->
                        prd.prdno.trim().toIntOrNull() == venda.codigo && prd.grade == venda.grade
                    }
                }
            }.map { venda ->
                val mapVenda = listVenda.filter { saldo ->
                    saldo.codigo == venda.codigo && saldo.grade == venda.grade
                }.groupBy { it.numMes }.mapValues { values ->
                    values.value.sumOf { it.quant }
                }
                VendaEntrada(
                    loja = venda.loja,
                    codigo = venda.codigo,
                    descricao = venda.descricao,
                    grade = venda.grade,
                    mesesVencimento = venda.mesesVencimento,
                    estoque = venda.estoque,
                    status = venda.status,
                    mfno = venda.mfno,
                    typeno = venda.typeno,
                    clno = venda.clno,
                    deptno = venda.deptno,
                    groupno = venda.groupno,
                    taxno = venda.taxno,
                    venda00 = mapVenda[0],
                    venda01 = mapVenda[1],
                    venda02 = mapVenda[2],
                    venda03 = mapVenda[3],
                    venda04 = mapVenda[4],
                    venda05 = mapVenda[5],
                    venda06 = mapVenda[6],
                    venda07 = mapVenda[7],
                    venda08 = mapVenda[8],
                    venda09 = mapVenda[9],
                    venda10 = mapVenda[10],
                    venda11 = mapVenda[11],
                    venda12 = mapVenda[12],
                    localizacao = venda.localizacao,
                )
            }.toList()
        }
    }
}
