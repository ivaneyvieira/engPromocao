USE sqldados;

DO @DIVENDA := IF(:diVenda = 0, CURRENT_DATE * 1, :diVenda);
DO @DFVENDA := IF(:dfVenda = 0, CURRENT_DATE * 1, :dfVenda);

DROP TEMPORARY TABLE IF EXISTS V;
CREATE TEMPORARY TABLE V
(
    PRIMARY KEY (storeno, prdno, grade)
)
SELECT X.storeno, X.prdno, X.grade, SUM(X.qtty) AS quant
FROM sqldados.nf AS N
         INNER JOIN sqldados.xaprd2 AS X USING (storeno, pdvno, xano)
WHERE tipo IN (3, 0)
  AND status <> 1
  AND (X.storeno IN (1, 2, 3, 4, 5, 6))
  AND issuedate = CURRENT_DATE * 1
  AND issuedate >= @DFVENDA
GROUP BY X.storeno, X.prdno, X.grade;

DROP TEMPORARY TABLE IF EXISTS D;
CREATE TEMPORARY TABLE D
(
    PRIMARY KEY (storeno, prdno, grade)
)
SELECT storeno, prdno, grade, SUM(qtty) AS quant
FROM sqldados.xalog2
WHERE qtty < 0
  AND (storeno IN (1, 2, 3, 4, 5, 6))
  AND date = CURRENT_DATE * 1
  AND date >= @DFVENDA
GROUP BY storeno, prdno, grade;

DROP TEMPORARY TABLE IF EXISTS T;
CREATE TEMPORARY TABLE T
SELECT *
FROM V
UNION
DISTINCT
SELECT *
FROM D;

DROP TEMPORARY TABLE IF EXISTS TT01;
CREATE TEMPORARY TABLE TT01
(
    PRIMARY KEY (storeno, prdno, grade)
)
SELECT X.storeno, X.prdno, X.grade, CURRENT_DATE AS date, ROUND(SUM(X.quant)) AS quant
FROM T AS X
GROUP BY X.storeno, X.prdno, X.grade;


DROP TEMPORARY TABLE IF EXISTS TT02;
CREATE TEMPORARY TABLE TT02
SELECT storeno, prdno, grade, CAST(MAX(date) AS DATE) AS date, SUM(quant) AS quant
FROM vendaDate
WHERE date BETWEEN @DIVENDA AND @DFVENDA
GROUP BY storeno, prdno, grade;

SELECT storeno                                     AS loja,
       TRIM(prdno) * 1                             AS codigo,
       grade                                       AS grade,
       IFNULL(TT01.date, TT02.date)                AS date,
       (TT02.quant + IFNULL(TT01.quant, 0)) / 1000 AS quant
FROM TT02
         LEFT JOIN TT01 USING (storeno, prdno, grade)

