USE sqldados;

DO @DIVENDA := IF(:diVenda = 0, CURRENT_DATE * 1, :diVenda);
DO @DFVENDA := IF(:dfVenda = 0, CURRENT_DATE * 1, :dfVenda);

SELECT storeno                 AS loja,
       TRIM(prdno) * 1         AS codigo,
       grade                   AS grade,
       CAST(MAX(date) AS DATE) AS date,
       SUM(quant / 1000)       AS quant
FROM vendaDate
WHERE date BETWEEN @DIVENDA AND @DFVENDA
GROUP BY storeno, prdno, grade
