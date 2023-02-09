UPDATE sqldados.prp
SET promo_validate = :dataNew
WHERE prdno = LPAD(:codigo * 1, 16, ' ')
  AND storeno = 10