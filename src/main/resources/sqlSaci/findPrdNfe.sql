USE sqldados;

DO @NFNO := :nfno;
DO @NFSE := :nfse;
DO @INVNO := :invno;

DROP TEMPORARY TABLE IF EXISTS T_NUMERO01;
CREATE TEMPORARY TABLE T_NUMERO01
SELECT concat(CONCAT(nfname, '/', invse)) as nfe, prdno, grade
FROM sqldados.iprd AS I
         INNER JOIN sqldados.inv AS N USING (invno)
WHERE N.nfname = @NFNO
  AND N.invse = @NFSE
  AND @INVNO = 0
  AND N.storeno IN (1, 2, 3, 4, 5, 8)
  AND concat(CONCAT(nfname, '/', invse)) != '/'
GROUP BY prdno, grade;

DROP TEMPORARY TABLE IF EXISTS T_NUMERO02;
CREATE TEMPORARY TABLE T_NUMERO02
SELECT concat(CONCAT(nfname, '/', invse)) as nfe, prdno, grade
FROM sqldados.iprd AS I
         inner join sqldados.inv AS N USING (invno)
WHERE I.invno = @INVNO
  AND @NFNO = ''
  AND @NFSE = ''
  AND concat(CONCAT(nfname, '/', invse)) != '/'
GROUP BY prdno, grade;

SELECT nfe, prdno, grade
FROM T_NUMERO01
UNION
SELECT nfe, prdno, grade
FROM T_NUMERO02
