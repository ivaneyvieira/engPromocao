package br.com.astrosoft.promocao.model.planilhas

import br.com.astrosoft.framework.model.Campo
import br.com.astrosoft.framework.model.CampoInt
import br.com.astrosoft.framework.model.CampoString
import br.com.astrosoft.promocao.model.beans.Produtos

class PlanilhaProdutoEstoqueGeral : PlanilhaProduto() {
    override val campos: List<Campo<*, Produtos>>
        get() = listOf(
            CampoInt("Cód") { codigo },
            CampoString("Descrição") { descricao },
            CampoString("Grade") { grade },
            CampoInt("Total") { estoque },
            CampoInt("Forn") { forn },
            CampoString("Abrev") { abrev },
            CampoInt("Tipo") { tipo },
            CampoInt("CL") { cl },
            CampoString("Cód. Barras") { codBar },
            CampoInt("DS VA") { DS_VA },
            CampoInt("DS AT") { DS_AT },
            CampoInt("DS TT") { DS_TT },
            CampoInt("MR VA") { MR_VA },
            CampoInt("MR AT") { MR_AT },
            CampoInt("MR TT") { MR_TT },
            CampoInt("MF VA") { MF_VA },
            CampoInt("MF AT") { MF_AT },
            CampoInt("MF TT") { MF_TT },
            CampoInt("PK VA") { PK_VA },
            CampoInt("PK AT") { PK_AT },
            CampoInt("PK TT") { PK_TT },
            CampoInt("TM VA") { TM_VA },
            CampoInt("TM AT") { TM_AT },
            CampoInt("TM TT") { TM_TT },
        )
}