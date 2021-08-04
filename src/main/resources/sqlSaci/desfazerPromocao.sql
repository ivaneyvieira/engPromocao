DO @MARCA := (SELECT MAX(c4)
	      FROM sqldados.prp
	      WHERE c4 <> ''
	        AND c4 LIKE CONCAT('%', :login)
		AND storeno = 10
		AND promo_validate >= CURRENT_DATE * 1);

UPDATE sqldados.prp
SET promo_price    = 0,
    promo_discount = 0,
    promo_validate = 0,
    c4             = ''
WHERE c4 = @MARCA
  AND storeno = 10
  AND promo_validate >= CURRENT_DATE * 1



