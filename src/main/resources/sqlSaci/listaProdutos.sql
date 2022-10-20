USE sqldados;

DROP TABLE IF EXISTS T_STK;
CREATE TEMPORARY TABLE T_STK (
  PRIMARY KEY (prdno, grade)
)
SELECT prdno,
       grade,
       SUM(IF(storeno = 1, qtty_atacado / 100, 0.00)) AS estJS,
       SUM(IF(storeno = 2, qtty_atacado / 100, 0.00)) AS estDS,
       SUM(IF(storeno = 3, qtty_atacado / 100, 0.00)) AS estMR,
       SUM(IF(storeno = 4, qtty_atacado / 100, 0.00)) AS estMF,
       SUM(IF(storeno = 5, qtty_atacado / 100, 0.00)) AS estPK,
       SUM(IF(storeno = 8, qtty_atacado / 100, 0.00)) AS estTM,
       SUM(qtty_atacado)                              AS estoque
FROM sqldados.stk AS S
WHERE S.storeno IN (1, 2, 3, 4, 5, 8)
GROUP BY prdno, grade;


DROP TABLE IF EXISTS sqltmp.T_RESULT;
CREATE TABLE sqltmp.T_RESULT (
  FULLTEXT INDEX (prdno,
		  codigo,
		  descricao,
		  grade,
		  fornStr,
		  abrev,
		  tipoStr,
		  cl,
		  codBar)
)
SELECT P.no                                                              AS prdno,
       TRIM(P.no)                                                        AS codigo,
       TRIM(MID(P.name, 1, 37))                                          AS descricao,
       IFNULL(B.grade, '')                                               AS grade,
       CAST(P.mfno AS char ASCII)                                        AS fornStr,
       P.mfno                                                            AS forn,
       V.sname                                                           AS abrev,
       CAST(P.typeno AS CHAR ASCII)                                      AS tipoStr,
       P.typeno                                                          AS tipo,
       CAST(P.clno AS CHAR ASCII)                                        AS cl,
       TRIM(IF(B.grade IS NULL, IFNULL(P2.gtin, P.barcode), B.barcode))  AS codBar,
       estJS                                                             AS estJS,
       estDS                                                             AS estDS,
       estMR                                                             AS estMR,
       estMF                                                             AS estMF,
       estPK                                                             AS estPK,
       estTM                                                             AS estTM,
       estoque                                                           AS estoque,
       PR.cost / 10000                                                   AS custo,
       IF(PR.promo_validate >= CURRENT_DATE, PR.promo_price / 100, 0.00) AS pPromo,
       PR.refprice / 100                                                 AS pRef
FROM sqldados.prd            AS P
  INNER JOIN T_STK           AS S
	       ON S.prdno = P.no
  LEFT JOIN  sqldados.prd2   AS P2
	       ON P.no = P2.prdno
  LEFT JOIN  sqldados.vend   AS V
	       ON V.no = P.mfno
  LEFT JOIN  sqldados.prp    AS PR
	       ON PR.prdno = P.no AND PR.storeno = 10
  LEFT JOIN  sqldados.prdbar AS B
	       ON S.prdno = B.prdno AND S.grade = B.grade
WHERE S.estoque != 0
GROUP BY P.no;


SELECT prdno,
       codigo,
       descricao,
       grade,
       forn,
       abrev,
       tipo,
       cl,
       codBar,
       estJS,
       estDS,
       estMR,
       estMF,
       estPK,
       estTM,
       estoque,
       custo,
       pPromo,
       pRef
FROM sqltmp.T_RESULT
WHERE :pesquisa = ''
   OR MATCH(prdno, codigo, descricao, grade, fornStr, abrev, tipoStr, cl, codBar)
	    AGAINST(:pesquisa IN BOOLEAN MODE)
