package br.com.astrosoft.promocao.viewmodel.promocao

import br.com.astrosoft.framework.viewmodel.fail
import br.com.astrosoft.promocao.model.beans.ETipoListaPromocao.BASE
import br.com.astrosoft.promocao.model.beans.PrecoPromocao
import java.time.LocalDate

class TabSemPromocaoViewModel(viewModel: PromocaoViewModel) :
        TabAbstractPromocaoViewModel<ITabSemPromocaoViewModel>(viewModel) {
  fun executaDesconto()= viewModel.exec {
    val desconto = desconto()

    val validade = validade()

    val list = subView.listSelected().ifEmpty { fail("Não há produtos selecionados") }

    PrecoPromocao.marcaDesconto(list, desconto, validade)

    subView.updateComponent()

    viewModel.showInformation("Operação realizada com sucesso")
  }

  private fun validade(): LocalDate {
    val validade = subView.validade() ?: fail("Validade não informado")

    if (validade.isBefore(LocalDate.now())) {
      fail("Data de validade inválida")
    }
    return validade
  }

  private fun desconto(): Double {
    val desconto = subView.desconto() ?: fail("Desconto não informado")

    if (desconto == 0.00) {
      fail("O desconto não pode ser zero")
    }
    return desconto
  }

  fun desfazDesconto() = viewModel.exec{
    PrecoPromocao.desfazDesconto()

    subView.updateComponent()

    viewModel.showInformation("Operação realizada com sucesso")
  }

  override val subView
    get() = viewModel.view.tabSemPromocaoViewModel
  override val tipoTab
    get() = listOf(BASE)
}

interface ITabSemPromocaoViewModel : ITabAbstractPromocaoViewModel{
  fun desconto() : Double?
  fun validade(): LocalDate?
}