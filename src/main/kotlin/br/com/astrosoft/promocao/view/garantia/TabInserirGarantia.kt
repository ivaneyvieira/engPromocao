package br.com.astrosoft.promocao.view.garantia

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.showQuestion
import br.com.astrosoft.promocao.model.beans.*
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
import br.com.astrosoft.promocao.viewmodel.garantia.ITabInserirGarantiaViewModel
import br.com.astrosoft.promocao.viewmodel.garantia.TabInserirGarantiaViewModel
import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.textfield.TextFieldVariant
import com.vaadin.flow.data.value.ValueChangeMode

class TabInserirGarantia(val viewModel: TabInserirGarantiaViewModel) :
        TabPanelGrid<ProdutoValidade>(ProdutoValidade::class), ITabInserirGarantiaViewModel {
  private lateinit var edtTipoValidade: Select<ETipoValidade>
  private lateinit var edtCodigo: TextField
  private lateinit var edtVendno: TextField
  private lateinit var edtTypeno: TextField
  private lateinit var edtClno: TextField

  private lateinit var cmbTipoValidade: Select<ETipoValidade>
  private lateinit var cmbRegistroValidade: Select<ERegistroValidade>
  private lateinit var edtValidade: IntegerField

  override fun updateComponent() {
    viewModel.updateView()
  }

  override fun listSelected(): List<ProdutoValidade> {
    return itensSelecionados()
  }

  override fun infoModifica(): InfoModifica {
    return InfoModifica(tipo = cmbTipoValidade.value,
                        validade = edtValidade.value ?: 0,
                        registro = cmbRegistroValidade.value)
  }

  override fun HorizontalLayout.toolBarConfig() {
    verticalLayout {
      this.isSpacing = false
      this.isMargin = false
      horizontalLayout {
        edtTipoValidade = select("Tempo") {
          this.setItems(ETipoValidade.values().toList())
          this.value = ETipoValidade.TODOS
          this.width = "125px"
          this.setItemLabelGenerator {
            it.descricao
          }

          this.addValueChangeListener {
            viewModel.updateView()
          }
        }

        edtCodigo = textField("Código") {
          this.valueChangeMode = ValueChangeMode.TIMEOUT
          this.addValueChangeListener {
            viewModel.updateView()
          }
        }

        edtVendno = textField("Fornecedor") {
          this.valueChangeMode = ValueChangeMode.TIMEOUT
          this.addValueChangeListener {
            viewModel.updateView()
          }
        }

        edtTypeno = textField("Tipo") {
          this.valueChangeMode = ValueChangeMode.TIMEOUT
          this.addValueChangeListener {
            viewModel.updateView()
          }
        }

        edtClno = textField("CL") {
          this.valueChangeMode = ValueChangeMode.TIMEOUT
          this.addValueChangeListener {
            viewModel.updateView()
          }
        }
      }
      horizontalLayout {
        cmbTipoValidade = select("Tipo") {
          this.setItems(ETipoValidade.values().toList())
          this.value = ETipoValidade.MES
          this.setItemLabelGenerator {
            it.descricao
          }
        }
        cmbRegistroValidade = select("Salva") {
          this.setItems(ERegistroValidade.values().toList())
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
            showQuestion("Confirma o processamento?") {
              viewModel.modificaValidade()
            }
          }
        }
      }
    }
  }

  override fun isAuthorized(user: IUser) = (user as? UserSaci)?.garantiaInserir ?: false
  override val label: String
    get() = "Inserir"

  override fun filtro() = FiltroGarantia(
    tipoValidade = edtTipoValidade.value ?: ETipoValidade.TODOS,
    codigo = edtCodigo.value ?: "",
    vendno = edtVendno.value ?: "",
    typeno = edtTypeno.value ?: "",
    clno = edtClno.value ?: "",
                                        )

  override fun Grid<ProdutoValidade>.gridPanel() {
    this.setSelectionMode(Grid.SelectionMode.MULTI)
    produtoCodigo()
    produtoDescicao()
    produtoDescricaoCompleta1()
    produtoDescricaoCompleta2()
    produtoClno() //produtoCentroLucro()
    produtoVendno()
    produtoFornecedor()
    produtoTipoNo()
    produtoTipoProduto()
    produtoTipoValidade()
    produtoMeseValidade()
  }
}


