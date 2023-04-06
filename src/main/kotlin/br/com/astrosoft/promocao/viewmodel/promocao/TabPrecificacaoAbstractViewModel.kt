package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.framework.model.BeanForm
import br.com.astrosoft.framework.viewmodel.ITabView
import br.com.astrosoft.promocao.model.beans.FiltroPrecificacao
import br.com.astrosoft.promocao.model.beans.Precificacao

abstract class TabPrecificacaoAbstractViewModel(val viewModel: PromocaoViewModel) {
  abstract val subView: ITabPrecificacaoViewModel

  fun updateView() {
    val filtro = subView.filtro()
    val list = Precificacao.findAll(filtro)
    subView.updateGrid(list)
  }

  fun updatePrecificacao(bean: BeanForm) {
    val list = subView.listSelected()
    Precificacao.updateItens(list, bean)
    updateView()
  }
}

interface ITabPrecificacaoViewModel : ITabView {
  fun filtro(): FiltroPrecificacao
  fun updateGrid(itens: List<Precificacao>)
  fun listSelected(): List<Precificacao>
}