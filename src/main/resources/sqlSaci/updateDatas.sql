UPDATE sqldados.prp
SET promo_validate = :dataNew
WHERE promo_validate = :dataOld
  AND prdno = LPAD(:codigo*1, 16, ' ')
  AND storeno = 10