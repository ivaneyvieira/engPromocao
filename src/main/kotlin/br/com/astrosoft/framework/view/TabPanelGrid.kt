package br.com.astrosoft.framework.view

import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.github.mvysny.karibudsl.v10.isExpand
import com.github.mvysny.kaributools.fetchAll
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.Text
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant.LUMO_COMPACT
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.NotificationVariant
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.data.provider.ListDataProvider
import kotlin.reflect.KClass

abstract class TabPanelGrid<T : Any>(classGrid: KClass<T>) : VerticalLayout() {
  private val dataProviderPanel = ListDataProvider<T>(mutableListOf())
  private val gridPanel: Grid<T> = Grid(classGrid.java, false)
  protected abstract fun HorizontalLayout.toolBarConfig()
  protected abstract fun Grid<T>.gridPanel()

  fun createTabComponent(): Component {
    this.setSizeFull()
    isMargin = false
    isPadding = false
    horizontalLayout {
      setWidthFull()
      toolBarConfig()
    }

    gridPanel.apply {
      this.dataProvider = dataProviderPanel
      isExpand = true
      isMultiSort = true
      addThemeVariants(LUMO_COMPACT)
      gridPanel()
    }
    addAndExpand(gridPanel)
    return this
  }

  fun updateGrid(itens: List<T>) {
    gridPanel.deselectAll()
    dataProviderPanel.updateItens(itens)
  }

  fun listBeans() = dataProviderPanel.fetchAll()

  fun itensSelecionados() = gridPanel.selectedItems.toList()

  fun showErro(msg: String?) {
    val notification = Notification()
    notification.addThemeVariants(NotificationVariant.LUMO_ERROR)

    val text = Div(Text(msg))

    val closeButton = Button(Icon("lumo", "cross"))
    closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE)
    closeButton.element.setAttribute("aria-label", "Close")
    closeButton.addClickListener { event -> notification.close() }

    val layout = HorizontalLayout(text, closeButton)
    layout.alignItems = FlexComponent.Alignment.CENTER

    notification.add(layout)
    notification.open()
  }
}