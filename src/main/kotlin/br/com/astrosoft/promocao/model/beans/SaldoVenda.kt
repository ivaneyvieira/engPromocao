package br.com.astrosoft.promocao.model.beans

import java.time.LocalDate

class SaldoVenda(
  val loja: Int,
  val codigo: Int,
  val grade: String,
  val date: LocalDate,
  val quant: Int,
)