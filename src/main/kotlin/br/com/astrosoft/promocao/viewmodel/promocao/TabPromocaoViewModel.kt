package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.framework.viewmodel.fail
import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao.PROMOCAO
import br.com.astrosoft.promocao.model.beans.PrecoPromocao
import br.com.astrosoft.promocao.model.saci
import java.time.LocalDate

class TabPromocaoViewModel(viewModel: PromocaoViewModel) :
  TabAbstractPromocaoViewModel<ITabPromocaoViewModel>(viewModel) {
  override val subView
    get() = viewModel.view.tabPromocaoViewModel
  override val tipoTab
    get() = listOf(PROMOCAO)

  private fun validade(): LocalDate {
    val validade = subView.validade() ?: fail("Validade não informado")

    if (validade.isBefore(LocalDate.now())) {
      fail("Data de validade inválida")
    }
    return validade
  }

  fun prorrogaValidade() = viewModel.exec {
    val validade = validade()

    val list = subView.listSelected().ifEmpty { fail("Não há produtos selecionados") }

    PrecoPromocao.prorrogaDesconto(list, validade)

    subView.updateComponent()

    viewModel.showInformation("Operação realizada com sucesso")
  }

  fun modificaData(dataNew: LocalDate?, dataOld: LocalDate?) = viewModel.exec {
    dataNew ?: fail("Data Não informada")
    dataOld ?: fail("Data Não informada")

    val filtro = subView.filtro()
    val resultList = PrecoPromocao.find(filtro)

    saci.modificaData(resultList, dataOld, dataNew)
    updateView()
  }
}

interface ITabPromocaoViewModel : ITabAbstractPromocaoViewModel {
  fun validade(): LocalDate?
}