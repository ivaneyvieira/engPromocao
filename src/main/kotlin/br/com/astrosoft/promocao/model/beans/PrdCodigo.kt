package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.promocao.model.saci

class PrdCodigo (val prdno: String, val grade: String){
  companion object{
    fun findPrdNfe(numero: String) = saci.findPrdNfe(numero)
  }
}