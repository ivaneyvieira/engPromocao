DO @PRDNO := LPAD(:codigo * 1, 16, ' ');
DO @LOGIN := :login;

UPDATE sqldados.prp
SET promo_discount = l6,
    promo_price    = l7,
    promo_validate = l8
WHERE prdno = @PRDNO
  AND storeno = 10;

UPDATE sqldados.prp
SET l6 = 0,
    l7 = 0,
    l8 = 0,
    c4 = ''
WHERE prdno = @PRDNO
  AND storeno = 10



