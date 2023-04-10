package br.com.astrosoft.promocao.view.garantia

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.MessageUtils.showQuestion
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.promocao.model.beans.*
import br.com.astrosoft.promocao.model.planilhas.PlanilhaProdutoValidade
import br.com.astrosoft.promocao.model.planilhas.PlanilhaValidade
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoClno
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoCodigo
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoDescricao
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoDescricaoCompleta2
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoEstoque
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoFornecedor
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoGrade
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoMeseValidade
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoTipoNo
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoTipoProduto
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoTipoValidade
import br.com.astrosoft.promocao.view.garantia.columns.ProdutoValidadeCol.produtoVendno
import br.com.astrosoft.promocao.viewmodel.garantia.ITabInserirGarantiaViewModel
import br.com.astrosoft.promocao.viewmodel.garantia.TabInserirGarantiaViewModel
import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.kaributools.tooltip
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.textfield.TextFieldVariant
import com.vaadin.flow.data.value.ValueChangeMode
import org.vaadin.stefan.LazyDownloadButton
import java.io.ByteArrayInputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TabInserirGarantia(val viewModel: TabInserirGarantiaViewModel) :
  TabPanelGrid<ProdutoValidade>(ProdutoValidade::class), ITabInserirGarantiaViewModel {
  private lateinit var edtTipoDiferenca: Select<ETipoDiferencaGarantiaSimples>
  private lateinit var edtTipoValidade: Select<ETipoValidade>
  private lateinit var edtFiltro: TextField

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
    return InfoModifica(
      tipo = cmbTipoValidade.value,
      validade = edtValidade.value ?: 0,
      registro = cmbRegistroValidade.value
    )
  }

  override fun HorizontalLayout.toolBarConfig() {
    verticalLayout {
      this.isSpacing = false
      this.isMargin = false
      horizontalLayout {
        edtTipoDiferenca = select("Cad x Des") {
          this.setItems(ETipoDiferencaGarantiaSimples.values().toList())
          this.value = ETipoDiferencaGarantiaSimples.TODOS
          this.width = "125px"
          this.setItemLabelGenerator {
            it.descricao
          }

          this.addValueChangeListener {
            viewModel.updateView()
          }
        }

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

        edtFiltro = textField("Filtro") {
          this.valueChangeMode = ValueChangeMode.TIMEOUT
          this.width = "500px"
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
        downloadExcel()
      }
    }
  }

  private fun HasComponents.downloadExcel() {
    val button = LazyDownloadButton(VaadinIcon.TABLE.create(), { filename() }, {
      val planilha = PlanilhaProdutoValidade()
      val list = itensSelecionados()
      val bytes = planilha.grava(list)
      ByteArrayInputStream(bytes)
    })
    button.addThemeVariants(ButtonVariant.LUMO_SMALL)
    button.tooltip = "Salva a planilha"
    add(button)
  }

  private fun filename(): String {
    val sdf = DateTimeFormatter.ofPattern("yyMMddHHmmss")
    val textTime = LocalDateTime.now().format(sdf)
    return "precificacao$textTime.xlsx"
  }


  override fun isAuthorized(user: IUser) = (user as? UserSaci)?.garantiaInserir ?: false
  override val label: String
    get() = "Inserir"

  override fun filtro() = FiltroGarantia(
    tipoDiferenca = edtTipoDiferenca.value ?: ETipoDiferencaGarantiaSimples.TODOS,
    tipoValidade = edtTipoValidade.value ?: ETipoValidade.TODOS,
    filtro = edtFiltro.value ?: "",
  )

  override fun Grid<ProdutoValidade>.gridPanel() {
    this.setSelectionMode(Grid.SelectionMode.MULTI)
    produtoCodigo()
    produtoDescricao()
    produtoGrade()
    produtoEstoque()
    produtoDescricaoCompleta2()
    produtoClno()
    produtoVendno()
    produtoFornecedor()
    produtoTipoNo()
    produtoTipoProduto()
    produtoTipoValidade()
    produtoMeseValidade()
  }
}


