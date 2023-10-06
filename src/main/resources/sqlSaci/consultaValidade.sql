USE sqldados;

SET sql_mode = '';

DO @QUERY := :query;
DO @QUERYLIKE := CONCAT(@QUERY, '%');

DO @LISTVEND := REPLACE(:listVend, ' ', '');
DO @TRIBUTACAO := :tributacao;
DO @TYPENO := :typeno;
DO @CLNO := :clno;

DROP TEMPORARY TABLE IF EXISTS T_PNAME;
CREATE TEMPORARY TABLE T_PNAME
SELECT prdno, name, TRIM(MID(name, 61, 100)) AS linha2
FROM sqldados.prdnam
WHERE name LIKE '%VAL%'
HAVING linha2 LIKE '%VAL%M%';

DROP TEMPORARY TABLE IF EXISTS T_VALNAME;
CREATE TEMPORARY TABLE T_VALNAME
(
    PRIMARY KEY (prdno)
)
SELECT prdno, name, linha2, TRIM(REPLACE(SUBSTRING_INDEX(linha2, ':', -1), 'M', '')) * 1 AS validade
FROM T_PNAME;

DROP TEMPORARY TABLE IF EXISTS T_VALCAD;
CREATE TEMPORARY TABLE T_VALCAD
(
    PRIMARY KEY (prdno)
)
SELECT P.no AS prdno, P.garantia AS mesesValidade
FROM sqldados.prd AS P
WHERE P.tipoGarantia = 2;

DROP TEMPORARY TABLE IF EXISTS T_MESTRE;
CREATE TEMPORARY TABLE T_MESTRE
(
    PRIMARY KEY (prdno)
)
SELECT prdno
FROM T_VALNAME
UNION
DISTINCT
SELECT prdno
FROM T_VALCAD;

DROP TEMPORARY TABLE IF EXISTS T_ULT_INVNO;
CREATE TEMPORARY TABLE T_ULT_INVNO
(
    PRIMARY KEY (prdno, grade)
)
SELECT prdno, grade, MAX(invno) AS invno
FROM sqldados.iprd AS I
         INNER JOIN inv AS N USING (invno)
         INNER JOIN T_MESTRE AS TM USING (prdno)
WHERE N.storeno = 4
  AND N.bits & POW(2, 4) = 0
  AND N.type = 0
GROUP BY prdno, grade;

DROP TEMPORARY TABLE IF EXISTS T_ULT_NFE;
CREATE TEMPORARY TABLE T_ULT_NFE
(
    PRIMARY KEY (prdno, grade)
)
SELECT prdno, grade, N.invno, CAST(N.date AS DATE) AS data, I.qtty / 1000 AS qtty
FROM T_ULT_INVNO
         INNER JOIN sqldados.inv AS N USING (invno)
         INNER JOIN sqldados.iprd AS I USING (invno, prdno, grade)
GROUP BY prdno, grade;

DROP TEMPORARY TABLE IF EXISTS T_SALDO;
CREATE TEMPORARY TABLE T_SALDO
(
    PRIMARY KEY (prdno)
)
SELECT prdno, SUM(qtty_varejo / 1000) AS saldo, SUM(IF(S.storeno = 4, qtty_varejo / 1000, 0.000)) AS saldoMF
FROM sqldados.stk AS S
         INNER JOIN T_MESTRE AS M USING (prdno)
WHERE S.storeno IN (2, 3, 4, 5, 6, 7, 8)
GROUP BY prdno;

DROP TEMPORARY TABLE IF EXISTS T_VALCOMPARA;
CREATE TEMPORARY TABLE T_VALCOMPARA
SELECT TRIM(M.prdno) * 1          AS codigo,
       M.prdno                    AS prdno,
       P.mfno                     AS vendno,
       P.typeno                   AS typeno,
       P.clno                     AS clno,
       TRIM(MID(P.name, 1, 37))   AS descricao,
       IFNULL(saldo, 0)           AS estoque,
       IFNULL(saldoMF, 0)         AS estoqueMF,
       IFNULL(N.validade, 0)      AS validade_descricao,
       IFNULL(C.mesesValidade, 0) AS validade_cadastro
FROM T_MESTRE AS M
         LEFT JOIN T_VALNAME AS N USING (prdno)
         LEFT JOIN T_VALCAD AS C USING (prdno)
         LEFT JOIN T_SALDO AS S USING (prdno)
         INNER JOIN sqldados.prd AS P ON P.no = M.prdno
WHERE (FIND_IN_SET(P.mfno, @LISTVEND) OR @LISTVEND = '')
  AND (P.typeno = @TYPENO OR @TYPENO = 0)
  AND (P.clno = @CLNO OR P.deptno = @CLNO OR P.groupno = @CLNO OR @CLNO = 0)
  AND (P.taxno = @TRIBUTACAO OR @TRIBUTACAO = '');

DROP TEMPORARY TABLE IF EXISTS T_VALCOMPARA_NUM;
CREATE TEMPORARY TABLE T_VALCOMPARA_NUM
SELECT codigo,
       prdno,
       vendno,
       typeno,
       clno,
       descricao,
       estoque,
       estoqueMF,
       validade_descricao,
       validade_cadastro,
       validade_descricao - validade_cadastro AS diferenca,
       CASE
           WHEN validade_cadastro = validade_descricao THEN 1
           WHEN validade_cadastro = 0 THEN 2
           WHEN validade_descricao = 0 THEN 3
           ELSE 4
           END                                AS tipo
FROM T_VALCOMPARA;

SELECT codigo,
       T.prdno,
       vendno,
       T.typeno,
       T.clno,
       P.grade,
       descricao,
       estoque,
       estoqueMF,
       validade_descricao,
       validade_cadastro,
       diferenca,
       tipo,
       CAST(MAX(MID(P.localizacao, 1, 4)) AS CHAR) AS localizacao,
       invno                                       AS invno,
       data                                        AS ultimaEntrada,
       qtty                                        AS qtty
FROM T_VALCOMPARA_NUM AS T
         LEFT JOIN sqldados.prdloc AS P ON P.prdno = T.prdno AND P.storeno = 4
         LEFT JOIN T_ULT_NFE AS N ON N.prdno = P.prdno AND N.grade = P.grade
WHERE (tipo = :tipo OR :tipo = 0)
  AND LPAD(TRIM(T.prdno), 6, '0') NOT BETWEEN '980000' AND '999999'
  AND CASE :marca
          WHEN 'T' THEN TRUE
          WHEN 'N' THEN MID(descricao, 1, 1) NOT IN ('.', '*', '!', '*', ']', ':', '#')
          WHEN 'S' THEN MID(descricao, 1, 1) IN ('.', '*', '!', '*', ']', ':', '#')
    END
  AND (codigo LIKE @QUERY OR vendno LIKE @QUERY OR typeno LIKE @QUERY OR clno LIKE @QUERY OR P.grade LIKE @QUERYLIKE OR
       descricao LIKE @QUERYLIKE OR localizacao LIKE @QUERYLIKE OR ROUND(estoque) LIKE @QUERY OR
       ROUND(estoqueMF) LIKE @QUERY)
GROUP BY T.prdno, grade
