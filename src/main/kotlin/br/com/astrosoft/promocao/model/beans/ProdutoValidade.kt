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
  val descricaoCompleta2: String
                     ) {
  fun modifica(infoModifica: InfoModifica): Int {
    when (infoModifica.registro) {
      ERegistroValidade.CADASTRO  -> {
        return if (tipoValidade != "Mês" || infoModifica.tipo != ETipoValidade.ANO) {
          saci.modificaValidadeCadastro(this, infoModifica)
          1
        }
        else 0
      }

      ERegistroValidade.DESCRICAO -> {
        saci.modificaValidadeDescricao(this, infoModifica)
        return 1
      }

      else                        -> return 0
    }
  }

  companion object {
    fun findAll(filtro: FiltroGarantia): List<ProdutoValidade> {
      return saci.consultaProdutoValidade(filtro)
    }
  }
}

enum class ETipoValidade(val num: Int, val descricao: String) {
  DIA(0, "Dia"), SEMANA(1, "Semana"), MES(2, "Mês"), ANO(3, "Ano"), TODOS(4, "Todos");
}

enum class ERegistroValidade(val descricao: String) {
  CADASTRO("No Cadastro"), DESCRICAO("Na Descrição")
}

data class InfoModifica(val tipo: ETipoValidade?, val validade: Int, val registro: ERegistroValidade?)

data class FiltroGarantia(
  val tipoDiferenca: ETipoDiferencaGarantiaSimples,
  val tipoValidade: ETipoValidade,
  val codigo: String,
  val vendno: String,
  val typeno: String,
  val clno: String,
                         )