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
            CampoInt(headerMes(1)) { venda00 ?: 1 },
            CampoInt(headerMes(2)) { venda00 ?: 2 },
            CampoInt(headerMes(3)) { venda00 ?: 3 },
            CampoInt(headerMes(4)) { venda00 ?: 4 },
            CampoInt(headerMes(5)) { venda00 ?: 5 },
            CampoInt(headerMes(6)) { venda00 ?: 6 },
            CampoInt(headerMes(7)) { venda00 ?: 7 },
            CampoInt(headerMes(8)) { venda00 ?: 8 },
            CampoInt(headerMes(9)) { venda00 ?: 9 },
            CampoInt(headerMes(10)) { venda00 ?: 10 },
            CampoInt(headerMes(11)) { venda00 ?: 11 },
            CampoInt(headerMes(12)) { venda00 ?: 12 },
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

