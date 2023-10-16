package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class PromocaoViewModel(view: IPromocaoView) : ViewModel<IPromocaoView>(view) {
  val tabBaseViewModel = TabBasePromocaoViewModel(this)
  val tab99ViewModel = Tab99ViewModel(this)
  val tabPromocaoViewModel = TabPromocaoViewModel(this)
  val tabSemPromocaoViewModel = TabSemPromocaoViewModel(this)
  val tabPrecificacaoViewModel = TabPrecificacaoViewModel(this)
  val tabPrecificacaoEntradaViewModel = TabPrecificacaoEntradaViewModel(this)
  val tabPrecificacaoEntradaMaViewModel = TabPrecificacaoEntradaMaViewModel(this)
  val tabPrecificacaoSaidaViewModel = TabPrecificacaoSaidaViewModel(this)

  override fun listTab() = listOf(
    view.tabBaseViewModel,
    view.tabSemPromocaoViewModel,
    view.tabPromocaoViewModel,
    view.tabPrecificacaoViewModel,
    view.tabPrecificacaoEntradaViewModel,
    view.tabPrecificacaoSaidaViewModel,
    view.tabPrecificacaoEntradaMaViewModel,
  )
}

interface IPromocaoView : IView {
  val tabBaseViewModel: ITabBasePromocaoViewModel
  val tab99ViewModel: ITab99ViewModel
  val tabPromocaoViewModel: ITabPromocaoViewModel
  val tabSemPromocaoViewModel: ITabSemPromocaoViewModel
  val tabPrecificacaoViewModel: ITabPrecificacaoViewModel
  val tabPrecificacaoEntradaViewModel: ITabPrecificacaoViewModel
  val tabPrecificacaoEntradaMaViewModel: ITabPrecificacaoViewModel
  val tabPrecificacaoSaidaViewModel: ITabPrecificacaoViewModel
}

