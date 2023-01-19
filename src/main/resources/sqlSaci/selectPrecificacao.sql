DO @CODIGO := :codigo;
DO @PRDNO := LPAD(@CODIGO, 16, ' ');
DO @LISTVEND := REPLACE(:listVend, ' ', '');
DO @TRIBUTACAO := :tributacao;
DO @TYPENO := :typeno;
DO @CLNO := :clno;

SELECT prdno                                                                   AS prdno,
       LPAD(TRIM(prdno), 6, '0')                                               AS codigo,
       TRIM(MID(PD.name, 1, 37))                                               AS descricao,
       PD.mfno                                                                 AS vendno,
       V.sname                                                                 AS fornecedor,
       ROUND(adm / 100, 2)                                                     AS cpmf,
       taxno                                                                   AS tributacao,
       PD.typeno                                                               AS typeno,
       PD.clno                                                                 AS clno,
       ROUND(IF(PD.taxno = '00', 0.00, IFNULL(PD.lucroTributado, 0)) / 100, 4) AS mvap,
       ROUND(IFNULL(P.dicm, 0) * (-1) / 100, 4)                                AS icmsp
FROM sqldados.prp          AS P
  INNER JOIN sqldados.prd  AS PD
	       ON PD.no = P.prdno
  INNER JOIN sqldados.vend AS V
	       ON PD.mfno = V.no
WHERE storeno = 10
  AND P.prdno < LPAD('960001', 16, ' ')
  AND (P.prdno = @PRDNO OR @CODIGO = 0)
  AND (FIND_IN_SET(PD.mfno, @LISTVEND) OR @LISTVEND = '')
  AND (PD.typeno = @TYPENO OR @TYPENO = 0)
  AND (PD.clno = @CLNO OR @CLNO = 0)
  AND (PD.taxno = @TRIBUTACAO OR @TRIBUTACAO = '')
