package br.com.astrosoft.promocao.model

import br.com.astrosoft.framework.model.DB
import br.com.astrosoft.framework.model.QueryDB
import br.com.astrosoft.promocao.model.beans.FiltroValidadeEntrada
import br.com.astrosoft.promocao.model.beans.ValidadeEntrada

class QueryEstoque : QueryDB(driver, url, username, password) {
    fun consultaValidadeEntrada(filtro: FiltroValidadeEntrada): List<ValidadeEntrada> {
        val sql = "/sqlEstoque/calculoValidade.sql"

        val listVend = filtro.listVend.joinToString(separator = ",")

        return query(sql, ValidadeEntrada::class) {
            addOptionalParameter("query", filtro.query)
            addOptionalParameter("marca", filtro.marca.codigo)

            addOptionalParameter("listVend", listVend)
            addOptionalParameter("tributacao", filtro.tributacao)
            addOptionalParameter("typeno", filtro.typeno)
            addOptionalParameter("clno", filtro.clno)
            addOptionalParameter("estoque", filtro.estoque.codigo)
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

