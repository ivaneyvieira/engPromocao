package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.saci

class ProdutoValidade(
  val codigo: String,
  val descricao: String,
  val clno: Int,
  val centroLucro: String,
  val vendno: Int,
  val fornecedor: String,
  val typeno: Int,
  val tipoProduto: String,
  val tipoValidade: String,
  val mesesValidade: Int,
  val descricaoCompleta1: String,
  val descricaoCompleta2: String,
                     ) {
  fun modifica(infoModifica: InfoModifica) {
    when (infoModifica.registro) {
      ERegistroValidade.CADASTRO  -> saci.modificaValidadeCadastro(this, infoModifica)
      ERegistroValidade.DESCRICAO -> saci.modificaValidadeDescricao(this, infoModifica)
    }
  }

  companion object {
    fun findAll(filtro: String): List<ProdutoValidade> {
      return saci.consultaProdutoValidade(filtro)
    }
  }
}

enum class ETipoValidade(val num: Int, val descricao: String) {
  DIA(0, "Dia"), SEMANA(1, "Semana"), MES(2, "Mês"), ANO(3, "Ano");
}

enum class ERegistroValidade(val descricao: String) {
  CADASTRO("No Cadastro"), DESCRICAO("Na Descrição")
}

data class InfoModifica(val tipo: ETipoValidade?, val validade: Int, val registro: ERegistroValidade?)