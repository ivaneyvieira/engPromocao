DO @HOJE := CURRENT_DATE * 1;
DO @VENDNO := :vendno;
DO @TYPENO := :typeno;
DO @CLNO := :clno;

DROP TEMPORARY TABLE IF EXISTS T_PRD;
CREATE TEMPORARY TABLE T_PRD (
  PRIMARY KEY (prdno)
)
SELECT P.no                     AS prdno,
       TRIM(MID(P.name, 1, 37)) AS descricao,
       P.clno                   AS clno,
       C.name                   AS centroLucro,
       P.mfno                   AS vendno,
       V.sname                  AS fornecedor,
       P.typeno                 AS typeno,
       T.name                   AS tipo,
       ROUND(sp / 100, 2)       AS preco
FROM sqldados.prd          AS P
  INNER JOIN sqldados.cl   AS C
	       ON P.clno = C.no
  INNER JOIN sqldados.type AS T
	       ON P.typeno = T.no
  INNER JOIN sqldados.vend AS V
	       ON P.mfno = V.no
WHERE (P.clno = @CLNO OR P.deptno = @CLNO OR P.groupno = @CLNO OR @CLNO = 0)
  AND (P.mfno = @VENDNO OR @VENDNO = 0)
  AND (P.typeno = @TYPENO OR @TYPENO = 0);

DROP TEMPORARY TABLE IF EXISTS T_PRICE;
CREATE TEMPORARY TABLE T_PRICE (
  PRIMARY KEY (prdno)
)
SELECT V.prdno                                                            AS prdno,
       CAST(IF(V.promo_validate < @HOJE, NULL, V.promo_validate) AS DATE) AS validade,
       ROUND(V.refprice / 100, 2)                                         AS refPrice,
       ROUND(IF(V.promo_validate < @HOJE, NULL, V.promo_price) / 100, 2)  AS promoPrice
FROM sqldados.prp  AS V
  INNER JOIN T_PRD AS P
	       ON P.prdno = V.prdno AND V.storeno = 10;


DROP TEMPORARY TABLE IF EXISTS T_PROMO;
CREATE TEMPORARY TABLE T_PROMO (
  PRIMARY KEY (promono, prdno)
)
SELECT P.no                                AS promono,
       I.prdno                             AS prdno,
       P.name                              AS descricaoPromocao,
       IF(type = 0, 'PERCENTUAL', 'VALOR') AS tipo,
       CAST(enddate AS DATE)               AS validade,
       ROUND(P.auxLong1 / 100, 2)          AS percentual,
       ROUND(I.price / 100, 2)             AS precoPromocional
FROM sqldados.promo            AS P
  INNER JOIN sqldados.promoprd AS I
	       ON I.promono = P.no
WHERE P.begdate <= @HOJE
  AND P.enddate >= @HOJE
GROUP BY promono, prdno;

SELECT TRIM(P.prdno)                            AS codigo,
       descricao,
       clno                                     AS clno,
       centroLucro                              AS centroLucro,
       vendno                                   AS vendno,
       fornecedor                               AS fornecedor,
       P.typeno                                 AS typeno,
       P.tipo                                   AS tipoProduto,
       preco,
       IFNULL(V.validade, M.validade)           AS validade,
       IFNULL(V.promoPrice, M.precoPromocional) AS precoPromocional,
       CASE
	 WHEN V.promoPrice IS NOT NULL
	   THEN 'PRECIFICACAO'
	 WHEN M.precoPromocional IS NOT NULL
	   THEN 'PROMOCAO'
	 ELSE ''
       END                                      AS origemPromocao,
       refPrice,
       promoPrice,
       promono,
       descricaoPromocao,
       M.tipo                                   AS tipoPromocao,
       percentual
FROM T_PRD           AS P
  INNER JOIN T_PRICE AS V
	       ON P.prdno = V.prdno
  LEFT JOIN  T_PROMO    M
	       ON M.prdno = V.prdno
