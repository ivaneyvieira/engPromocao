DO @CODIGO := :codigo;
DO @PRDNO := LPAD(@CODIGO, 16, ' ');
DO @VENDNO := :vendno;
DO @TRIBUTACAO := :tributacao;

SELECT prdno                     AS prdno,
       LPAD(TRIM(prdno), 6, '0') AS codigo,
       TRIM(MID(PD.name, 1, 37)) AS descricao,
       PD.mfno                   AS vendno,
       V.sname                   AS fornecedor,
       ROUND(adm / 100, 2)       AS cpmf,
       taxno                     AS tributacao
FROM sqldados.prp          AS P
  INNER JOIN sqldados.prd  AS PD
	       ON PD.no = P.prdno
  INNER JOIN sqldados.vend AS V
	       ON PD.mfno = V.no
WHERE storeno = 10
  AND (P.prdno = @PRDNO OR @CODIGO = 0)
  AND (PD.mfno = @VENDNO OR @VENDNO = 0)
  AND (PD.taxno = @TRIBUTACAO OR @TRIBUTACAO = '')
