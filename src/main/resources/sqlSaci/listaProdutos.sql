USE sqldados;

DROP TABLE IF EXISTS T_STK;
CREATE TEMPORARY TABLE T_STK (
  PRIMARY KEY (prdno, grade)
)
SELECT prdno,
       grade,
       SUM(IF(storeno = 1, qtty_varejo / 100, 0.00)) AS estJS,
       SUM(IF(storeno = 2, qtty_varejo / 100, 0.00)) AS estDS,
       SUM(IF(storeno = 3, qtty_varejo / 100, 0.00)) AS estMR,
       SUM(IF(storeno = 4, qtty_varejo / 100, 0.00)) AS estMF,
       SUM(IF(storeno = 5, qtty_varejo / 100, 0.00)) AS estPK,
       SUM(IF(storeno = 8, qtty_varejo / 100, 0.00)) AS estTM,
       SUM(qtty_varejo)                              AS estoque
FROM sqldados.stk AS S
WHERE S.storeno IN (1, 2, 3, 4, 5, 8)
GROUP BY prdno, grade;


DROP TABLE IF EXISTS sqltmp.T_RESULT;
CREATE TABLE sqltmp.T_RESULT
SELECT P.no                                                              AS prdno,
       TRIM(P.no) * 1                                                    AS codigo,
       TRIM(MID(P.name, 1, 37))                                          AS descricao,
       IFNULL(B.grade, '')                                               AS grade,
       CAST(P.mfno AS char ASCII)                                        AS fornStr,
       P.mfno                                                            AS forn,
       V.sname                                                           AS abrev,
       CAST(P.typeno AS CHAR ASCII)                                      AS tipoStr,
       P.typeno                                                          AS tipo,
       P.clno                                                            AS cl,
       RPAD(MID(LPAD(P.clno, 6, '0'), 1, 2), 6, '0') * 1                 AS groupno,
       RPAD(MID(LPAD(P.clno, 6, '0'), 1, 4), 6, '0') * 1                 AS deptno,
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

DO @PESQUISA := :pesquisa;
DO @PESQUISA_LIKE := CONCAT(:pesquisa, '%');

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
   OR codigo = @PESQUISA
   OR descricao LIKE @PESQUISA_LIKE
   OR grade LIKE @PESQUISA_LIKE
   OR fornStr LIKE @PESQUISA_LIKE
   OR abrev LIKE @PESQUISA_LIKE
   OR tipo = @PESQUISA
   OR cl = @PESQUISA
   OR groupno = @PESQUISA
   OR deptno = @PESQUISA
   OR codBar = @PESQUISA
