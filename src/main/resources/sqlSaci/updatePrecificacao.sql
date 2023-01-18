UPDATE sqldados.prp
SET adm = ROUND(:cmpf * 100)
WHERE prdno = :prdno
  AND storeno = 10