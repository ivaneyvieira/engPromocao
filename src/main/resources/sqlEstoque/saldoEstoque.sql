SELECT TRIM(P.codigo) * 1                                                                                    AS codigo,
       IF(:grade = 'S', P.grade, '')                                                                         AS grade,
       CAST(GROUP_CONCAT(DISTINCT MID(I.localizacao, 1, 4)) AS char)                                         AS abreviacao,
       SUM(IF(I.status IN ('RECEBIDO'), 1, IF(I.status IN ('CONFERIDA', 'ENTREGUE'), -1, 0)) * I.quantidade) AS saldo
FROM engEstoque.notas AS N
         INNER JOIN engEstoque.itens_nota AS I
                    ON N.id = I.nota_id
         INNER JOIN engEstoque.produtos AS P
                    ON I.produto_id = P.id
WHERE N.tipo_nota NOT LIKE 'CANCELADA%'
GROUP BY P.codigo, IF(:grade = 'S', P.grade, '')