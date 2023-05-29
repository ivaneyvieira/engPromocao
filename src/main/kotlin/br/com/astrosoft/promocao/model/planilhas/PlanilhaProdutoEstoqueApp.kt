package br.com.astrosoft.promocao.model.planilhas

import br.com.astrosoft.framework.model.Campo
import br.com.astrosoft.framework.model.CampoInt
import br.com.astrosoft.framework.model.CampoString
import br.com.astrosoft.promocao.model.beans.Produtos

class PlanilhaProdutoEstoqueApp : PlanilhaProduto() {
    override val campos: List<Campo<*, Produtos>>
        get() = listOf(
            CampoInt("Cód") { codigo },
            CampoString("Descrição") { descricao },
            CampoString("Grade") { grade },
            CampoString("UN") { unidade },
            CampoInt("Qt Compra") { qttyCompra ?: 0 },
            CampoInt("Qt Venda") { qttyVendas ?: 0 },
            CampoInt("Total") { estoque },
            CampoInt("MF App") { MF_App ?: 0 },
            CampoInt("MF Dif") { MF_Dif },
            CampoInt("Forn") { forn },
            CampoString("Abrev") { abrev },
            CampoString("Tributação") { tributacao ?: "" },
            CampoInt("Tipo") { tipo },
            CampoInt("CL") { cl },
            CampoString("Cód. Barras") { codBar },
        )
}