package br.com.astrosoft.promocao.model.planilhas

import br.com.astrosoft.framework.model.Campo
import br.com.astrosoft.framework.model.CampoInt
import br.com.astrosoft.framework.model.CampoString
import br.com.astrosoft.promocao.model.beans.ProdutoValidade
import com.github.nwillc.poink.workbook
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment

class PlanilhaProdutoValidade {
  private val campos: List<Campo<*, ProdutoValidade>> =
    listOf(
      CampoString("Código") { codigo },
      CampoString("Descrição") { descricao },
      CampoString("Grade") { grade ?: "" },
      CampoInt("Estoque") { estoque ?: 0 },
      CampoString("Descrição Completa Linha 2") { descricaoCompleta2 },
      CampoInt("Cl") { clno },
      CampoInt("Cod. Forn") { vendno },
      CampoString("Fornecedor") { fornecedor },
      CampoInt("Cod. Tipo") { typeno },
      CampoString("Tempo") { tipoValidade },
      CampoInt("Validade") { mesesValidade },
    )

  fun grava(listaProdutos: List<ProdutoValidade>): ByteArray {
    val wb = workbook {
      val headerStyle = cellStyle("Header") {
        fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
        fillPattern = FillPatternType.SOLID_FOREGROUND
        this.verticalAlignment = VerticalAlignment.TOP
      }
      val rowStyle = cellStyle("Row") {
        this.verticalAlignment = VerticalAlignment.TOP
      }
      val stSemGrade = sheet("Validade") {
        val headers = campos.map { it.header }
        row(headers, headerStyle)
        listaProdutos.forEach { produto ->
          val valores = campos.map {
            it.produceValue(produto)
          }
          row(valores, rowStyle)
        }
      }

      campos.forEachIndexed { index, _ ->
        stSemGrade.autoSizeColumn(index)
      }
    }
    val outBytes = ByteArrayOutputStream()
    wb.write(outBytes)
    return outBytes.toByteArray()
  }
}

