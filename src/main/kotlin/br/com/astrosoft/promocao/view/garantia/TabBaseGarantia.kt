package br.com.astrosoft.promocao.view.garantia

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.promocao.model.beans.ComparaValidade
import br.com.astrosoft.promocao.model.beans.ETipoDiferencaGarantia
import br.com.astrosoft.promocao.model.beans.FiltroValidade
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaCodigo
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaDescicao
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaDiferenca
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaEstoque
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaGrade
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaLocalizacao
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaTipoDiferenca
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaValidadeCad
import br.com.astrosoft.promocao.view.garantia.columns.GarantiaDiferenca.garantiaValidadeDesc
import br.com.astrosoft.promocao.viewmodel.garantia.ITabBaseGarantiaViewModel
import br.com.astrosoft.promocao.viewmodel.garantia.TabBaseGarantiaViewModel
import com.github.mvysny.karibudsl.v10.select
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode

class TabBaseGarantia(val viewModel: TabBaseGarantiaViewModel) : TabPanelGrid<ComparaValidade>(ComparaValidade::class),
  ITabBaseGarantiaViewModel {
  private lateinit var cmbTipoGarantia: Select<ETipoDiferencaGarantia>
  private lateinit var edtQuery: TextField

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun listSelected(): List<ComparaValidade> {
    return itensSelecionados()
  }

  override fun HorizontalLayout.toolBarConfig() {
    cmbTipoGarantia = select("Tipo Diferença") {
      this.setItems(ETipoDiferencaGarantia.values().toList())
      this.value = ETipoDiferencaGarantia.TODOS
      this.width = "250px"
      this.setItemLabelGenerator {
        it.descricao
      }

      this.addValueChangeListener {
        viewModel.updateView()
      }
    }

    edtQuery = textField("Filtro") {
      this.valueChangeMode = ValueChangeMode.TIMEOUT
      this.addValueChangeListener {
        viewModel.updateView()
      }
    }
  }

  override fun isAuthorized(user: IUser) = (user as? UserSaci)?.garantiaBase ?: false
  override val label: String
    get() = "Base"

  override fun filtro() =
    FiltroValidade(tipo = cmbTipoGarantia.value ?: ETipoDiferencaGarantia.TODOS, query = edtQuery.value)

  override fun Grid<ComparaValidade>.gridPanel() {
    garantiaCodigo()
    garantiaDescicao()
    garantiaGrade()
    garantiaLocalizacao()
    garantiaEstoque()
    garantiaValidadeDesc()
    garantiaValidadeCad()
    garantiaDiferenca()
    garantiaTipoDiferenca()
  }
}


