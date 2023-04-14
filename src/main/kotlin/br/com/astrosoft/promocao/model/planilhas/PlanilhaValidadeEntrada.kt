package br.com.astrosoft.promocao.model.planilhas

import br.com.astrosoft.framework.model.Campo
import br.com.astrosoft.framework.model.CampoInt
import br.com.astrosoft.framework.model.CampoString
import br.com.astrosoft.framework.util.format
import br.com.astrosoft.promocao.model.beans.ValidadeEntrada
import com.github.nwillc.poink.workbook
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment

class PlanilhaValidadeEntrada {
  private val campos: List<Campo<*, ValidadeEntrada>> =
    listOf(
      CampoInt("Loja") { loja },
      CampoInt("Código") { codigo },
      CampoString("Descrição") { descricao },
      CampoString("Grade") { grade },
      CampoInt("Validade") { validade ?: 0 },
      CampoString("NF Entrada") { nfEntrada ?: "" },
      CampoString("Data Entrada") { dataEntrada.format() },
      CampoString("Fabricacao") { dataFabricacao.format() },
      CampoInt("Meses Fab") { mesesFabricacao ?: 0 },
      CampoString("Vencimento") { vencimento.format() },
      CampoInt("Meses Venc") { mesesVencimento ?: 0 },
      CampoInt("Entrada") { entrada ?: 0 },
      CampoInt("Saldo") { saldo ?: 0 },
      CampoInt("Meses Estoque") { estoque ?: 0 },
    )

  fun grava(listaProdutos: List<ValidadeEntrada>): ByteArray {
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

