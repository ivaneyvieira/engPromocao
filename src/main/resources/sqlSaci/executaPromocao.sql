DO @PRDNO := LPAD(:codigo * 1, 16, ' ');
DO @DESCONTO := :desconto;
DO @VALIDADE := :validade;
DO @MARCA := :marca;

UPDATE sqldados.prp
SET promo_price    = TRUNCATE(refprice * (100.00 - @DESCONTO) / 100, 0),
    promo_discount = TRUNCATE(@DESCONTO * 100, 0),
    promo_validate = @VALIDADE,
    c4             = @MARCA
WHERE prdno = @PRDNO
  AND storeno = 10
  AND promo_validate < CURRENT_DATE * 1
