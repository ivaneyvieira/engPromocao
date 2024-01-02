USE engEstoque;
SET sql_mode = '';

DO @QUERY := :query;
DO @QUERYLIKE := CONCAT(@QUERY, '%');

DO @LISTVEND := REPLACE(:listVend, ' ', '');
DO @TYPENO := :typeno;
DO @CLNO := :clno;
DO @NFE := :nfe;

DROP TEMPORARY TABLE IF EXISTS T_PRD;
CREATE TEMPORARY TABLE T_PRD
(
    PRIMARY KEY (prdno, grade)
)
SELECT prdno,
       IF(:temGrade = 'S', grade, '') AS grade,
       descricao,
       mfno                           AS vendno,
       typeno,
       clno,
       deptno,
       groupno,
       taxno,
       ROUND(SUM(saldoMF))            AS saldoNerus
FROM saldos
WHERE CASE :marca
          WHEN 'T' THEN TRUE
          WHEN 'N' THEN MID(descricao, 1, 1) NOT IN ('.', '*', '!', '*', ']', ':', '#')
          WHEN 'S' THEN MID(descricao, 1, 1) IN ('.', '*', '!', '*', ']', ':', '#')
    END
  AND (FIND_IN_SET(mfno, @LISTVEND) OR @LISTVEND = '')
  AND (typeno = @TYPENO OR @TYPENO = 0)
  AND (clno = @CLNO OR deptno = @CLNO OR groupno = @CLNO OR @CLNO = 0)
  AND (prdno = :codigo OR :codigo = 0)
GROUP BY prdno, IF(:temGrade = 'S', grade, '');

DROP TEMPORARY TABLE IF EXISTS T_MOV_APP;
CREATE TEMPORARY TABLE T_MOV_APP
SELECT L.numero                                                      AS loja,
       P.codigo                                                      AS prdno,
       IF(:temGrade = 'S', P.grade, '')                              AS grade,
       CAST(GROUP_CONCAT(DISTINCT MID(I.localizacao, 1, 4)) AS char) AS abreviacao,
       N.numero                                                      AS nota,
       I.status                                                      AS status,
       N.data                                                        AS data,
       N.tipo_mov                                                    AS tipo,
       SUM(IF(I.status IN ('RECEBIDO'), 1, IF(I.status IN ('CONFERIDA', 'ENTREGUE'), -1, 0)) *
           I.quantidade)                                             AS saldoApp
FROM engEstoque.notas AS N
         INNER JOIN engEstoque.lojas AS L
                    ON N.loja_id = L.id
         INNER JOIN engEstoque.itens_nota AS I
                    ON N.id = I.nota_id
         INNER JOIN engEstoque.produtos AS P
                    ON I.produto_id = P.id
         INNER JOIN T_PRD AS T
                    ON P.codigo = T.prdno AND IF(:temGrade = 'S', P.grade, '') = T.grade
WHERE N.tipo_nota NOT LIKE 'CANCELADA%'
  AND I.status IN ('RECEBIDO', 'CONFERIDA', 'ENTREGUE')
GROUP BY L.numero, P.codigo, IF(:temGrade = 'S', P.grade, ''), nota, tipo, status;

DROP TEMPORARY TABLE IF EXISTS T_SALDO_APP;
CREATE TEMPORARY TABLE T_SALDO_APP
(
    PRIMARY KEY (prdno, grade)
)
SELECT prdno, grade, abreviacao, SUM(saldoApp) AS saldoApp
FROM T_MOV_APP
GROUP BY prdno, grade
HAVING SUM(saldoApp) <> 0;

DROP TEMPORARY TABLE IF EXISTS T_ENTRADA;
CREATE TEMPORARY TABLE T_ENTRADA
(
    PRIMARY KEY (loja, prdno, grade, nota)
)
SELECT prdno,
       grade,
       loja,
       nota,
       data,
       tipo,
       saldoApp AS entrada
FROM T_MOV_APP
WHERE tipo = 'ENTRADA';

DROP TEMPORARY TABLE IF EXISTS T_MOV01;
CREATE TEMPORARY TABLE T_MOV01
(
    PRIMARY KEY (loja, codigo, grade, nfEntrada)
)
SELECT loja                                 AS loja,
       prdno                                AS codigo,
       descricao                            AS descricao,
       vendno,
       typeno,
       clno,
       deptno,
       groupno,
       taxno,
       grade                                AS grade,
       abreviacao                           AS localizacao,
       saldoNerus                           AS estoqueNerus,
       saldoApp                             AS estoqueApp,
       entrada                              AS entrada,
       nota                                 AS nfEntrada,
       data                                 AS dataEntrada,
       CONCAT(loja, '-', prdno, '-', grade) AS chave
FROM T_ENTRADA
         INNER JOIN T_PRD
                    USING (prdno, grade)
         LEFT JOIN T_SALDO_APP USING (prdno, grade);

DO @CHAVE := '';
DO @SALDO := 0;

DROP TEMPORARY TABLE IF EXISTS T_MOV02;
CREATE TEMPORARY TABLE T_MOV02
SELECT loja,
       codigo,
       descricao,
       grade,
       localizacao,
       estoqueNerus,
       estoqueApp,
       entrada,
       nfEntrada,
       dataEntrada,
       vendno,
       typeno,
       clno,
       deptno,
       groupno,
       taxno,
       @CHAVE                                                                      AS cv,
       chave                                                                       AS ch,
       ROUND(IF(@CHAVE = chave, @SALDO, estoqueApp))                               AS saldoAnt,
       @SALDO := ROUND(IF(@CHAVE = chave, @SALDO - entrada, estoqueApp - entrada)) AS saldo,
       @CHAVE := chave                                                             AS chave
FROM T_MOV01
ORDER BY loja, codigo, grade, dataEntrada DESC;

SELECT loja,
       TRIM(codigo) * 1                          AS codigo,
       descricao,
       vendno,
       grade,
       localizacao,
       estoqueNerus,
       estoqueApp,
       IF(entrada > saldoAnt, saldoAnt, entrada) AS saldo,
       entrada,
       nfEntrada,
       CAST(dataEntrada AS date)                 AS dataEntrada
FROM T_MOV02
WHERE saldoAnt > 0
  AND (codigo LIKE @QUERY OR localizacao LIKE @QUERY OR descricao LIKE @QUERYLIKE OR grade LIKE @QUERYLIKE OR
       nfEntrada LIKE @QUERYLIKE OR DATE_FORMAT(dataEntrada, '%d/%m/%Y') LIKE @QUERYLIKE
    )
  AND CASE :estoque
          WHEN 'T' THEN TRUE
          WHEN '=' THEN estoqueApp = 0
          WHEN '>' THEN estoqueApp > 0
          WHEN '<' THEN estoqueApp < 0
          ELSE FALSE
    END
  AND (nfEntrada = @NFE OR @NFE = '')
  AND (grade = :grade OR :grade = '')
ORDER BY loja, codigo, grade, dataEntrada DESC