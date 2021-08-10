DO @PRDNO := LPAD(:codigo * 1, 16, ' ');
DO @DESCONTO := :desconto;
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
SET promo_price    = TRUNCATE(refprice * (100.00 - @DESCONTO) / 100, 0),
    promo_discount = TRUNCATE(@DESCONTO * 100, 0),
    promo_validate = @VALIDADE
WHERE prdno = @PRDNO
  AND storeno = 10
