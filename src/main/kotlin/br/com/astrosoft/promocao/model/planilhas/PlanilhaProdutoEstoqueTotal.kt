package br.com.astrosoft.promocao.model.planilhas

import br.com.astrosoft.framework.model.Campo
import br.com.astrosoft.framework.model.CampoInt
import br.com.astrosoft.framework.model.CampoNumber
import br.com.astrosoft.framework.model.CampoString
import br.com.astrosoft.promocao.model.beans.Produtos
import br.com.astrosoft.promocao.model.planilhas.PlanilhaProduto

class PlanilhaProdutoEstoqueTotal: PlanilhaProduto() {
  override val campos: List<Campo<*, Produtos>>
    get() = listOf(
      CampoInt("Cód") { codigo  },
      CampoString("Descrição") { descricao  },
      CampoString("Grade") { grade  },
      CampoInt("Total") { estoque  },
      CampoInt("Forn") { forn  },
      CampoString("Abrev") { abrev  },
      CampoInt("Tipo") { tipo  },
      CampoInt("CL") { cl  },
      CampoString("Cód. Barras") { codBar  },
      CampoInt("DS TT") { DS_TT  },
      CampoInt("MR TT") { MR_TT  },
      CampoInt("MF TT") { MF_TT  },
      CampoInt("PK TT") { PK_TT  },
      CampoInt("TM TT") { TM_TT  },
                  )
}