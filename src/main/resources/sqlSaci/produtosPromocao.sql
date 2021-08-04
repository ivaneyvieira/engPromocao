DO @HOJE := CURRENT_DATE * 1;
DO @CODIGO := :codigo;
DO @PRDNO := LPAD(@CODIGO, 16, ' ');
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
  AND (P.typeno = @TYPENO OR @TYPENO = 0)
  AND (P.no = @PRDNO OR @CODIGO = 0);

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

SELECT LPAD(TRIM(P.prdno), 6, '0')                             AS codigo,
       descricao                                               AS descricao,
       V.validade                                              AS validade,
       refPrice                                                AS refPrice,
       ROUND((refPrice - V.promoPrice) * 100.00 / refPrice, 2) AS desconto,
       V.promoPrice                                            AS precoPromocional,
       clno                                                    AS clno,
       centroLucro                                             AS centroLucro,
       vendno                                                  AS vendno,
       fornecedor                                              AS fornecedor,
       P.typeno                                                AS typeno,
       P.tipo                                                  AS tipoProduto,
       IF(V.promoPrice IS NOT NULL, 'PROMOCAO', 'BASE')        AS origemPromocao
FROM T_PRD           AS P
  INNER JOIN T_PRICE AS V
	       USING (prdno)
WHERE IF(V.promoPrice IS NOT NULL, 'PROMOCAO', 'BASE') IN (:tipoLista)