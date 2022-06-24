package br.com.astrosoft.promocao.model

import br.com.astrosoft.framework.util.CupsUtils
import br.com.astrosoft.framework.util.SystemUtils
import br.com.astrosoft.framework.util.ZPLPreview

object EtiquetaChave {
  private fun template(dados: DadosEtiquetaProduto): String {
    val template = "/templatePrint/etiquetaProduto.zpl"
    val zpl = SystemUtils.readFile(template)
    return zpl
      .replace("[codigo]", dados.codigo)
      .replace("[grade]", dados.grade)
      .replace("[descricao]", dados.descricao)
      .replace("[barcode]", dados.barcode)
  }

  private fun print(impressora: String, dados: List<DadosEtiquetaProduto>) {
    val zpl = dados.joinToString("\n") {
      template(it)
    }
    CupsUtils.printCups(impressora, zpl) {
      println(it)
    }
  }

  private fun printPreview(impressora: String, dados: List<DadosEtiquetaProduto>) {
    val dadosPreview = if (dados.size > 50) dados.subList(0, 50) else dados
    val zplPreview = dadosPreview.joinToString("\n") {
      template(it)
    }
    ZPLPreview.showZPLPreview(impressora, zplPreview) {
      print(impressora, dados)
    }
  }

  fun printPreviewProdutos(impressora: String, produtos: List<DadosEtiquetaProduto>) {
    printPreview(impressora, produtos)
  }
}

data class DadosEtiquetaProduto(
  val codigo: String,
  val grade: String,
  val descricao: String,
  val barcode: String,
                               )

