package br.com.astrosoft.promocao.model.planilhas

import br.com.astrosoft.framework.model.Campo
import br.com.astrosoft.promocao.model.beans.Produtos
import com.github.nwillc.poink.workbook
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment

abstract class PlanilhaProduto {
    abstract val campos: List<Campo<*, Produtos>>

    fun grava(listaProdutos: List<Produtos>): ByteArray {
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

