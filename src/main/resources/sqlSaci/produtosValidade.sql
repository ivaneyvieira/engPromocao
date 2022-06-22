DO @FILTRO := :filtro;
DO @FILTROLIKE := IF(@FILTRO REGEXP '^[0-9]+$', '%', CONCAT('%', @FILTRO, '%'));
DO @CODIGO := IF(@FILTRO REGEXP '^[0-9]+$', @FILTRO, '0');
DO @PRDNO := LPAD(@CODIGO, 16, ' ');
DO @VENDNO := IF(@FILTRO REGEXP '^[0-9]+$', @FILTRO, '0');
DO @TYPENO := IF(@FILTRO REGEXP '^[0-9]+$', @FILTRO, '0');
DO @CLNO := IF(@FILTRO REGEXP '^[0-9]{6}$', @FILTRO, '0');
DO @CLNF := CASE
	      WHEN @CLNO LIKE '%0000'
		THEN CONCAT(MID(@CLNO, 1, 2), '9999')
	      WHEN @CLNO LIKE '%00'
		THEN CONCAT(MID(@CLNO, 1, 4), '99')
	      ELSE @CLNO
	    END;

DROP TEMPORARY TABLE IF EXISTS T_PRD;
CREATE TEMPORARY TABLE T_PRD (
  PRIMARY KEY (prdno)
)
SELECT LPAD(P.no * 1, 6, '0')   AS prdno,
       TRIM(MID(P.name, 1, 37)) AS descricao,
       P.clno                   AS clno,
       C.name                   AS centroLucro,
       P.mfno                   AS vendno,
       V.sname                  AS fornecedor,
       P.typeno                 AS typeno,
       T.name                   AS tipo,
       ROUND(sp / 100, 2)       AS preco,
       CASE P.tipoGarantia
	 WHEN 0
	   THEN 'Dia'
	 WHEN 1
	   THEN 'Semana'
	 WHEN 2
	   THEN 'Mês'
	 WHEN 3
	   THEN 'Ano'
       END                      AS tipoValidade,
       P.garantia               AS mesesValidade
FROM sqldados.prd          AS P
  INNER JOIN sqldados.cl   AS C
	       ON P.clno = C.no
  INNER JOIN sqldados.type AS T
	       ON P.typeno = T.no
  INNER JOIN sqldados.vend AS V
	       ON P.mfno = V.no
WHERE ((P.clno BETWEEN @CLNO AND @CLNF) OR (P.mfno = @VENDNO) OR (P.typeno = @TYPENO) OR
       (P.no = @PRDNO) OR @CODIGO = '0')
  AND ((C.name LIKE @FILTROLIKE) OR (V.name LIKE @FILTROLIKE) OR (T.name LIKE @FILTROLIKE) OR
       (P.name LIKE @FILTROLIKE));


SELECT LPAD(TRIM(P.prdno), 6, '0') AS codigo,
       descricao                   AS descricao,
       clno                        AS clno,
       centroLucro                 AS centroLucro,
       vendno                      AS vendno,
       fornecedor                  AS fornecedor,
       P.typeno                    AS typeno,
       P.tipo                      AS tipoProduto,
       tipoValidade                AS tipoValidade,
       P.mesesValidade             AS mesesValidade
FROM T_PRD AS P