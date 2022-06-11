package br.com.astrosoft.promocao.view.promocao

import br.com.astrosoft.framework.model.Config.user
import br.com.astrosoft.framework.util.ZPLPreview
import br.com.astrosoft.promocao.model.EtiquetaChave
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.viewmodel.preco.ITabBasePrecoViewModel
import br.com.astrosoft.promocao.viewmodel.preco.TabBasePrecoViewModel
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class TabBasePreco(viewModel: TabBasePrecoViewModel) : TabAbstractPreco<ITabBasePrecoViewModel>(viewModel),
        ITabBasePrecoViewModel {
  override val label: String
    get() = "Base"

  override fun HorizontalLayout.addAditionaisFields() {
    button("Etiqueta") {
      icon = VaadinIcon.PRINT.create()
      onLeftClick {
        val itens = itensSelecionados()
        if(itens.isEmpty()){
          showErro("Nenhum item selecionado")
        }else {
          val dados = itens.flatMap {
            it.dadosEtiquetas()
          }
          val userSaci = user as? UserSaci
          val impressora = userSaci?.impressora
          if(impressora != null) {
            EtiquetaChave.printPreviewProdutos(impressora, dados)
          }
        }
      }
    }
  }
}