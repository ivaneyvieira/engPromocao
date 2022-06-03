package br.com.astrosoft.promocao.model

import br.com.astrosoft.framework.model.Config.appName
import br.com.astrosoft.framework.model.DB
import br.com.astrosoft.framework.model.QueryDB
import br.com.astrosoft.framework.util.toSaciDate
import br.com.astrosoft.promocao.model.beans.*
import java.time.LocalDate

class QuerySaci : QueryDB(driver, url, username, password) {
  fun findUser(login: String?): UserSaci? {
    login ?: return null
    val sql = "/sqlSaci/userSenha.sql"
    return query(sql, UserSaci::class) {
      addParameter("login", login)
      addParameter("appName", appName)
    }.firstOrNull()
  }

  fun findAllUser(): List<UserSaci> {
    val sql = "/sqlSaci/userSenha.sql"
    return query(sql, UserSaci::class) {
      addParameter("login", "TODOS")
      addParameter("appName", appName)
    }
  }

  fun allLojas(): List<Loja> {
    val sql = "/sqlSaci/listLojas.sql"
    return query(sql, Loja::class)
  }

  fun updateUser(user: UserSaci) {
    val sql = "/sqlSaci/updateUser.sql"
    script(sql) {
      addOptionalParameter("login", user.login)
      addOptionalParameter("bitAcesso", user.bitAcesso)
      addOptionalParameter("loja", user.storeno)
      addOptionalParameter("appName", appName)
    }
  }

  fun produtosPromocao(filtro: FiltroPrecoPromocao): List<PrecoPromocao> {
    val sql = "/sqlSaci/produtosPromocao.sql"
    return query(sql, PrecoPromocao::class) {
      addOptionalParameter("codigo", filtro.codigo)
      addOptionalParameter("vendno", filtro.vendno)
      addOptionalParameter("clno", filtro.clno)
      addOptionalParameter("typeno", filtro.typeno)
      addOptionalParameter("decimal99", filtro.decimal99)
      addOptionalParameter("tipoLista", filtro.tipoLista.map { it.name })
    }
  }

  fun prorrogaPromocao(codigo: String, validade: LocalDate, login: String) {
    val sql = "/sqlSaci/prorrogaPromocao.sql"
    script(sql) {
      addOptionalParameter("codigo", codigo)
      addOptionalParameter("validade", validade.toSaciDate())
      addOptionalParameter("login", login)
    }
  }

  fun executaPromocao(codigo: String, desconto: Double, validade: LocalDate, login: String) {
    val sql = "/sqlSaci/executaPromocao.sql"
    script(sql) {
      addOptionalParameter("codigo", codigo)
      addOptionalParameter("desconto", desconto)
      addOptionalParameter("validade", validade.toSaciDate())
      addOptionalParameter("login", login)
    }
  }

  fun desfazPromocao(codigo: String, login: String) {
    val sql = "/sqlSaci/desfazerPromocao.sql"
    script(sql) {
      addOptionalParameter("login", login)
      addOptionalParameter("codigo", codigo)
    }
  }

  fun consultaValidade(tipo: ETipoDiferencaGarantia): List<ComparaValidade> {
    val sql = "/sqlSaci/consultaValidade.sql"

    return query(sql, ComparaValidade::class) {
      addOptionalParameter("tipo", tipo.num)
    }
  }

  fun modificaData(resultList: List<PrecoPromocao>, dataOld: LocalDate, dataNew: LocalDate) {
    val sql = "/sqlSaci/updateDatas.sql"
    resultList.forEach { preco ->
      script(sql) {
        addOptionalParameter("dataOld", dataOld.toSaciDate())
        addOptionalParameter("dataNew", dataNew.toSaciDate())
        addOptionalParameter("codigo", preco.codigo)
      }
    }
  }

  companion object {
    private val db = DB("saci")
    internal val driver = db.driver
    internal val url = db.url
    internal val username = db.username
    internal val password = db.password
    val ipServer: String? = url.split("/").getOrNull(2)
  }
}

val saci = QuerySaci()

