package br.com.astrosoft.promocao.model

import br.com.astrosoft.promocao.model.beans.Loja
import br.com.astrosoft.promocao.model.beans.UserSaci
import br.com.astrosoft.framework.model.Config.appName
import br.com.astrosoft.framework.model.DB
import br.com.astrosoft.framework.model.QueryDB
import br.com.astrosoft.promocao.model.beans.FiltroPrecoPromocao
import br.com.astrosoft.promocao.model.beans.PrecoPromocao

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

  fun produtosPromocao(filtro : FiltroPrecoPromocao) : List<PrecoPromocao>{
    val sql = "/sqlSaci/produtosPromocao.sql"
    return query(sql, PrecoPromocao::class){
      addOptionalParameter("vendno", filtro.vendno)
      addOptionalParameter("clno", filtro.clno)
      addOptionalParameter("typeno", filtro.typeno)
      addOptionalParameter("saldo", filtro.tipoSaldo.name)
      addOptionalParameter("tipoLista", filtro.tipoLista.map { it.name })
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

