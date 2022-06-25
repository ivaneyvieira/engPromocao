SELECT TRIM(P.no)                                                       AS codigo,
       IFNULL(B.grade, '')                                              AS grade,
       TRIM(IF(B.grade IS NULL, IFNULL(P2.gtin, P.barcode), B.barcode)) AS barcode
FROM sqldados.prd           AS P
  LEFT JOIN sqldados.prdbar AS B
	      ON P.no = B.prdno AND P.clno NOT BETWEEN 10000 AND 19999
  LEFT JOIN sqldados.prd2   AS P2
	      ON P.no = P2.prdno
WHERE P.no = LPAD(:codigo, 16, ' ')