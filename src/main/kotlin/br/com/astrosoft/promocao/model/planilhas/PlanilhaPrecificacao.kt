package br.com.astrosoft.promocao.model.planilhas

import br.com.astrosoft.framework.model.Campo
import br.com.astrosoft.framework.model.CampoInt
import br.com.astrosoft.framework.model.CampoNumber
import br.com.astrosoft.framework.model.CampoString
import br.com.astrosoft.promocao.model.beans.Precificacao
import com.github.nwillc.poink.workbook
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment

class PlanilhaPrecificacao {
  private val campos: List<Campo<*, Precificacao>> =
    listOf(
      CampoString("Cod") { codigo },
      CampoString("Descrição") { descricao },
      CampoInt("Cod For") { vendno },
      CampoString("NCM") { ncm },
      CampoInt("Tipo") { typeno },
      CampoInt("CL") { clno },
      CampoString("Trib") { tributacao },
      CampoNumber("MVA") { mvap ?: 0.00 },
      CampoNumber("ICMS Ent") { creditoICMS ?: 0.00 },
      CampoNumber("P. Fab") { pcfabrica ?: 0.00 },
      CampoNumber("IPI") { ipi ?: 0.00 },
      CampoNumber("Emb") { embalagem ?: 0.00 },
      CampoNumber("IR ST") { retido ?: 0.00 },
      CampoNumber("C. ICMS") { icmsp ?: 0.00 },
      CampoNumber("Frete") { frete ?: 0.00 },
      CampoNumber("C.Cont") { custoContabil ?: 0.00 },
      CampoNumber("ICM Sai") { icms ?: 0.00 },
      CampoNumber("FCP") { fcp },
      CampoNumber("Pis") { pis ?: 0.00 },
      CampoNumber("IR") { ir ?: 0.00 },
      CampoNumber("CS") { contrib ?: 0.00 },
      CampoNumber("CPMF") { cpmf },
      CampoNumber("Desp") { fixa ?: 0.00 },
      CampoNumber("Out") { outras ?: 0.00 },
      CampoNumber("Lucro") { lucroLiq ?: 0.00 },
      CampoNumber("P.Sug.") { precoSug ?: 0.00 },
      CampoNumber("P.Ref.") { precoRef ?: 0.00 },
          )

  fun grava(listaProdutos: List<Precificacao>): ByteArray {
    val wb = workbook {
      val headerStyle = cellStyle("Header") {
        fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
        fillPattern = FillPatternType.SOLID_FOREGROUND
        this.verticalAlignment = VerticalAlignment.TOP
      }
      val rowStyle = cellStyle("Row") {
        this.verticalAlignment = VerticalAlignment.TOP
      }
      val stSemGrade = sheet("Precificacao") {
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

