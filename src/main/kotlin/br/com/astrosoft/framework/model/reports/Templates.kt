package br.com.astrosoft.framework.model.reports

import net.sf.dynamicreports.report.builder.DynamicReports.stl
import net.sf.dynamicreports.report.builder.DynamicReports.template
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder
import net.sf.dynamicreports.report.builder.style.StyleBuilder
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment.CENTER
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment.LEFT
import net.sf.dynamicreports.report.constant.PageOrientation.LANDSCAPE
import net.sf.dynamicreports.report.constant.PageType.A4
import net.sf.dynamicreports.report.constant.VerticalTextAlignment.MIDDLE
import java.awt.Color

object Templates {
  val rootStyle: StyleBuilder = stl.style().setPadding(1)
  fun columnStyle(fontSize: Int): StyleBuilder = stl.style(rootStyle).setFontSize(fontSize)
  fun columnTitleStyle(fontSize: Int): StyleBuilder =
      stl
        .style(columnStyle(fontSize))
        .setBorder(stl.pen1Point())
        .setHorizontalTextAlignment(CENTER)
        .setBackgroundColor(Color.LIGHT_GRAY)
        .bold()
  val groupStyle: StyleBuilder = stl.style().setHorizontalTextAlignment(LEFT).bold()
  val subtotalStyle: StyleBuilder = stl.style().bold()
  fun reportTemplate(fontSize: Int): ReportTemplateBuilder =
      template()
        .setPageFormat(A4, LANDSCAPE)
        .setColumnStyle(columnStyle(fontSize))
        .setColumnTitleStyle(columnTitleStyle(fontSize))
        .setGroupStyle(groupStyle)
        .setGroupTitleStyle(groupStyle)
        .setSubtotalStyle(subtotalStyle)
        .setDetailStyle(stl.style(rootStyle).setFontSize(fontSize))
}