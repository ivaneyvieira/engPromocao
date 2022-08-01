package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.saci

class ComparaValidade(val codigo: Int,
                      val prdno: String,
                      val descricao: String,
                      val estoque: Int,
                      val validade_descricao: Int,
                      val validade_cadastro: Int,
                      val diferenca: Int,
                      val tipo: Int,
                      val localizacao: String) {
  val tipoGarantia
    get() = ETipoDiferencaGarantia.values().toList().firstOrNull { it.num == tipo }

  val tipoString
    get() = tipoGarantia?.descricao ?: ""

  companion object {
    fun consultaByTipo(tipo: ETipoDiferencaGarantia) = saci.consultaValidade(tipo)
  }
}

enum class ETipoDiferencaGarantia(val num: Int, val descricao: String) {
  IGUAL(1, "Validade Igual"),
  DIFCAD(2, "Sem validade cadastro"),
  DIFDESC(3, "Sem validade descricao"),
  DIFTUDO(4, "Validade Diferente"),
  TODOS(0, "Todos")
}