package br.com.astrosoft.promocao.model

import br.com.astrosoft.framework.model.DB
import br.com.astrosoft.framework.model.QueryDB
import br.com.astrosoft.promocao.model.beans.*

class QueryEstoque : QueryDB(driver, url, username, password) {
  fun consultaValidadeEntrada(filtro: FiltroValidadeEntrada): List<ValidadeEntrada> {
    val sql = "/sqlEstoque/calculoValidade.sql"
    return query(sql, ValidadeEntrada::class){
      addOptionalParameter("query", filtro.query)
    }
  }
  companion object {
    private val db = DB("db")
    internal val driver = db.driver
    internal val url = db.url
    internal val username = db.username
    internal val password = db.password
  }
}

val estoque = QueryEstoque()

