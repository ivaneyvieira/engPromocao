package br.com.astrosoft.promocao.reports

import br.com.astrosoft.framework.model.reports.PropriedadeRelatorio
import br.com.astrosoft.framework.model.reports.ReportBuild
import br.com.astrosoft.promocao.model.beans.Produtos
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder
import net.sf.dynamicreports.report.builder.DynamicReports
import net.sf.dynamicreports.report.builder.style.Styles
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment.*
import net.sf.dynamicreports.report.constant.PageOrientation
import java.awt.Color

class RelatorioProduto : ReportBuild<Produtos>() {
  init {
    columnReport(Produtos::codigo, header = "Código", aligment = RIGHT, width = 50)
    columnReport(Produtos::descricao, header = "Descrição", aligment = LEFT)
    columnReport(Produtos::grade, header = "Grade", aligment = CENTER, width = 50)
    columnReport(Produtos::unidade, header = "Unidade", aligment = CENTER, width = 50)
    columnReport(Produtos::DS_TT, header = "Quant", aligment = RIGHT, width = 50)
  }

  override fun makeReport(itens: List<Produtos>): JasperReportBuilder {
    return super
      .makeReport(itens)
      .setPageMargin(DynamicReports.margin(0))
      .setTitleStyle(DynamicReports.stl.style().setForegroundColor(Color.WHITE).setPadding(Styles.padding().setTop(20)))
      .setColumnStyle(DynamicReports.stl.style().setForegroundColor(Color.WHITE).setFontSize(8).setLeftPadding(3).setRightPadding(3))
      .setGroupStyle(DynamicReports.stl.style().setForegroundColor(Color.WHITE).setPadding(Styles.padding().setLeft(4)))
      .setBackgroundStyle(DynamicReports.stl.style().setBackgroundColor(Color(35, 51, 72)))
  }

  override fun config(itens: List<Produtos>): PropriedadeRelatorio {
    return PropriedadeRelatorio(
      titulo = "Estoque Loja DS",
      subTitulo = "",
      color = Color.WHITE,
      detailFonteSize = 8,
      pageOrientation = PageOrientation.PORTRAIT
    )
  }
}