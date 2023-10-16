package br.com.astrosoft.promocao.model.planilhas

import br.com.astrosoft.framework.model.Campo
import br.com.astrosoft.framework.model.CampoInt
import br.com.astrosoft.framework.model.CampoString
import br.com.astrosoft.framework.util.format
import br.com.astrosoft.promocao.model.beans.VendaEntrada
import com.github.nwillc.poink.workbook
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment
import java.time.LocalDate

class PlanilhaGarantiaVenda {
  private fun headerMes(num: Int): String {
    val dataMes = LocalDate.now().withDayOfMonth(1).minusMonths(num.toLong())
    return dataMes.format("MM/yy")
  }

  private val campos: List<Campo<*, VendaEntrada>> =
    listOf(
      CampoInt("Loja") { loja ?: 0 },
      CampoInt("Código") { codigo ?: 0 },
      CampoString("Descrição") { descricao ?: "" },
      CampoString("Grade") { grade ?: "" },
      CampoString("Loc") { localizacao ?: "" },
      CampoInt("Estoque") { estoque ?: 0 },
      CampoInt("M. Venc") { mesesVencimento ?: 0 },
      CampoInt(headerMes(0)) { venda00 ?: 0 },
      CampoInt(headerMes(1)) { venda01 ?: 0 },
      CampoInt(headerMes(2)) { venda02 ?: 0 },
      CampoInt(headerMes(3)) { venda03 ?: 0 },
      CampoInt(headerMes(4)) { venda04 ?: 0 },
      CampoInt(headerMes(5)) { venda05 ?: 0 },
      CampoInt(headerMes(6)) { venda06 ?: 0 },
      CampoInt(headerMes(7)) { venda07 ?: 0 },
      CampoInt(headerMes(8)) { venda08 ?: 0 },
      CampoInt(headerMes(9)) { venda09 ?: 0 },
      CampoInt(headerMes(10)) { venda10 ?: 0 },
      CampoInt(headerMes(11)) { venda11 ?: 0 },
      CampoInt(headerMes(12)) { venda12 ?: 0 },
    )

  fun grava(listaProdutos: List<VendaEntrada>): ByteArray {
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

