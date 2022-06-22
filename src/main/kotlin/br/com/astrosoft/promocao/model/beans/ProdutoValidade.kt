package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.saci

class ProdutoValidade(val codigo: String,
                      val descricao: String,
                      val clno: Int,
                      val centroLucro: String,
                      val vendno: Int,
                      val fornecedor: String,
                      val typeno: Int,
                      val tipoProduto: String,
                      val tipoValidade: String,
                      val mesesValidade: Int) {

  companion object {
    fun findAll(filtro: String): List<ProdutoValidade> {
      return saci.consultaProdutoValidade(filtro)
    }
  }
}

enum class ETipoValidade(val num: Int, val descricao: String) {
  DIA(0, "Dia"), SEMANA(1, "Semana"), MES(2, "Mês"), ANO(3, "Ano");
}