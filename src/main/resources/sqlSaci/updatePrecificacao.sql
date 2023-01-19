UPDATE sqldados.prp
SET adm = ROUND(:cpmf * 100),
    pis = ROUND(:fcp * 100)
WHERE prdno = :prdno
  AND storeno = 10