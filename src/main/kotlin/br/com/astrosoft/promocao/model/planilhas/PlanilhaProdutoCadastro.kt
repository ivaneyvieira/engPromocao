package br.com.astrosoft.promocao.model.planilhas

import br.com.astrosoft.framework.model.Campo
import br.com.astrosoft.framework.model.CampoInt
import br.com.astrosoft.framework.model.CampoNumber
import br.com.astrosoft.framework.model.CampoString
import br.com.astrosoft.promocao.model.beans.Produtos

class PlanilhaProdutoCadastro : PlanilhaProduto() {
  override val campos: List<Campo<*, Produtos>>
    get() = listOf(
      CampoInt("Cód") { codigo },
      CampoString("Descrição") { descricao },
      CampoString("Grade") { grade },
      CampoString("UN") { unidade },
      CampoString("F. Linha") { foraLinha },
      CampoString("Trib") { trib },
      CampoInt("Forn") { forn },
      CampoString("Abrev") { abrev },
      CampoInt("Tipo") { tipo },
      CampoInt("CL") { cl },
      CampoString("Cód. Barras") { codBar },
      CampoString("Ref Forn") { refForn },
      CampoNumber("P. Bruto") { pesoBruto },
      CampoString("U. Gar") { uGar },
      CampoInt("T Gar") { tGar },
      CampoNumber("Emb") { emb },
      CampoString("NCM") { ncm },
      CampoString("Site") { site },
    )
}