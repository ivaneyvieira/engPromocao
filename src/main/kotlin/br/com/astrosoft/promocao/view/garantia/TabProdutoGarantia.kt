package br.com.astrosoft.promocao.view.garantia

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.promocao.model.beans.ProdutoValidade
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.garantiaCentroLucro
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.garantiaClno
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.garantiaCodigo
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.garantiaDescicao
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.garantiaFornecedor
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.garantiaMeseValidade
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.garantiaTipoNo
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.garantiaTipoProduto
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.garantiaTipoValidade
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.garantiaVendno
import br.com.astrosoft.promocao.viewmodel.garantia.ITabProdutoGarantiaViewModel
import br.com.astrosoft.promocao.viewmodel.garantia.TabProdutoGarantiaViewModel
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode

class TabProdutoGarantia(val viewModel: TabProdutoGarantiaViewModel) :
        TabPanelGrid<ProdutoValidade>(ProdutoValidade::class), ITabProdutoGarantiaViewModel {
  private lateinit var edtFiltro: TextField

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun listSelected(): List<ProdutoValidade> {
    return itensSelecionados()
  }

  override fun HorizontalLayout.toolBarConfig() {
    edtFiltro = textField ("Filtro") {
      this.width = "250px"
      this.valueChangeMode = ValueChangeMode.TIMEOUT
      this.addValueChangeListener {
        viewModel.updateView()
      }
    }
  }

  override fun isAuthorized(user: IUser) = true
  override val label: String
    get() = "Inserir"

  override fun filtro() = edtFiltro.value ?: ""

  override fun Grid<ProdutoValidade>.gridPanel() {
    garantiaCodigo()
    garantiaDescicao()
    garantiaClno()
    garantiaCentroLucro()
    garantiaVendno()
    garantiaFornecedor()
    garantiaTipoNo()
    garantiaTipoProduto()
    garantiaTipoValidade()
    garantiaMeseValidade()

  }
}


