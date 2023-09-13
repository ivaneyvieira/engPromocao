package br.com.astrosoft.promocao.model.beans

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.promocao.model.saci
import kotlin.math.pow
import kotlin.reflect.KProperty

class UserSaci : IUser {
    var no: Int = 0
    var name: String = ""
    override var login: String = ""
    override var senha: String = ""
    var bitAcesso: Int = 0
    var storeno: Int = 0
    var prntno: Int = 0
    var impressora: String? = ""
    override var ativo by DelegateAuthorized(0)
    var garantiaBase by DelegateAuthorized(1)
    var garantiaInserir by DelegateAuthorized(2)

    var precoBase by DelegateAuthorized(3)
    var precoPreco by DelegateAuthorized(4)
    var precoPromocao by DelegateAuthorized(5)

    var promocao99 by DelegateAuthorized(6)
    var promocaoBase by DelegateAuthorized(7)
    var promocaoPromocao by DelegateAuthorized(8)
    var promocaoSemPromocao by DelegateAuthorized(9)

//    var produtoBase by DelegateAuthorized(10)

    val promocaoPrecificacao by DelegateAuthorized(11)
    var produtoCadastro by DelegateAuthorized(12)
    val promocaoPrecificacaoEntrada by DelegateAuthorized(13)
    val promocaoPrecificacaoSaida by DelegateAuthorized(14)
    var garantiaValidade by DelegateAuthorized(15)
    var garantiaEntrada by DelegateAuthorized(16)
    var garantiaVenda by DelegateAuthorized(17)
    var produtoAppEntrada by DelegateAuthorized(18)
    var produtoApp by DelegateAuthorized(19)
    var produtoEstoqueGeral by DelegateAuthorized(20)
    var produtoEstoqueTotal by DelegateAuthorized(21)
    var garantiaEstoqueApp by DelegateAuthorized(22)
    val promocaoPrecificacaoEntradaMa by DelegateAuthorized(23)

    val menuProduto = produtoAppEntrada || produtoApp || produtoEstoqueGeral || produtoEstoqueTotal ||
            produtoCadastro
    val menuPromocao = precoBase || precoPreco || precoPromocao || promocaoPrecificacao ||
            promocaoPrecificacaoEntrada || promocaoPrecificacaoEntradaMa || promocaoPrecificacaoSaida
    val menuGarantia = garantiaBase || garantiaInserir || garantiaValidade || garantiaEntrada ||
            garantiaVenda || garantiaEstoqueApp
    val menuPreco = promocao99 || promocaoBase || promocaoPromocao || promocaoSemPromocao

    override val admin
        get() = login == "ADM"

    companion object {
        fun findAll(): List<UserSaci> {
            return saci.findAllUser().filter { it.ativo }
        }

        fun updateUser(user: UserSaci) {
            saci.updateUser(user)
        }

        fun findUser(login: String?): UserSaci? {
            return saci.findUser(login)
        }
    }
}

class DelegateAuthorized(numBit: Int) {
    private val bit = 2.toDouble().pow(numBit).toInt()

    operator fun getValue(thisRef: UserSaci?, property: KProperty<*>): Boolean {
        thisRef ?: return false
        return (thisRef.bitAcesso and bit) != 0 || thisRef.admin
    }

    operator fun setValue(thisRef: UserSaci?, property: KProperty<*>, value: Boolean?) {
        thisRef ?: return
        val v = value ?: false
        thisRef.bitAcesso = when {
            v -> thisRef.bitAcesso or bit
            else -> thisRef.bitAcesso and bit.inv()
        }
    }
}


