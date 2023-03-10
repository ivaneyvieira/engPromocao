DO @CODIGO := :codigo;
DO @PRDNO := LPAD(@CODIGO, 16, ' ');
DO @LISTVEND := REPLACE(:listVend, ' ', '');
DO @TRIBUTACAO := :tributacao;
DO @TYPENO := :typeno;
DO @CLNO := :clno;
DO @QUERY := :query;
DO @QUERYLIKE := CONCAT(@QUERY, '%');

SELECT prdno                                                                   AS prdno,
       LPAD(TRIM(prdno), 6, '0')                                               AS codigo,
       TRIM(MID(PD.name, 1, 37))                                               AS descricao,
       PD.mfno                                                                 AS vendno,
       V.sname                                                                 AS fornecedor,
       ROUND(adm / 100, 2)                                                     AS cpmf,
       taxno                                                                   AS tributacao,
       PD.typeno                                                               AS typeno,
       PD.clno                                                                 AS clno,
       ROUND(IF(PD.taxno = '00', 0.00, IFNULL(PD.lucroTributado, 0)) / 100, 4) AS mvap,
       P.dicm / 100                                                            AS icmsp,
       ROUND(pis / 100, 2)                                                     AS fcp,
       P.fob / 10000                                                           AS pcfabrica,
       P.ipi / 100                                                             AS ipi,
       P.package / 100                                                         AS embalagem,
       P.costdel3 / 100                                                        AS retido,
       IF(PD.taxno = '06', PD.auxShort1 / 100, 0.00)                           AS creditoICMS,
       P.freight / 100                                                         AS frete,
       P.cost / 10000                                                          AS custoContabil,
       P.icm / 100                                                             AS icms,
       P.finsoc / 100                                                          AS pis,
       P.comm / 100                                                            AS ir,
       P.adv / 100                                                             AS contrib,
       P.refpdel2 / 100                                                        AS fixa,
       P.refpdel3 / 100                                                        AS outras,
       P.profit / 100                                                          AS lucroLiq,
       TRUNCATE((P.cost / 100) / ((100 - (((P.icm + P.pis + P.finsoc + comm + adv + adm + refpdel1 +
					    refpdel2 + refpdel3) + profit) / 100)) / 100), 0) /
       100                                                                     AS precoSug,
       P.refprice / 100                                                        AS precoRef

FROM sqldados.prp          AS P
  INNER JOIN sqldados.prd  AS PD
	       ON PD.no = P.prdno
  INNER JOIN sqldados.vend AS V
	       ON PD.mfno = V.no
WHERE storeno = 10
  AND P.prdno < LPAD('960001', 16, ' ')
  AND (P.prdno = @PRDNO OR @CODIGO = 0)
  AND (FIND_IN_SET(PD.mfno, @LISTVEND) OR @LISTVEND = '')
  AND (PD.typeno = @TYPENO OR @TYPENO = 0)
  AND (PD.clno = @CLNO OR @CLNO = 0)
  AND (PD.taxno = @TRIBUTACAO OR @TRIBUTACAO = '')
  AND CASE :marca
	WHEN 'T'
	  THEN TRUE
	WHEN 'N'
	  THEN MID(PD.name, 1, 1) NOT IN ('.', '*', '!', '*', ']', ':', '#')
	WHEN 'S'
	  THEN MID(PD.name, 1, 1) IN ('.', '*', '!', '*', ']', ':', '#')
      END
HAVING @QUERY = ''
    OR descricao LIKE @QUERYLIKE
    OR REPLACE(REPLACE(FORMAT(mvap, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(icmsp, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(fcp, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(pcfabrica, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(ipi, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(embalagem, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(retido, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(creditoICMS, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(frete, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(custoContabil, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(icms, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(pis, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(ir, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(contrib, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(fixa, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(outras, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(lucroLiq, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(precoSug, 2), ',', ''), '.', ',') LIKE @QUERY
    OR REPLACE(REPLACE(FORMAT(precoRef, 2), ',', ''), '.', ',') LIKE @QUERY
