package br.com.astrosoft.promocao.model

import br.com.astrosoft.framework.util.CupsUtils
import br.com.astrosoft.framework.util.SystemUtils
import br.com.astrosoft.framework.util.ZPLPreview
import br.com.astrosoft.framework.util.format
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object EtiquetaChave {
  private fun template(dados: DadosEtiquetaProduto): String {
    val template = "/templatePrint/etiquetaPrecoProduto.zpl"
    val zpl = SystemUtils.readFile(template)
    return zpl
      .replace("[data]", dados.dataStr)
      .replace("[ref]", dados.ref)
      .replace("[preco]", dados.precoStr)
      .replace("[larg]", dados.largStr)
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

data class DadosEtiquetaProduto(val codigo: String,
                                val grade: String,
                                val descricao: String,
                                val barcode: String,
                                val ref: String,
                                val data: LocalDate?,
                                val preco: Double,
                                val metroCaixa: Double?) {
  val dataStr: String get() = data?.format(DateTimeFormatter.ofPattern("dd/MM/yy")) ?: ""
  val precoStr: String = if (metroCaixa == null) preco.format() else (preco / metroCaixa).format()
  private val larg = when {
    precoStr.length < 4  -> 140
    precoStr.length == 5 -> 120
    precoStr.length == 6 -> 105
    else                 -> 90
  }
  val largStr: String get() = larg.toString()
}

