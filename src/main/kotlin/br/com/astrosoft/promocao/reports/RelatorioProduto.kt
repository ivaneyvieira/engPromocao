package br.com.astrosoft.promocao.reports

import br.com.astrosoft.framework.model.reports.PropriedadeRelatorio
import br.com.astrosoft.framework.model.reports.ReportBuild
import br.com.astrosoft.promocao.model.beans.Produtos
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder
import net.sf.dynamicreports.report.builder.DynamicReports.*
import net.sf.dynamicreports.report.builder.style.Styles
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment.*
import net.sf.dynamicreports.report.constant.PageOrientation
import java.awt.Color

class RelatorioProduto : ReportBuild<Produtos>() {
  init {
    columnReport(Produtos::codigo, header = "Código", aligment = RIGHT, width = 80)
    columnReport(Produtos::descricao, header = "Descrição", aligment = LEFT)
    columnReport(Produtos::grade, header = "Grade", aligment = CENTER, width = 100)
    columnReport(Produtos::unidade, header = "Unidade", aligment = CENTER, width = 80)
    columnReport(Produtos::DS_TT, header = "Quant", aligment = RIGHT, width = 80)
  }

  override fun makeReport(itens: List<Produtos>): JasperReportBuilder {
    return super
      .makeReport(itens)
      .setPageMargin(margin(0))
      .setColumnHeaderStyle(
        stl.style().setForegroundColor(Color.WHITE).setLeftPadding(8).setRightPadding(8)
      )
      .setTitleStyle(
        stl.style().setForegroundColor(Color.WHITE).setPadding(Styles.padding().setTop(20))
      )
      .setColumnStyle(
        stl.style().setForegroundColor(Color.WHITE).setLeftPadding(8).setRightPadding(8)
      )
      .setGroupStyle(stl.style().setForegroundColor(Color.WHITE).setPadding(Styles.padding().setLeft(8)))
      .setBackgroundStyle(stl.style().setBackgroundColor(Color(35, 51, 72)))
  }

  override fun config(itens: List<Produtos>): PropriedadeRelatorio {
    return PropriedadeRelatorio(
      titulo = "Estoque Loja DS",
      subTitulo = "",
      color = Color.WHITE,
      fonteSize = 14,
      pageOrientation = PageOrientation.PORTRAIT
    )
  }
}