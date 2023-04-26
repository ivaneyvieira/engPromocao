package br.com.astrosoft.promocao.model

import br.com.astrosoft.framework.model.BeanForm
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
      addOptionalParameter("marca", filtro.marcaPonto.codigo)
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

    val listVend = filtro.listVend.joinToString(separator = ",")

    return query(sql, ComparaValidade::class) {
      addOptionalParameter("tipo", filtro.tipo.num)
      addOptionalParameter("query", filtro.query)
      addOptionalParameter("marca", filtro.marca.codigo)

      addOptionalParameter("listVend", listVend)
      addOptionalParameter("tributacao", filtro.tributacao)
      addOptionalParameter("typeno", filtro.typeno)
      addOptionalParameter("clno", filtro.clno)
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

  fun modificaDataSolo(resultList: List<PrecoPromocao>, dataNew: LocalDate) {
    val sql = "/sqlSaci/updateDatasSolo.sql"
    resultList.forEach { preco ->
      script(sql) {
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
      ETipoValidade.DIA -> "D"
      ETipoValidade.SEMANA -> "S"
      ETipoValidade.MES -> "M"
      ETipoValidade.ANO -> "A"
      else -> ""
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

  fun listaProdutos(filtro: FiltroProduto): List<Produtos> {
    val sql = "/sqlSaci/listaProdutos.sql"

    val listVend = filtro.listVend.joinToString(separator = ",")

    return query(sql, Produtos::class) {
      addOptionalParameter("pesquisa", filtro.pesquisa)
      addOptionalParameter("marca", filtro.marcaPonto.codigo)
      addOptionalParameter("inativo", filtro.inativo.codigo)
      addOptionalParameter("todoEstoque", filtro.todoEstoque.let { if (it) "S" else "N" })
      addOptionalParameter("codigo", filtro.codigo)
      addOptionalParameter("listVend", listVend)
      addOptionalParameter("tributacao", filtro.tributacao)
      addOptionalParameter("typeno", filtro.typeno)
      addOptionalParameter("clno", filtro.clno)
      addOptionalParameter("estoqueTotal", filtro.estoqueTotal.codigo)
      addOptionalParameter("diCompra", filtro.diCompra.toSaciDate())
      addOptionalParameter("dfCompra", filtro.dfCompra.toSaciDate())
      addOptionalParameter("diVenda", filtro.diVenda.toSaciDate())
      addOptionalParameter("dfVenda", filtro.dfVenda.toSaciDate())
    }
  }

  fun listaPrecificacao(filtro: FiltroPrecificacao): List<Precificacao> {
    val sql = "/sqlSaci/selectPrecificacao.sql"

    val listVend = filtro.listVend.joinToString(separator = ",")

    return query(sql, Precificacao::class) {
      addOptionalParameter("codigo", filtro.codigo)
      addOptionalParameter("listVend", listVend)
      addOptionalParameter("tributacao", filtro.tributacao)
      addOptionalParameter("typeno", filtro.typeno)
      addOptionalParameter("clno", filtro.clno)
      addOptionalParameter("marca", filtro.marcaPonto.codigo)
      addOptionalParameter("query", filtro.query)
    }
  }

  fun savePrecificacao(prp: Precificacao) {
    val sql = "/sqlSaci/updatePrecificacao.sql"
    script(sql) {
      addOptionalParameter("prdno", prp.prdno)

      addOptionalParameter("mvap", prp.mvap)
      addOptionalParameter("creditoICMS", prp.creditoICMS)
      addOptionalParameter("pcfabrica", prp.pcfabrica)
      addOptionalParameter("ipi", prp.ipi)

      addOptionalParameter("embalagem", prp.embalagem)
      addOptionalParameter("retido", prp.retido)
      addOptionalParameter("icmsp", prp.icmsp)
      addOptionalParameter("frete", prp.frete)
      addOptionalParameter("freteICMS", prp.freteICMS)

      addOptionalParameter("icms", prp.icms)
      addOptionalParameter("fcp", prp.fcp)
      addOptionalParameter("pis", prp.pis)
      addOptionalParameter("ir", prp.ir)

      addOptionalParameter("contrib", prp.contrib)
      addOptionalParameter("cpmf", prp.cpmf)
      addOptionalParameter("fixa", prp.fixa)
      addOptionalParameter("outras", prp.outras)
    }
  }

  fun saveListPrecificacao(list: List<Precificacao>, bean: BeanForm) {
    transaction {
      list.forEach { pre ->
        val mvap = bean.mvap
        if (mvap != null) {
          pre.mvap = mvap.toDouble()
        }

        val creditoICMS = bean.creditoICMS
        if (creditoICMS != null) {
          pre.creditoICMS = creditoICMS.toDouble()
        }

        val pcfabrica = bean.pcfabrica
        if (pcfabrica != null) {
          pre.pcfabrica = pcfabrica.toDouble()
        }

        val ipi = bean.ipi
        if (ipi != null) {
          pre.ipi = ipi.toDouble()
        }

        val embalagem = bean.embalagem
        if (embalagem != null) {
          pre.embalagem = embalagem.toDouble()
        }

        val retido = bean.retido
        if (retido != null) {
          pre.retido = retido.toDouble()
        }

        val icmsp = bean.icmsp
        if (icmsp != null) {
          pre.icmsp = icmsp.toDouble()
        }

        val frete = bean.frete
        if (frete != null) {
          pre.frete = frete.toDouble()
        }

        val freteICMS = bean.freteICMS
        if (freteICMS != null) {
          pre.freteICMS = freteICMS.toDouble()
        }

        val icms = bean.icms
        if (icms != null) {
          pre.icms = icms.toDouble()
        }

        val fcp = bean.fcp
        if (fcp != null) {
          pre.fcp = fcp.toDouble()
        }

        val pis = bean.pis
        if (pis != null) {
          pre.pis = pis.toDouble()
        }

        val ir = bean.ir
        if (ir != null) {
          pre.ir = ir.toDouble()
        }

        val contrib = bean.contrib
        if (contrib != null) {
          pre.contrib = contrib.toDouble()
        }

        val cpmf = bean.cpmf
        if (cpmf != null) {
          pre.cpmf = cpmf.toDouble()
        }

        val fixa = bean.fixa
        if (fixa != null) {
          pre.fixa = fixa.toDouble()
        }

        val outras = bean.outras
        if (outras != null) {
          pre.outras = outras.toDouble()
        }

        pre.save()
      }
    }
  }

  fun saldoData(diDate: LocalDate?,  dfDate: LocalDate?): List<SaldoVenda> {
    val sql = "/sqlSaci/totalVendas.sql"

    return query(sql, SaldoVenda::class) {
      addOptionalParameter("diVenda", diDate.toSaciDate())
      addOptionalParameter("dfVenda", dfDate.toSaciDate())
    }
  }

  fun findPrdNfe(numero: String): List<PrdCodigo> {
    val sql = "/sqlSaci/findPrdNfe.sql"

    return query(sql, PrdCodigo::class) {
      if(numero.contains("/")) {
        val nfno = numero.split("/").getOrNull(0) ?: ""
        val nfse = numero.split("/").getOrNull(1) ?: ""
        val invno = 0
        addOptionalParameter("nfno", nfno)
        addOptionalParameter("nfse", nfse)
        addOptionalParameter("invno", invno)
      } else {
        val nfno = ""
        val nfse = ""
        val invno = numero.toIntOrNull() ?: 0
        addOptionalParameter("nfno", nfno)
        addOptionalParameter("nfse", nfse)
        addOptionalParameter("invno", invno)
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

