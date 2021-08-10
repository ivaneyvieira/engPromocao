DO @PRDNO := LPAD(:codigo * 1, 16, ' ');
DO @VALIDADE := :validade;
DO @LOGIN := :login;

UPDATE sqldados.prp
SET l6 = promo_discount,
    l7 = promo_price,
    l8 = promo_validate,
    c4 = @LOGIN
WHERE prdno = @PRDNO
  AND storeno = 10;

UPDATE sqldados.prp
SET promo_validate = @VALIDADE
WHERE prdno = @PRDNO
  AND storeno = 10
