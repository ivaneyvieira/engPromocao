UPDATE sqldados.prp
SET c4 = ''
WHERE c4 <> ''
  AND c4 LIKE CONCAT('%', :login)
  AND storeno = 10
  AND promo_validate >= CURRENT_DATE * 1