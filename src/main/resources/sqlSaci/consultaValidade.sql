USE sqldados;

DO @QUERY := :query;
DO @CODIGO := IF(@QUERY REGEXP '^[0-9]+$', @QUERY, 0);
DO @QUERYLIKE := IF(@QUERY REGEXP '^[0-9]+$', '', CONCAT(@QUERY, '%'));

DROP TEMPORARY TABLE IF EXISTS T_PNAME;
CREATE TEMPORARY TABLE T_PNAME
SELECT prdno,
       name,
       TRIM(MID(name, 61, 100)) AS linha2
FROM sqldados.prdnam
WHERE name LIKE '%VAL%'
HAVING linha2 LIKE '%VAL%M%';

DROP TEMPORARY TABLE IF EXISTS T_VALNAME;
CREATE TEMPORARY TABLE T_VALNAME (
  PRIMARY KEY (prdno)
)
SELECT prdno,
       name,
       linha2,
       TRIM(REPLACE(SUBSTRING_INDEX(linha2, ':', -1), 'M', '')) * 1 AS validade
FROM T_PNAME;

DROP TEMPORARY TABLE IF EXISTS T_VALCAD;
CREATE TEMPORARY TABLE T_VALCAD (
  PRIMARY KEY (prdno)
)
SELECT P.no AS prdno, P.garantia AS mesesValidade
FROM sqldados.prd AS P
WHERE P.tipoGarantia = 2;

DROP TEMPORARY TABLE IF EXISTS T_MESTRE;
CREATE TEMPORARY TABLE T_MESTRE (
  PRIMARY KEY (prdno)
)
SELECT prdno
FROM T_VALNAME
UNION
DISTINCT
SELECT prdno
FROM T_VALCAD;

DROP TEMPORARY TABLE IF EXISTS T_SALDO;
CREATE TEMPORARY TABLE T_SALDO (
  PRIMARY KEY (prdno)
)
SELECT prdno, SUM(qtty_varejo / 1000) AS saldo
FROM sqldados.stk     AS S
  INNER JOIN T_MESTRE AS M
	       USING (prdno)
WHERE S.storeno IN (2, 3, 4, 5, 6, 7)
GROUP BY prdno;

DROP TEMPORARY TABLE IF EXISTS T_VALCOMPARA;
CREATE TEMPORARY TABLE T_VALCOMPARA
SELECT TRIM(M.prdno) * 1          AS codigo,
       M.prdno                    AS prdno,
       TRIM(MID(P.name, 1, 37))   AS descricao,
       IFNULL(saldo, 0)           AS estoque,
       IFNULL(N.validade, 0)      AS validade_descricao,
       IFNULL(C.mesesValidade, 0) AS validade_cadastro
FROM T_MESTRE             AS M
  LEFT JOIN  T_VALNAME    AS N
	       USING (prdno)
  LEFT JOIN  T_VALCAD     AS C
	       USING (prdno)
  LEFT JOIN  T_SALDO      AS S
	       USING (prdno)
  INNER JOIN sqldados.prd AS P
	       ON P.no = M.prdno
WHERE (prdno = LPAD(@CODIGO, 16, ' ') OR @CODIGO = '0') AND
      (P.name LIKE @QUERYLIKE OR @QUERYLIKE = '');

DROP TEMPORARY TABLE IF EXISTS T_VALCOMPARA_NUM;
CREATE TEMPORARY TABLE T_VALCOMPARA_NUM
SELECT codigo,
       prdno,
       descricao,
       estoque,
       validade_descricao,
       validade_cadastro,
       validade_descricao - validade_cadastro AS diferenca,
       CASE
	 WHEN validade_cadastro = validade_descricao
	   THEN 1
	 WHEN validade_cadastro = 0
	   THEN 2
	 WHEN validade_descricao = 0
	   THEN 3
	 ELSE 4
       END                                    AS tipo
FROM T_VALCOMPARA;

SELECT codigo,
       T.prdno,
       grade,
       descricao,
       estoque,
       validade_descricao,
       validade_cadastro,
       diferenca,
       tipo,
       CAST(MAX(MID(P.localizacao, 1, 4)) AS CHAR) AS localizacao
FROM T_VALCOMPARA_NUM       AS T
  LEFT JOIN sqldados.prdloc AS P
	      ON P.prdno = T.prdno AND P.storeno = 4
WHERE (tipo = :tipo OR :tipo = 0)
  AND LPAD(TRIM(T.prdno), 6, '0') NOT BETWEEN '980000' AND '999999'
GROUP BY T.prdno, grade