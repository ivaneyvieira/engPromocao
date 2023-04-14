USE engEstoque;
SET sql_mode = '';

DO @QUERY := :query;
DO @QUERYLIKE := CONCAT(@QUERY, '%');

DROP TEMPORARY TABLE IF EXISTS T;
CREATE TEMPORARY TABLE T
    SELECT TRIM(P.codigo) * 1        AS codigo,
           TP.nome                   AS descricao,
           P.grade,
           CONCAT(P.codigo, P.grade) AS chave,
           L.numero                  AS loja,
           N.numero,
           P.meses_vencimento,
           I.data_fabricacao,
           N.data,
           I.quantidade,
           ROUND(S.saldo)            AS saldo,
           S.garantia
    FROM notas AS N
             INNER JOIN lojas L ON N.loja_id = L.id
             INNER JOIN itens_nota AS I ON N.id = I.nota_id
             INNER JOIN produtos AS P ON I.produto_id = P.id
             INNER JOIN saldos AS S ON S.prdno = P.codigo AND S.grade = P.grade
             INNER JOIN tab_produtos AS TP ON TP.codigo = P.codigo AND TP.grade = P.grade
    WHERE meses_vencimento IS NOT NULL
      AND N.tipo_mov = 'ENTRADA'
      AND N.tipo_nota = 'COMPRA';

SET @CHAVE := '';
SET @NUM := 0;
SET @ACUMULADO := 0;
SET @ACUMULADO2 := 0;
SET @TIPO := 'OK';

DROP TEMPORARY TABLE IF EXISTS T2;
CREATE TEMPORARY TABLE T2
    SELECT @NUM := @NUM + 1                                                                           AS seq,
           codigo,
           descricao,
           grade,
           loja,
           numero,
           meses_vencimento,
           data_fabricacao,
           data,
           quantidade,
           saldo,
           garantia,
           IF(@CHAVE = chave, @ACUMULADO, 0)                                                          AS acumulado2,
           @ACUMULADO := IF(@CHAVE = chave, @ACUMULADO + T.quantidade, T.quantidade)                  AS acumulado,
           @TIPO := IF(@CHAVE = chave, IF(@TIPO = 'OK', IF(@ACUMULADO > saldo, 'N', 'OK'), ''), 'OK') AS tipo,
           @CHAVE                                                                                     AS cavheOld,
           @CHAVE := chave                                                                            AS chave
    FROM T
    ORDER BY codigo, grade, data_fabricacao DESC, data DESC, numero;

DROP TEMPORARY TABLE IF EXISTS T3;
CREATE TEMPORARY TABLE T3
    SELECT seq,
           codigo,
           descricao,
           grade,
           loja,
           numero,
           meses_vencimento,
           data_fabricacao,
           DATE_ADD(data_fabricacao, INTERVAL meses_vencimento MONTH) AS data_vencimento,
           data,
           quantidade,
           saldo,
           acumulado,
           acumulado2,
           tipo,
           IF(saldo > acumulado, quantidade, T2.saldo - acumulado2)   AS sobra,
           cavheOld,
           chave
    FROM T2
    WHERE tipo != ''
    ORDER BY seq;

SELECT loja,
       codigo,
       descricao,
       grade,
       meses_vencimento                                                      AS validade,
       numero                                                                AS nfEntrada,
       data                                                                  AS dataEntrada,
       data_fabricacao                                                       AS dataFabricacao,

       TIMESTAMPDIFF(MONTH, CONCAT(DATE_FORMAT(data_fabricacao, '%Y%m'), '01'),
                     CONCAT(DATE_FORMAT(data, '%Y%m'), '01')) + 1            AS mesesFabricacao,
       data_vencimento                                                       AS vencimento,
       TIMESTAMPDIFF(MONTH, CONCAT(DATE_FORMAT(CURRENT_DATE, '%Y%m'), '01'),
                     CONCAT(DATE_FORMAT(data_vencimento, '%Y%m'), '01')) - 1 AS mesesVencimento,
       quantidade                                                            AS entrada,
       sobra                                                                 AS saldo,
       saldo                                                                 AS estoque,
       tipo                                                                  AS status
FROM T3
WHERE (codigo LIKE @QUERY OR descricao LIKE @QUERYLIKE OR grade LIKE @QUERYLIKE OR numero LIKE @QUERYLIKE OR
       DATE_FORMAT(data, '%d/%m/%Y') LIKE @QUERYLIKE OR DATE_FORMAT(data_fabricacao, '%d/%m/%Y') LIKE @QUERYLIKE OR
       DATE_FORMAT(data_vencimento, '%d/%m/%Y') LIKE @QUERYLIKE)
  AND (tipo = 'OK' OR tipo = 'N')
ORDER BY seq

