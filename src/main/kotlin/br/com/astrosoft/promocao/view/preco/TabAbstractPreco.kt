package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.preco.view.preco.columns.NotaNddViewColumns.precoCentroLucro
import br.com.astrosoft.preco.view.preco.columns.NotaNddViewColumns.precoCodigo
import br.com.astrosoft.preco.view.preco.columns.NotaNddViewColumns.precoDesconto
import br.com.astrosoft.preco.view.preco.columns.NotaNddViewColumns.precoDescricao
import br.com.astrosoft.preco.view.preco.columns.NotaNddViewColumns.precoFornecedor
import br.com.astrosoft.preco.view.preco.columns.NotaNddViewColumns.precoPrecoPromocional
import br.com.astrosoft.preco.view.preco.columns.NotaNddViewColumns.precoPrecoRef
import br.com.astrosoft.preco.view.preco.columns.NotaNddViewColumns.precoTipoProduto
import br.com.astrosoft.preco.view.preco.columns.NotaNddViewColumns.precoValidade
import br.com.astrosoft.preco.view.preco.columns.NotaNddViewColumns.precoVendno
import br.com.astrosoft.promocao.model.beans.FiltroPrecoPromocao
import br.com.astrosoft.promocao.model.beans.PrecoPromocao
import br.com.astrosoft.promocao.model.planilhas.PlanilhaPromocao
import br.com.astrosoft.promocao.viewmodel.preco.ITabAbstractPrecoViewModel
import br.com.astrosoft.promocao.viewmodel.preco.TabAbstractPrecoViewModel
import com.github.mvysny.karibudsl.v10.integerField
import com.github.mvysny.karibudsl.v10.tooltip
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.data.value.ValueChangeMode.TIMEOUT
import org.vaadin.stefan.LazyDownloadButton
import java.io.ByteArrayInputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class TabAbstractPreco<T : ITabAbstractPrecoViewModel>(open val viewModel: TabAbstractPrecoViewModel<T>) :
        TabPanelGrid<PrecoPromocao>(PrecoPromocao::class), ITabAbstractPrecoViewModel {
  private lateinit var edtCodigo: IntegerField
  private lateinit var edtVend: IntegerField
  private lateinit var edtCl: IntegerField
  private lateinit var edtType: IntegerField

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun listSelected(): List<PrecoPromocao> {
    return itensSelecionados()
  }

  override fun HorizontalLayout.toolBarConfig() {
    edtCodigo = integerField("Código") {
      this.valueChangeMode = TIMEOUT
      addValueChangeListener {
        viewModel.updateView()
      }
    }

    edtVend = integerField("Fornecedor") {
      this.valueChangeMode = TIMEOUT
      addValueChangeListener {
        viewModel.updateView()
      }
    }

    edtCl = integerField("Centro de Lucro") {
      this.valueChangeMode = TIMEOUT
      addValueChangeListener {
        viewModel.updateView()
      }
    }

    edtType = integerField("Tipo de produto") {
      this.valueChangeMode = TIMEOUT
      addValueChangeListener {
        viewModel.updateView()
      }
    }

    this.downloadExcel()

    addAditionaisFields()
  }

  private fun HasComponents.downloadExcel() {
    val button = LazyDownloadButton(VaadinIcon.TABLE.create(), { filename() }, {
      val planilha = PlanilhaPromocao()
      val bytes = planilha.grava(listBeans())
      ByteArrayInputStream(bytes)
    })
    button.addThemeVariants(ButtonVariant.LUMO_SMALL)
    button.tooltip = "Salva a planilha"
    add(button)
  }

  private fun filename(): String {
    val sdf = DateTimeFormatter.ofPattern("yyMMddHHmmss")
    val textTime = LocalDateTime.now().format(sdf)
    val filename = "promocao$textTime.xlsx"
    return filename
  }

  protected abstract fun HorizontalLayout.addAditionaisFields()

  override fun isAuthorized(user: IUser) = true

  override fun filtro() = FiltroPrecoPromocao(codigo = edtCodigo.value ?: 0,
                                              vendno = edtVend.value ?: 0,
                                              clno = edtCl.value ?: 0,
                                              typeno = edtType.value ?: 0,
                                              tipoLista = viewModel.tipoTab,
                                              decimal99 = "N")

  override fun Grid<PrecoPromocao>.gridPanel() {
    setSelectionMode(Grid.SelectionMode.MULTI)

    precoCodigo()
    precoDescricao()
    precoPrecoRef()
    precoPrecoPromocional()
    precoDesconto()
    precoValidade()
    precoVendno()
    precoFornecedor()
    precoTipoProduto()
    precoCentroLucro()
  }
}


