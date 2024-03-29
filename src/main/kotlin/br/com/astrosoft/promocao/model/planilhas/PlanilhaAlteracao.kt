package br.com.astrosoft.promocao.model.planilhas

import br.com.astrosoft.framework.model.Campo
import br.com.astrosoft.framework.model.CampoNumber
import br.com.astrosoft.framework.model.CampoString
import br.com.astrosoft.framework.util.format
import br.com.astrosoft.promocao.model.beans.PrecoAlteracao
import com.github.nwillc.poink.workbook
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment

class PlanilhaAlteracao {
  private val campos: List<Campo<*, PrecoAlteracao>> =
    listOf(
      CampoString("Cod") { codigo },
      CampoString("Descrição") { descricao },
      CampoNumber("M Piso") { metroCaixa ?: 0.00 },
      CampoNumber("R\$ Ref.") { refprice ?: 0.00 },
      CampoNumber("R\$ Ref M2") { refpriceM ?: 0.00 },
      CampoNumber("R\$ Promo.") { promo_price ?: 0.00 },
      CampoNumber("R\$ Promo M2") { promo_priceM ?: 0.00 },
      CampoString("Validade") { dataPromocao.format() },
      CampoNumber("% Perc") { desconto ?: 0.00 },
      CampoString("Forn") { vendno.toString() },
      CampoString("Abrev") { fornecedor },
      CampoString("Tipo") { typeno.toString() },
      CampoString("Centro Lucro") { clno.toString() },
      CampoString("Usuário") { usuario ?: "" },
      CampoString("Data") { data.format() },
      CampoString("Hora") { time.format() },
    )

  fun grava(listaProdutos: List<PrecoAlteracao>): ByteArray {
    val wb = workbook {
      val headerStyle = cellStyle("Header") {
        fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
        fillPattern = FillPatternType.SOLID_FOREGROUND
        this.verticalAlignment = VerticalAlignment.TOP
      }
      val rowStyle = cellStyle("Row") {
        this.verticalAlignment = VerticalAlignment.TOP
      }
      val stSemGrade = sheet("Preços VTEX") {
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

