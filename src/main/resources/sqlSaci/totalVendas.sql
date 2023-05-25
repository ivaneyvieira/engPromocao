USE sqldados;

DO @DIVENDA := IF(:diVenda = 0, CURRENT_DATE * 1, :diVenda);
DO @DFVENDA := IF(:dfVenda = 0, CURRENT_DATE * 1, :dfVenda);

DROP TEMPORARY TABLE IF EXISTS T_NF_VENDA;
CREATE TEMPORARY TABLE T_NF_VENDA
(
    PRIMARY KEY (storeno, prdno, grade)
)
SELECT X.storeno, X.prdno, X.grade, issuedate as data, SUM(X.qtty) AS quant
FROM sqldados.nf AS N
         INNER JOIN sqldados.xaprd2 AS X USING (storeno, pdvno, xano)
WHERE tipo IN (3, 0)
  AND status <> 1
  AND (X.storeno IN (1, 2, 3, 4, 5, 6))
  AND issuedate = CURRENT_DATE * 1
GROUP BY X.storeno, X.prdno, X.grade;

DROP TEMPORARY TABLE IF EXISTS T_NF_DEVOLUCAO;
CREATE TEMPORARY TABLE T_NF_DEVOLUCAO
SELECT storeno, prdno, grade, date as data, SUM(qtty) AS quant
FROM sqldados.xalog2
WHERE qtty < 0
  AND (storeno IN (1, 2, 3, 4, 5, 6))
  AND date = CURRENT_DATE * 1
GROUP BY storeno, prdno, grade;

DROP TEMPORARY TABLE IF EXISTS T_NF_VENDA_DEV_ANALITICO;
CREATE TEMPORARY TABLE T_NF_VENDA_DEV_ANALITICO
SELECT storeno, prdno, grade, data, quant
FROM T_NF_VENDA
UNION
DISTINCT
SELECT storeno, prdno, grade, data, quant
FROM T_NF_DEVOLUCAO;

DROP TEMPORARY TABLE IF EXISTS T_NF_VENDA_DEV_SISTETICO;
CREATE TEMPORARY TABLE T_NF_VENDA_DEV_SISTETICO
SELECT X.storeno,
       X.prdno,
       X.grade,
       DATE_SUB(data, INTERVAL DAYOFMONTH(data) - 1 DAY) as date,
       ROUND(SUM(X.quant))                               AS quant
FROM T_NF_VENDA_DEV_ANALITICO AS X
GROUP BY X.storeno, X.prdno, X.grade, X.data
UNION
SELECT storeno, prdno, grade, DATE_SUB(date, INTERVAL DAYOFMONTH(date) - 1 DAY) as date, ROUND(SUM(quant)) AS quant
FROM vendaDate
WHERE date BETWEEN @DIVENDA AND @DFVENDA
GROUP BY storeno, prdno, grade, date;

SELECT storeno            AS loja,
       TRIM(prdno) * 1    AS codigo,
       grade              AS grade,
       cast(date as date) AS date,
       quant / 1000       AS quant
FROM T_NF_VENDA_DEV_SISTETICO
