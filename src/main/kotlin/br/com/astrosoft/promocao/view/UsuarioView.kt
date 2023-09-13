package br.com.astrosoft.promocao.view

import br.com.astrosoft.framework.view.UserLayout
import br.com.astrosoft.framework.viewmodel.IUsuarioView
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.promocao.viewmodel.UsuarioViewModel
import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import org.vaadin.crudui.crud.CrudOperation
import org.vaadin.crudui.crud.CrudOperation.*
import org.vaadin.crudui.crud.impl.GridCrud

@Route(layout = PromocaoLayout::class)
@PageTitle("Usuário")
class UsuarioView : UserLayout<UserSaci, UsuarioViewModel>(), IUsuarioView {
    override val viewModel = UsuarioViewModel(this)

    override fun columns(): List<String> {
        return listOf(UserSaci::no.name, UserSaci::login.name, UserSaci::name.name, UserSaci::impressora.name)
    }

    override fun createGrid() = GridCrud(UserSaci::class.java)

    override fun formCrud(
        operation: CrudOperation?,
        domainObject: UserSaci?,
        readOnly: Boolean,
        binder: Binder<UserSaci>
    ): Component {
        return FormLayout().apply {
            if (operation in listOf(READ, DELETE, UPDATE)) integerField("Número") {
                isReadOnly = readOnly
                binder.bind(this, UserSaci::no.name)
            }
            if (operation in listOf(ADD, READ, DELETE, UPDATE)) textField("Login") {
                isReadOnly = readOnly
                binder.bind(this, UserSaci::login.name)
            }
            if (operation in listOf(READ, DELETE, UPDATE)) textField("Nome") {
                isReadOnly = true
                binder.bind(this, UserSaci::name.name)
            }
            if (operation in listOf(ADD, READ, DELETE, UPDATE)) {
                comboBox<Int>("Número Loja") {
                    isReadOnly = readOnly
                    isAllowCustomValue = false
                    val lojas = viewModel.allLojas()
                    val values = lojas.map { it.no } + listOf(0)
                    setItems(values.distinct().sorted())
                    this.setItemLabelGenerator { storeno ->
                        when (storeno) {
                            0 -> "Todas as lojas"
                            else -> lojas.firstOrNull { loja ->
                                loja.no == storeno
                            }?.descricao ?: ""
                        }
                    }
                    isAllowCustomValue = false
                    binder.bind(this, UserSaci::storeno.name)
                }

                formLayout {
                    h4("Produtos") {
                        colspan = 2
                    }

                    checkBox("Estoque Geral") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::produtoEstoqueGeral.name)
                    }

                    checkBox("Cadastro") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::produtoCadastro.name)
                    }

                    checkBox("Estoque Total") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::produtoEstoqueTotal.name)
                    }

                    checkBox("Estoque App") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::produtoApp.name)
                    }

                    checkBox("Estoque App Por Entrada") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::produtoAppEntrada.name)
                    }
                }

                formLayout {
                    h4("Promoção") {
                        colspan = 2
                    }

                    checkBox("Base") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::promocaoBase.name)
                    }
                    checkBox("99") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::promocao99.name)
                    }
                    checkBox("Promoção") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::promocaoPromocao.name)
                    }
                    checkBox("Sem Promoção") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::promocaoSemPromocao.name)
                    }
                    checkBox("Precificação") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::promocaoPrecificacao.name)
                    }
                    checkBox("Precificação Entrada") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::promocaoPrecificacaoEntrada.name)
                    }
                    checkBox("Precificação Saída") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::promocaoPrecificacaoSaida.name)
                    }
                    checkBox("Prec Ent MA") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::promocaoPrecificacaoEntradaMa.name)
                    }
                }

                formLayout {
                    h4("Preco") {
                        colspan = 2
                    }

                    checkBox("Base") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::precoBase.name)
                    }
                    checkBox("Preço") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::precoPreco.name)
                    }
                    checkBox("Promoção") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::precoPromocao.name)
                    }
                }

                formLayout {
                    h4("Garantia") {
                        colspan = 2
                    }

                    checkBox("Base") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::garantiaBase.name)
                    }
                    checkBox("Inserir") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::garantiaInserir.name)
                    }
                    checkBox("Validade") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::garantiaValidade.name)
                    }
                    checkBox("Validade Entrada") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::garantiaEntrada.name)
                    }
                    checkBox("Venda") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::garantiaVenda.name)
                    }
                    checkBox("Estoque App") {
                        isReadOnly = readOnly
                        binder.bind(this, UserSaci::garantiaEstoqueApp.name)
                    }
                }
            }
        }
    }
}

