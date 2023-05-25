package br.com.astrosoft.promocao.model.planilhas

import br.com.astrosoft.framework.model.Campo
import br.com.astrosoft.framework.model.CampoNumber
import br.com.astrosoft.framework.model.CampoString
import br.com.astrosoft.framework.util.format
import br.com.astrosoft.promocao.model.beans.PrecoPromocao
import com.github.nwillc.poink.workbook
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment

class PlanilhaPromocao {
    private val campos: List<Campo<*, PrecoPromocao>> =
        listOf(
            CampoString("Cod") { codigo },
            CampoString("Descrição") { descricao },
            CampoString("Validade") { validade.format() },
            CampoNumber("R\$ Ref.") { refPrice },
            CampoNumber("% Perc") { desconto ?: 0.00 },
            CampoNumber("R\$ Promo") { precoPromocional ?: 0.00 },
            CampoString("Forn") { vendno.toString() },
            CampoString("Abrev") { fornecedor },
            CampoString("Tipo") { typeno.toString() },
            CampoString("Centro Lucro") { clno.toString() },
        )

    fun grava(listaProdutos: List<PrecoPromocao>): ByteArray {
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

