SET SQL_MODE = "";

DO @HOJE := CURRENT_DATE * 1;
DO @CODIGO := :codigo;
DO @PRDNO := LPAD(@CODIGO, 16, ' ');
DO @VENDNO := :vendno;
DO @TYPENO := :typeno;
DO @CLNO := LPAD(:clno, 6, '0');
DO @CLNF := CASE
                WHEN @CLNO LIKE '%0000' THEN CONCAT(MID(@CLNO, 1, 2), '9999')
                WHEN @CLNO LIKE '%00' THEN CONCAT(MID(@CLNO, 1, 4), '99')
                ELSE @CLNO
    END;

DROP TEMPORARY TABLE IF EXISTS T_PRD;
CREATE TEMPORARY TABLE T_PRD
(
    PRIMARY KEY (prdno)
)
SELECT P.no                     AS prdno,
       TRIM(MID(P.name, 1, 37)) AS descricao,
       P.clno                   AS clno,
       C.name                   AS centroLucro,
       P.mfno                   AS vendno,
       P.mfno_ref               AS refFornecedor,
       V.sname                  AS fornecedor,
       P.typeno                 AS typeno,
       T.name                   AS tipo,
       ROUND(sp / 100, 2)       AS preco
FROM sqldados.prd AS P
         INNER JOIN sqldados.cl AS C ON P.clno = C.no
         INNER JOIN sqldados.type AS T ON P.typeno = T.no
         INNER JOIN sqldados.vend AS V ON P.mfno = V.no
WHERE (P.clno BETWEEN @CLNO AND @CLNF OR @CLNO = 0)
  AND (P.mfno = @VENDNO OR @VENDNO = 0)
  AND (P.typeno = @TYPENO OR @TYPENO = 0)
  AND (P.no = @PRDNO OR @CODIGO = 0)
  AND CASE :marca
          WHEN 'T' THEN TRUE
          WHEN 'N' THEN MID(P.name, 1, 1) NOT IN ('.', '*', '!', '*', ']', ':', '#')
          WHEN 'S' THEN MID(P.name, 1, 1) IN ('.', '*', '!', '*', ']', ':', '#')
          ELSE FALSE
    END;

DROP TEMPORARY TABLE IF EXISTS T_STK;
CREATE TEMPORARY TABLE T_STK
(
    PRIMARY KEY (prdno)
)
SELECT prdno, SUM(ROUND(qtty_varejo / 1000)) AS estoque
FROM sqldados.stk AS S
         INNER JOIN T_PRD USING (prdno)
WHERE storeno IN (1, 2, 3, 4, 5, 6, 8)
GROUP BY prdno;

DROP TEMPORARY TABLE IF EXISTS T_PRICE;
CREATE TEMPORARY TABLE T_PRICE
(
    PRIMARY KEY (prdno)
)
SELECT V.prdno                        AS prdno,
       CAST(V.promo_validate AS DATE) AS validade,
       ROUND(V.refprice / 100, 2)     AS refPrice,
       ROUND(V.promo_price / 100, 2)  AS promoPrice,
       V.c4                           AS login
FROM sqldados.prp AS V
         INNER JOIN T_PRD AS P ON P.prdno = V.prdno AND V.storeno = 10;

SELECT LPAD(TRIM(P.prdno), 6, '0')                             AS codigo,
       descricao                                               AS descricao,
       CAST(IF(validade = 0, NULL, validade) AS DATE)          AS validade,
       refPrice                                                AS refPrice,
       ROUND((refPrice - V.promoPrice) * 100.00 / refPrice, 2) AS desconto,
       V.promoPrice                                            AS precoPromocional,
       clno                                                    AS clno,
       centroLucro                                             AS centroLucro,
       vendno                                                  AS vendno,
       fornecedor                                              AS fornecedor,
       P.typeno                                                AS typeno,
       P.tipo                                                  AS tipoProduto,
       IF(V.promoPrice IS NOT NULL, 'PROMOCAO', 'BASE')        AS origemPromocao,
       V.login                                                 AS login,
       P.refFornecedor                                         AS refFornecedor,
       IFNULL(estoque, 0)                                      AS estoque
FROM T_PRD AS P
         LEFT JOIN T_STK AS S USING (prdno)
         INNER JOIN T_PRICE AS V USING (prdno)
WHERE IF(V.validade >= @HOJE, 'PROMOCAO', 'BASE') IN (:tipoLista)
  AND IF(:decimal99 = 'S', ROUND(refPrice * 100 - TRUNCATE(refPrice, 0) * 100) = 99, TRUE)