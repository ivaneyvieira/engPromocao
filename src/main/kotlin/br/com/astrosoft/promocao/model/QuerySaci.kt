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

  fun produtoGrade(codigo: String): List<ProdutoGrade> {
    val sql = "/sqlSaci/produtoGrade.sql"
    return query(sql, ProdutoGrade::class) {
      addOptionalParameter("codigo", codigo.toIntOrNull()?.toString())
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

  fun produtosPrecoAlteracao(filtro: FiltroPrecoAlteracao): List<PrecoAlteracao> {
    val sql = "/sqlSaci/alteracaoPreco.sql"
    return query(sql, PrecoAlteracao::class) {
      addOptionalParameter("codigo", filtro.codigo)
      addOptionalParameter("vendno", filtro.vendno)
      addOptionalParameter("clno", filtro.clno)
      addOptionalParameter("typeno", filtro.typeno)
      addOptionalParameter("dataInicial", filtro.dataInicial?.toSaciDate() ?: 0)
      addOptionalParameter("dataFinal", filtro.dataFinal?.toSaciDate() ?: 0)
    }
  }

  fun produtosPromocaoAlteracao(filtro: FiltroPrecoAlteracao): List<PrecoAlteracao> {
    val sql = "/sqlSaci/alteracaoPromocao.sql"
    return query(sql, PrecoAlteracao::class) {
      addOptionalParameter("codigo", filtro.codigo)
      addOptionalParameter("vendno", filtro.vendno)
      addOptionalParameter("clno", filtro.clno)
      addOptionalParameter("typeno", filtro.typeno)
      addOptionalParameter("dataInicial", filtro.dataInicial?.toSaciDate() ?: 0)
      addOptionalParameter("dataFinal", filtro.dataFinal?.toSaciDate() ?: 0)
    }
  }

  fun produtosBaseAlteracao(filtro: FiltroPrecoAlteracao): List<PrecoAlteracao> {
    val sql = "/sqlSaci/alteracaoBase.sql"
    return query(sql, PrecoAlteracao::class) {
      addOptionalParameter("codigo", filtro.codigo)
      addOptionalParameter("vendno", filtro.vendno)
      addOptionalParameter("clno", filtro.clno)
      addOptionalParameter("typeno", filtro.typeno)
      addOptionalParameter("dataInicial", filtro.dataInicial?.toSaciDate() ?: 0)
      addOptionalParameter("dataFinal", filtro.dataFinal?.toSaciDate() ?: 0)
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

  fun consultaValidade(filtro: FiltroValidade): List<ComparaValidade> {
    val sql = "/sqlSaci/consultaValidade.sql"

    return query(sql, ComparaValidade::class) {
      addOptionalParameter("tipo", filtro.tipo.num)
      addOptionalParameter("query", filtro.query)
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

  fun consultaProdutoValidade(filtro: FiltroGarantia): List<ProdutoValidade> {
    val sql = "/sqlSaci/produtosValidade.sql"

    return query(sql, ProdutoValidade::class) {
      addOptionalParameter("tipoDiferenca", filtro.tipoDiferenca.num)
      addOptionalParameter("tipoValidade", filtro.tipoValidade.num)
      addOptionalParameter("filtro", filtro.filtro)
    }
  }

  fun modificaValidadeCadastro(produto: ProdutoValidade, infoModifica: InfoModifica) {
    val sql = """
      UPDATE sqldados.prd AS P
      SET P.tipoGarantia  = ${infoModifica.tipo?.num},
          P.garantia      = ${infoModifica.validade}
      WHERE no = LPAD(${produto.codigo} * 1, 16, ' ')
    """.trimIndent()
    if (infoModifica.tipo != null) {
      script(sql)
    }
  }

  fun modificaValidadeDescricao(produto: ProdutoValidade, infoModifica: InfoModifica) {
    val tempo = when (infoModifica.tipo) {
      ETipoValidade.DIA    -> "D"
      ETipoValidade.SEMANA -> "S"
      ETipoValidade.MES    -> "M"
      ETipoValidade.ANO    -> "A"
      else                 -> ""
    }
    val validade = infoModifica.validade
    val novaValidade = if (validade == 0) ""
    else "VALIDADE: ${infoModifica.validade}${tempo}"
    val sql = """
      UPDATE sqldados.prdnam AS P
      SET P.name = CONCAT(RPAD('${produto.descricaoCompleta1}', 60, ' '), 
                          RPAD('${novaValidade}', 60, ' '))
      WHERE prdno = LPAD(${produto.codigo} * 1, 16, ' ')
    """.trimIndent()
    if (infoModifica.tipo != null) {
      script(sql)
    }
  }
  fun listaProdutos(filtro: FiltroProduto) : List<Produtos> {
    val sql = "/sqlSaci/listaProdutos.sql"

    return query(sql, Produtos::class) {
      addOptionalParameter("pesquisa", filtro.pesquisa)
    }
  }
  fun listaPrecificacao(filtro: FiltroPrecificacao) : List<Precificacao> {
    val sql = "/sqlSaci/selectPrecificacao.sql"

    return query(sql, Precificacao::class) {
      addOptionalParameter("codigo", filtro.codigo)
      addOptionalParameter("vendno", filtro.vendno)
      addOptionalParameter("tributacao", filtro.tributacao)
    }
  }

  fun savePrecificacao(prp: Precificacao) {
    val sql = "/sqlSaci/updatePrecificacao.sql"
    script(sql){
      addOptionalParameter("prdno", prp.prdno)
      addOptionalParameter("cpmf", prp.cpmf)
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

