package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.promocao.model.beans.FiltroPrecificacao
import br.com.astrosoft.promocao.model.beans.Precificacao

class TabPrecificacaoViewModel(val viewModel: PromocaoViewModel) {
  val subView
    get() = viewModel.view.tabPrecificacaoViewModel

  fun updateView() {
    val filtro = subView.filtro()
    val list = Precificacao.findAll(filtro)
    subView.updateGrid(list)
  }

  fun modificaCpmf(cpmfNew: Double?) {
    val list = subView.listSelected()
    if (cpmfNew != null) {
      list.forEach { pre ->
        pre.cpmf = cpmfNew
        pre.save()
      }
      updateView()
    }
  }
}

interface ITabPrecificacaoViewModel : ITabView {
  fun filtro(): FiltroPrecificacao
  fun updateGrid(itens: List<Precificacao>)
  fun listSelected(): List<Precificacao>
}