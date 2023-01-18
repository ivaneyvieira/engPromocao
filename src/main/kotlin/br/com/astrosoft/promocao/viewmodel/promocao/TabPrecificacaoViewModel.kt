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
}

interface ITabPrecificacaoViewModel : ITabView{
  fun filtro(): FiltroPrecificacao
  fun updateGrid(itens: List<Precificacao>)
  fun listSelected(): List<Precificacao>
}