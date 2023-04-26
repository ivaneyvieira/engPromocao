package br.com.astrosoft.promocao.view.garantia

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.view.PromocaoLayout
import br.com.astrosoft.promocao.viewmodel.garantia.GarantiaViewModel
import br.com.astrosoft.promocao.viewmodel.garantia.IGarantiaView
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(value = "garantia", layout = PromocaoLayout::class)
@PageTitle("Garantia")
@CssImport("./styles/gridTotal.css", themeFor = "vaadin-grid")
class GarantiaView : ViewLayout<GarantiaViewModel>(), IGarantiaView {
    override val viewModel: GarantiaViewModel = GarantiaViewModel(this)
    override val tabBaseGarantia = TabBaseGarantia(viewModel.tabBaseGarantia)
    override val tabValidadeGarantia = TabValidadeGarantia(viewModel.tabValidadeGarantia)
    override val tabControleValidade = TabControleValidade(viewModel.tabControleValidade)
    override val tabInserirGarantia = TabInserirGarantia(viewModel.tabInserirGarantia)

    override fun isAccept(user: IUser): Boolean {
        val userSaci = user as? UserSaci ?: return false
        return userSaci.menuGarantia
    }

    init {
        addTabSheat(viewModel)
    }
}

