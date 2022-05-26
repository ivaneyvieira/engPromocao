package br.com.astrosoft.promocao.view.garantia

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.promocao.model.beans.ComparaValidade
import br.com.astrosoft.promocao.model.beans.ETipoDiferencaGarantia
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaCodigo
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaDescicao
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaDiferenca
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaTipoDiferenca
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaValidadeCad
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaValidadeDesc
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoCentroLucro
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoCodigo
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoDesconto
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoDescricao
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoFornecedor
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoPrecoPromocional
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoPrecoRef
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoTipoProduto
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoValidade
import br.com.astrosoft.promocao.view.promocao.columns.NotaNddViewColumns.promocaoVendno
import br.com.astrosoft.promocao.viewmodel.garantia.ITabBaseGarantiaViewModel
import br.com.astrosoft.promocao.viewmodel.garantia.TabBaseGarantiaViewModel
import br.com.astrosoft.promocao.viewmodel.promocao.*
import com.github.mvysny.karibudsl.v10.comboBox
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class TabBaseGarantia(val viewModel: TabBaseGarantiaViewModel) :
        TabPanelGrid<ComparaValidade>(ComparaValidade::class), ITabBaseGarantiaViewModel {
  private lateinit var cmbTipoGarantia : ComboBox<ETipoDiferencaGarantia>

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun listSelected(): List<ComparaValidade> {
    return itensSelecionados()
  }

  override fun HorizontalLayout.toolBarConfig() {
    cmbTipoGarantia = comboBox("Tipo Diferença") {
      this.setItems(ETipoDiferencaGarantia.values().toList())
      this.isAutoOpen = true
      this.isAllowCustomValue = false

      this.addValueChangeListener {
        viewModel.updateView()
      }
    }
  }

  override fun isAuthorized(user: IUser) = true
  override val label: String
    get() = "Base"

  override fun filtro() = cmbTipoGarantia.value ?: ETipoDiferencaGarantia.TODOS

  override fun Grid<ComparaValidade>.gridPanel() {
    garantiaCodigo()
    garantiaDescicao()
    garantiaValidadeDesc()
    garantiaValidadeCad()
    garantiaDiferenca()
    garantiaTipoDiferenca()
  }
}


