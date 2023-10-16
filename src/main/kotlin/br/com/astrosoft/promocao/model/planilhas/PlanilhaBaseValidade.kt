package br.com.astrosoft.promocao.model.planilhas

import br.com.astrosoft.framework.model.Campo
import br.com.astrosoft.framework.model.CampoInt
import br.com.astrosoft.framework.model.CampoString
import br.com.astrosoft.promocao.model.beans.ComparaValidade
import com.github.nwillc.poink.workbook
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment

class PlanilhaBaseValidade {
  private val campos: List<Campo<*, ComparaValidade>> =
    listOf(
      CampoInt("Código") { codigo },
      CampoString("Descrição") { descricao },
      CampoString("Grade") { grade ?: "" },
      CampoString("Loc") { localizacao ?: "" },
      CampoInt("Estoque") { estoque },
      CampoInt("Validade Desc.") { validade_descricao },
      CampoInt("Validade Cad.") { validade_cadastro },
      CampoInt("Diferença") { diferenca },
      CampoString("Tipo") { tipoString },
    )

  fun grava(listaProdutos: List<ComparaValidade>): ByteArray {
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

