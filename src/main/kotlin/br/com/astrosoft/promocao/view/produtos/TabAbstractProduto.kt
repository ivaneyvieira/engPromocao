package br.com.astrosoft.promocao.view.produtos

import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.promocao.model.beans.FiltroProduto
import br.com.astrosoft.promocao.model.beans.Produtos
import br.com.astrosoft.promocao.viewmodel.produto.ITabAbstractProdutoViewModel
import br.com.astrosoft.promocao.viewmodel.produto.TabAbstractProdutoViewModel
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode.LAZY
import com.vaadin.flow.data.value.ValueChangeMode.TIMEOUT
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class TabAbstractProduto<T : ITabAbstractProdutoViewModel>(open val viewModel: TabAbstractProdutoViewModel<T>,
                                                                val showDatas: Boolean = true) :
        TabPanelGrid<Produtos>(Produtos::class), ITabAbstractProdutoViewModel {
  private lateinit var edtPesquisa: TextField

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun HorizontalLayout.toolBarConfig() {
    edtPesquisa = textField("Pesquisa") {
      this.valueChangeMode = LAZY
      addValueChangeListener {
        viewModel.updateView()
      }
    }
    this.downloadExcel()

    addAditionaisFields()
  }

  private fun HasComponents.downloadExcel() {
    // TODO
  }

  private fun filename(): String {
    val sdf = DateTimeFormatter.ofPattern("yyMMddHHmmss")
    val textTime = LocalDateTime.now().format(sdf)
    val filename = "promocao$textTime.xlsx"
    return filename
  }

  protected abstract fun HorizontalLayout.addAditionaisFields()

  override fun filtro() = FiltroProduto(pesquisa = edtPesquisa.value ?: "")

  override fun Grid<Produtos>.gridPanel() {
    setSelectionMode(Grid.SelectionMode.MULTI)
    this.colunasGrid()
  }

  abstract fun Grid<Produtos>.colunasGrid()
}


