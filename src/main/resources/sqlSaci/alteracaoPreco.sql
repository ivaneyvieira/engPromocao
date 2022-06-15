USE sqldados;

SET SQL_MODE = '';

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

DO @DATAI := :dataInicial;
DO @DATAF := :dataFinal;

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
  AND (P.no = @PRDNO OR @CODIGO = '');

DO @DT := @DATAI;
DO @DI := CAST(SUBDATE(20220301, 365 * 1) * 1 AS UNSIGNED);


DROP TEMPORARY TABLE IF EXISTS T_HIS_TEMP;
CREATE TEMPORARY TABLE T_HIS_TEMP
SELECT storeno,
       prdno,
       MAX(CAST(CONCAT(LPAD(H.date, 10, '0'), LPAD(H.time, 10, '0')) AS CHAR)) AS datetime
FROM sqldados.prphis AS H
WHERE storeno = 10
  AND date >= @DI
  AND date < @DT
GROUP BY storeno, prdno;

DROP TEMPORARY TABLE IF EXISTS T_HIS;
CREATE TEMPORARY TABLE T_HIS (
  PRIMARY KEY (storeno, prdno, date, time),
  date INT(10),
  time INT(10)
)
SELECT storeno,
       prdno,
       MID(datetime, 1, 10) * 1  AS date,
       MID(datetime, 11, 10) * 1 AS time
FROM T_HIS_TEMP;


DROP TEMPORARY TABLE IF EXISTS T_PRD_HIS;
CREATE TEMPORARY TABLE T_PRD_HIS (
  PRIMARY KEY (prdno)
)
SELECT prdno,
       refprice                                                AS refpriceAnt,
       IF(H.promo_validate >= H.date, H.promo_price, refprice) AS promoPreco
FROM sqldados.prphis AS H
  INNER JOIN T_HIS
	       USING (storeno, prdno, date, time)
GROUP BY prdno;

DO @CHAVE := '';
DO @COD := '';
DO @PRECO := '';
DO @NUMERO := 0;

DROP TEMPORARY TABLE IF EXISTS T_PRECO_HIS;
CREATE TEMPORARY TABLE T_PRECO_HIS (
  PRIMARY KEY (storeno, prdno, datetime),
  datetime VARCHAR(20)
)
SELECT H.storeno,
       @CHAVE                                                                     AS chaveAnt,
       @COD := H.prdno                                                            AS prdno,
       @PRECO := H.refprice                                                       AS precoSaci,
       @VALIDADE := IF(H.promo_validate = 0, NULL, H.promo_validate)              AS validadeAnt,
       @NUMERO := @NUMERO + IF(@CHAVE = CAST(CONCAT(@PRECO, @COD) AS CHAR), 0, 1) AS numero,
       @VALIDADE := IF(H.promo_validate = 0, NULL, H.promo_validate)              AS promo_validate,
       @CHAVE := CAST(CONCAT(@PRECO, @COD) AS CHAR)                               AS chave,
       IF(H.promo_validate >= H.date, promo_price, H.refprice) / 100              AS promo_price,
       P.promoPreco / 100                                                         AS promo_priceAnt,
       @PRECO / 100                                                               AS refprice,
       P.refpriceAnt / 100                                                        AS refpriceAnt,
       @PRECO = P.refpriceAnt                                                     AS igual,
       CAST(CONCAT(LPAD(H.date, 10, '0'), LPAD(H.time, 10, '0')) AS CHAR)         AS datetime,
       H.userno,
       H.date,
       H.time
FROM sqldados.prphis   AS H
  LEFT JOIN  T_PRD_HIS AS P
	       USING (prdno)
  INNER JOIN T_PRD
	       USING (prdno)
WHERE H.date BETWEEN @DATAI AND @DATAF
  AND H.storeno = 10
  AND IFNULL(P.refpriceAnt, 0) != H.refprice
ORDER BY H.storeno, H.prdno, H.date, H.time;

DROP TEMPORARY TABLE IF EXISTS T_PRECO_HIS_MAX;
CREATE TEMPORARY TABLE T_PRECO_HIS_MAX (
  PRIMARY KEY (storeno, prdno, date, time)
)
SELECT H.storeno,
       H.prdno,
       H.date,
       MAX(H.time) AS time
FROM T_PRECO_HIS AS H
GROUP BY H.storeno, H.prdno, H.date;

DROP TEMPORARY TABLE IF EXISTS T_PRECO_HIS02;
CREATE TEMPORARY TABLE T_PRECO_HIS02 (
  PRIMARY KEY (storeno, prdno, datetime),
  datetime VARCHAR(20)
)
SELECT storeno,
       chaveAnt,
       prdno,
       precoSaci,
       promo_validate,
       numero,
       chave,
       promo_price,
       promo_priceAnt,
       refprice,
       refpriceAnt,
       datetime,
       userno,
       date,
       time
FROM T_PRECO_HIS AS H
  INNER JOIN T_PRECO_HIS_MAX
	       USING (storeno, prdno, date, time);

SELECT LPAD(TRIM(P.prdno), 6, '0')                            AS codigo,
       descricao                                              AS descricao,
       CAST(MID(MIN(datetime), 1, 10) * 1 AS DATE)            AS data,
       SEC_TO_TIME(MID(MIN(datetime), 11, 10) * 1)            AS time,
       userno                                                 AS userno,
       U.name                                                 AS usuario,
       refprice                                               AS refprice,
       refpriceAnt                                            AS refpriceAnt,
       promo_price                                            AS promo_price,
       promo_priceAnt                                         AS promo_priceAnt,
       CAST(promo_validate AS DATE)                           AS dataPromocao,
       ROUND((refPrice - promo_price) * 100.00 / refPrice, 2) AS desconto,
       clno                                                   AS clno,
       centroLucro                                            AS centroLucro,
       vendno                                                 AS vendno,
       fornecedor                                             AS fornecedor,
       P.typeno                                               AS typeno,
       P.tipo                                                 AS tipoProduto
FROM T_PRD                  AS P
  INNER JOIN T_PRECO_HIS02    AS V
	       USING (prdno)
  INNER JOIN sqldados.users AS U
	       ON U.no = userno
GROUP BY numero