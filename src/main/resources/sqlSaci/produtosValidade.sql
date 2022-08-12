USE sqldados;

DO @TIPODIF := :tipoDiferenca;
DO @TIPOVAL := :tipoValidade;
DO @FILTRO := :filtro;
DO @FILTRO_LIKE := CONCAT(@FILTRO, '%');
DO @CODIGO := IF(@FILTRO REGEXP '^[0-9]{6}$', @FILTRO*1, 0);
DO @PRDNO := LPAD(@CODIGO, 16, ' ');
DO @VENDNO := @FILTRO * 1;
DO @TYPENO := @FILTRO * 1;
DO @CL := @FILTRO * 1;
DO @CLNO := LPAD(@CL, 6, '0');
DO @CLNF := CASE
	      WHEN @CLNO LIKE '%0000'
		THEN CONCAT(MID(@CLNO, 1, 2), '9999')
	      WHEN @CLNO LIKE '%00'
		THEN CONCAT(MID(@CLNO, 1, 4), '99')
	      ELSE @CLNO
	    END;

/*
SELECT @FILTRO, @FILTRO_LIKE, @CODIGO, @PRDNO, @VENDNO, @CLNO, @CLNF
*/

/***********************************************************************************************/

DROP TEMPORARY TABLE IF EXISTS T_PNAME;
CREATE TEMPORARY TABLE T_PNAME
SELECT prdno,
       name,
       TRIM(MID(name, 61, 100)) AS linha2
FROM sqldados.prdnam
WHERE name LIKE '%VAL%'
  AND TRIM(MID(name, 61, 100)) LIKE '%VAL%M%';

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

DROP TEMPORARY TABLE IF EXISTS T_VALCOMPARA;
CREATE TEMPORARY TABLE T_VALCOMPARA
SELECT TRIM(M.prdno) * 1          AS codigo,
       M.prdno                    AS prdno,
       IFNULL(N.validade, 0)      AS validade_descricao,
       IFNULL(C.mesesValidade, 0) AS validade_cadastro
FROM T_MESTRE         AS M
  LEFT JOIN T_VALNAME AS N
	      USING (prdno)
  LEFT JOIN T_VALCAD  AS C
	      USING (prdno);

DROP TEMPORARY TABLE IF EXISTS T_VALCOMPARA_NUM;
CREATE TEMPORARY TABLE T_VALCOMPARA_NUM (
  PRIMARY KEY (prdno)
)
SELECT codigo,
       prdno,
       validade_descricao,
       validade_cadastro,
       validade_descricao - validade_cadastro AS diferenca,
       CASE
	 WHEN validade_cadastro = validade_descricao
	   THEN 1
	 WHEN validade_cadastro != validade_descricao
	   THEN 2
	 ELSE 0
       END                                    AS tipo
FROM T_VALCOMPARA;

/***********************************************************************************************/

DROP TEMPORARY TABLE IF EXISTS T_PRD;
CREATE TEMPORARY TABLE T_PRD (
  PRIMARY KEY (prdno)
)
SELECT LPAD(P.no * 1, 6, '0')    AS prdno,
       TRIM(MID(P.name, 1, 37))  AS descricao,
       P.clno                    AS clno,
       C.name                    AS centroLucro,
       P.mfno                    AS vendno,
       V.sname                   AS fornecedor,
       P.typeno                  AS typeno,
       T.name                    AS tipo,
       ROUND(sp / 100, 2)        AS preco,
       CASE P.tipoGarantia
	 WHEN 0
	   THEN 'Dia'
	 WHEN 1
	   THEN 'Semana'
	 WHEN 2
	   THEN 'Mês'
	 WHEN 3
	   THEN 'Ano'
       END                       AS tipoValidade,
       P.garantia                AS mesesValidade,
       TRIM(MID(N.name, 1, 60))  AS descricaoCompleta1,
       TRIM(MID(N.name, 61, 60)) AS descricaoCompleta2
FROM sqldados.prd             AS P
  INNER JOIN sqldados.cl      AS C
	       ON P.clno = C.no
  INNER JOIN sqldados.type    AS T
	       ON P.typeno = T.no
  INNER JOIN sqldados.vend    AS V
	       ON P.mfno = V.no
  LEFT JOIN  sqldados.prdnam  AS N
	       ON P.no = N.prdno
  LEFT JOIN  T_VALCOMPARA_NUM AS TV
	       ON TV.prdno = P.no
WHERE (IFNULL(TV.tipo, 0) = @TIPODIF OR @TIPODIF = 0)
  AND (IFNULL(tipoGarantia, 0) = @TIPOVAL OR @TIPOVAL = 4)
  AND ((P.no = @PRDNO) OR (P.name LIKE @FILTRO_LIKE) OR
       (P.mfno = @VENDNO) OR (V.sname LIKE @FILTRO_LIKE) OR
       (P.typeno = @TYPENO OR (T.name LIKE @FILTRO_LIKE)) OR
       (P.clno BETWEEN @CLNO AND @CLNF) OR (@FILTRO = ''));

SELECT LPAD(TRIM(P.prdno), 6, '0')    AS codigo,
       descricao                      AS descricao,
       clno                           AS clno,
       centroLucro                    AS centroLucro,
       vendno                         AS vendno,
       fornecedor                     AS fornecedor,
       P.typeno                       AS typeno,
       P.tipo                         AS tipoProduto,
       P.tipoValidade                 AS tipoValidade,
       P.mesesValidade                AS mesesValidade,
       IFNULL(descricaoCompleta1, '') AS descricaoCompleta1,
       IFNULL(descricaoCompleta2, '') AS descricaoCompleta2
FROM T_PRD AS P

