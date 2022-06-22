package br.com.astrosoft.promocao.view.garantia

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.promocao.model.beans.ERegistroValidade
import br.com.astrosoft.promocao.model.beans.ETipoValidade
import br.com.astrosoft.promocao.model.beans.InfoModifica
import br.com.astrosoft.promocao.model.beans.ProdutoValidade
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoCentroLucro
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoClno
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoCodigo
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoDescicao
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoDescricaoCompleta1
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoDescricaoCompleta2
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoFornecedor
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoMeseValidade
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoTipoNo
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoTipoProduto
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoTipoValidade
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoVendno
import br.com.astrosoft.promocao.viewmodel.garantia.ITabProdutoGarantiaViewModel
import br.com.astrosoft.promocao.viewmodel.garantia.TabProdutoGarantiaViewModel
import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.textfield.TextFieldVariant
import com.vaadin.flow.data.value.ValueChangeMode

class TabProdutoGarantia(val viewModel: TabProdutoGarantiaViewModel) :
        TabPanelGrid<ProdutoValidade>(ProdutoValidade::class), ITabProdutoGarantiaViewModel {
  private lateinit var edtFiltro: TextField
  private lateinit var cmbTipoValidade: ComboBox<ETipoValidade>
  private lateinit var cmbRegistroValidade: ComboBox<ERegistroValidade>
  private lateinit var edtValidade: IntegerField

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun listSelected(): List<ProdutoValidade> {
    return itensSelecionados()
  }

  override fun infoModifica(): InfoModifica {
    return InfoModifica(
      tipo     = cmbTipoValidade.value,
      validade = edtValidade.value ?: 0,
      registro = cmbRegistroValidade.value
                       )
  }

  override fun HorizontalLayout.toolBarConfig() {
    edtFiltro = textField ("Filtro") {
      this.width = "250px"
      this.valueChangeMode = ValueChangeMode.TIMEOUT
      this.addValueChangeListener {
        viewModel.updateView()
      }
    }
    cmbTipoValidade = comboBox("Tipo") {
      this.setItems(ETipoValidade.values().toList())
      this.isAutoOpen = true
      this.isAllowCustomValue = false
      this.value = ETipoValidade.MES
      this.setItemLabelGenerator {
        it.descricao
      }
    }
    cmbRegistroValidade = comboBox("Salva") {
      this.setItems(ERegistroValidade.values().toList())
      this.isAutoOpen = true
      this.isAllowCustomValue = false
      this.value = ERegistroValidade.CADASTRO
      this.setItemLabelGenerator {
        it.descricao
      }
    }
    edtValidade = integerField("Validade") {
      this.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT)
    }
    button("Modifica validade") {
      icon = VaadinIcon.PLUS_CIRCLE.create()
      onLeftClick {
        viewModel.modificaValidade()
      }
    }
  }

  override fun isAuthorized(user: IUser) = true
  override val label: String
    get() = "Inserir"

  override fun filtro() = edtFiltro.value ?: ""

  override fun Grid<ProdutoValidade>.gridPanel() {
    this.setSelectionMode(Grid.SelectionMode.MULTI)
    produtoCodigo()
    produtoDescicao()
    produtoDescricaoCompleta1()
    produtoDescricaoCompleta2()
    produtoClno()
    //produtoCentroLucro()
    produtoVendno()
    produtoFornecedor()
    produtoTipoNo()
    produtoTipoProduto()
    produtoTipoValidade()
    produtoMeseValidade()
  }
}


