USE sqldados;

DO @HOJE := CURRENT_DATE * 1;
DO @CODIGO := :codigo;
DO @PRDNO := LPAD(@CODIGO, 16, ' ');
DO @VENDNO := :vendno;
DO @TYPENO := :typeno;
DO @CLNO := LPAD(:clno, 6, '0');
DO @CLNF := CASE
	      WHEN @CLNO LIKE '%0000'
		THEN CONCAT(MID(@CLNO, 1, 2), '9999')
	      WHEN @CLNO LIKE '%00'
		THEN CONCAT(MID(@CLNO, 1, 4), '99')
	      ELSE @CLNO
	    END;
DO @DATA := :dataAlteracao;

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
WHERE (P.clno BETWEEN @CLNO AND @CLNF OR @CLNO = 0)
  AND (P.mfno = @VENDNO OR @VENDNO = 0)
  AND (P.typeno = @TYPENO OR @TYPENO = 0)
  AND (P.no = @PRDNO OR @CODIGO = 0);

DROP TEMPORARY TABLE IF EXISTS T_PRECO_ATUAL;
CREATE TEMPORARY TABLE T_PRECO_ATUAL (
  PRIMARY KEY (storeno, prdno)
)
SELECT storeno,
       prdno,
       promo_price
FROM sqldados.prp
  INNER JOIN T_PRD
	       USING (prdno)
WHERE storeno = 10
  AND promo_validate >= @HOJE;

DROP TEMPORARY TABLE IF EXISTS T_PRECO_HIS;
CREATE TEMPORARY TABLE T_PRECO_HIS (
  PRIMARY KEY (storeno, prdno, datetime),
  datetime VARCHAR(20)
)
SELECT H.storeno,
       H.prdno,
       IF(H.promo_validate >= H.date, promo_price, refprice) AS promo_price,
       CONCAT(LPAD(date, 10, '0'), LPAD(time, 10, '0'))      AS datetime,
       userno
FROM sqldados.prphis AS H
  INNER JOIN T_PRD
	       USING (prdno)
WHERE H.date >= @DATA
  AND H.storeno = 10;

DROP TEMPORARY TABLE IF EXISTS T_PRECO_ALTER;
CREATE TEMPORARY TABLE T_PRECO_ALTER (
  PRIMARY KEY (storeno, prdno, datetime)
)
SELECT H.storeno,
       H.prdno,
       MAX(datetime) AS datetime
FROM T_PRECO_HIS           AS H
  INNER JOIN T_PRECO_ATUAL AS P
	       ON P.storeno = H.storeno AND P.prdno = H.prdno AND P.promo_price = H.promo_price
GROUP BY H.storeno, H.prdno;

DROP TEMPORARY TABLE IF EXISTS T_PRECO_OLD;
CREATE TEMPORARY TABLE T_PRECO_OLD (
  PRIMARY KEY (storeno, prdno, datetime)
)
SELECT H.storeno,
       H.prdno,
       MAX(datetime) AS datetime
FROM T_PRECO_HIS           AS H
  INNER JOIN T_PRECO_ATUAL AS P
	       ON P.storeno = H.storeno AND P.prdno = H.prdno AND P.promo_price != H.promo_price
GROUP BY H.storeno, H.prdno;

DROP TEMPORARY TABLE IF EXISTS T_PRECO_01;
CREATE TEMPORARY TABLE T_PRECO_01 (
  PRIMARY KEY (storeno, prdno)
)
SELECT storeno,
       prdno,
       CAST(MID(datetime, 1, 10) * 1 AS DATE) AS date,
       SEC_TO_TIME(MID(datetime, 11, 10) * 1) AS time,
       H.userno,
       H.promo_price / 100                    AS promo_price
FROM T_PRECO_HIS           AS H
  INNER JOIN T_PRECO_ALTER AS A
	       USING (storeno, prdno, datetime);

DROP TEMPORARY TABLE IF EXISTS T_PRECO_02;
CREATE TEMPORARY TABLE T_PRECO_02 (
  PRIMARY KEY (storeno, prdno)
)
SELECT storeno,
       prdno,
       CAST(MID(datetime, 1, 10) * 1 AS DATE) AS date,
       SEC_TO_TIME(MID(datetime, 11, 10) * 1) AS time,
       H.userno,
       H.promo_price / 100                    AS promo_price
FROM T_PRECO_HIS         AS H
  INNER JOIN T_PRECO_OLD AS A
	       USING (storeno, prdno, datetime);

DROP TEMPORARY TABLE IF EXISTS T_PRECO_ALTERACAO;
CREATE TEMPORARY TABLE T_PRECO_ALTERACAO (
  PRIMARY KEY (prdno)
)
SELECT prdno,
       N.date        AS date,
       N.time        AS time,
       N.userno,
       O.promo_price AS promo_priceOld,
       N.promo_price AS promo_priceNew
FROM T_PRECO_01         AS N
  INNER JOIN T_PRECO_02 AS O
	       USING (storeno, prdno);

SELECT LPAD(TRIM(P.prdno), 6, '0') AS codigo,
       descricao                   AS descricao,
       date                        AS alteracao,
       promo_priceNew              AS precoNew,
       promo_priceOld              AS precoOld,
       clno                        AS clno,
       centroLucro                 AS centroLucro,
       vendno                      AS vendno,
       fornecedor                  AS fornecedor,
       P.typeno                    AS typeno,
       P.tipo                      AS tipoProduto
FROM T_PRD                     AS P
  INNER JOIN T_PRECO_ALTERACAO AS V
	       USING (prdno)