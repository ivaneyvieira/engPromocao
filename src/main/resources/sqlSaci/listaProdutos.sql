USE sqldados;

DO @CODIGO := :codigo;
DO @PRDNO := LPAD(@CODIGO, 16, ' ');
DO @LISTVEND := REPLACE(:listVend, ' ', '');
DO @TRIBUTACAO := :tributacao;
DO @TYPENO := :typeno;
DO @CLNO := :clno;
DO @DIVENDA := :diVenda;
DO @DFVENDA := IF(:dfVenda = 0, 99999999, :dfVenda);
DO @DICOMPRA := :diCompra;
DO @DFCOMPRA := IF(:dfCompra = 0, 99999999, :dfCompra);
DO @GRADE := :temGrade;
DO @LOJA := :loja;

DROP TABLE IF EXISTS T_STK;
CREATE TEMPORARY TABLE T_STK
(
    PRIMARY KEY (prdno, gradeOpt)
)
SELECT prdno,
       if(@GRADE = 'S', grade, '')                                     as gradeOpt,
       SUM(IF(storeno = 1, qtty_varejo / 1000, 0.00))                  AS JS_VA,
       SUM(IF(storeno = 1, qtty_atacado / 1000, 0.00))                 AS JS_AT,
       SUM(IF(storeno = 1, (qtty_varejo + qtty_atacado) / 1000, 0.00)) AS JS_TT,

       SUM(IF(storeno = 2, qtty_varejo / 1000, 0.00))                  AS DS_VA,
       SUM(IF(storeno = 2, qtty_atacado / 1000, 0.00))                 AS DS_AT,
       SUM(IF(storeno = 2, (qtty_varejo + qtty_atacado) / 1000, 0.00)) AS DS_TT,

       SUM(IF(storeno = 3, qtty_varejo / 1000, 0.00))                  AS MR_VA,
       SUM(IF(storeno = 3, qtty_atacado / 1000, 0.00))                 AS MR_AT,
       SUM(IF(storeno = 3, (qtty_varejo + qtty_atacado) / 1000, 0.00)) AS MR_TT,

       SUM(IF(storeno = 4, qtty_varejo / 1000, 0.00))                  AS MF_VA,
       SUM(IF(storeno = 4, qtty_atacado / 1000, 0.00))                 AS MF_AT,
       SUM(IF(storeno = 4, (qtty_varejo + qtty_atacado) / 1000, 0.00)) AS MF_TT,

       SUM(IF(storeno = 5, qtty_varejo / 1000, 0.00))                  AS PK_VA,
       SUM(IF(storeno = 5, qtty_atacado / 1000, 0.00))                 AS PK_AT,
       SUM(IF(storeno = 5, (qtty_varejo + qtty_atacado) / 1000, 0.00)) AS PK_TT,

       SUM(IF(storeno = 8, qtty_varejo / 1000, 0.00))                  AS TM_VA,
       SUM(IF(storeno = 8, qtty_atacado / 1000, 0.00))                 AS TM_AT,
       SUM(IF(storeno = 8, (qtty_varejo + qtty_atacado) / 1000, 0.00)) AS TM_TT,
       SUM(qtty_varejo + qtty_atacado) / 1000                          AS estoque
FROM sqldados.stk AS S
WHERE S.storeno IN (1, 2, 3, 4, 5, 6)
  AND (S.storeno = @LOJA OR @LOJA = 0)
GROUP BY prdno, gradeOpt;

DROP TABLE IF EXISTS T_RESULT;
CREATE TEMPORARY TABLE T_RESULT
(
    PRIMARY KEY (prdno, grade)
)
SELECT P.no                                                             AS prdno,
       TRIM(P.no) * 1                                                   AS codigo,
       TRIM(MID(P.name, 1, 37))                                         AS descricao,
       IFNULL(S.gradeOpt, '')                                           AS grade,
       CAST(P.mfno AS CHAR ASCII)                                       AS fornStr,
       P.mfno                                                           AS forn,
       P.taxno                                                          AS tributacao,
       V.sname                                                          AS abrev,
       CAST(P.typeno AS CHAR ASCII)                                     AS tipoStr,
       P.typeno                                                         AS tipo,
       P.clno                                                           AS cl,
       RPAD(MID(LPAD(P.clno, 6, '0'), 1, 2), 6, '0') * 1                AS groupno,
       RPAD(MID(LPAD(P.clno, 6, '0'), 1, 4), 6, '0') * 1                AS deptno,
       TRIM(IF(B.grade IS NULL, IFNULL(P2.gtin, P.barcode), B.barcode)) AS codBar,
       ROUND(JS_VA)                                                     AS JS_VA,
       ROUND(JS_AT)                                                     AS JS_AT,
       ROUND(JS_TT)                                                     AS JS_TT,
       ROUND(DS_VA)                                                     AS DS_VA,
       ROUND(DS_AT)                                                     AS DS_AT,
       ROUND(DS_TT)                                                     AS DS_TT,
       ROUND(MR_VA)                                                     AS MR_VA,
       ROUND(MR_AT)                                                     AS MR_AT,
       ROUND(MR_TT)                                                     AS MR_TT,
       ROUND(MF_VA)                                                     AS MF_VA,
       ROUND(MF_AT)                                                     AS MF_AT,
       ROUND(MF_TT)                                                     AS MF_TT,
       ROUND(PK_VA)                                                     AS PK_VA,
       ROUND(PK_AT)                                                     AS PK_AT,
       ROUND(PK_TT)                                                     AS PK_TT,
       ROUND(TM_VA)                                                     AS TM_VA,
       ROUND(TM_AT)                                                     AS TM_AT,
       ROUND(TM_TT)                                                     AS TM_TT,
       ROUND(estoque)                                                   AS estoque,
       P.taxno                                                          AS trib,
       P.mfno_ref                                                       AS refForn,
       P.weight_g                                                       AS pesoBruto,
       CASE P.tipoGarantia
           WHEN 0 THEN 'Dia'
           WHEN 1 THEN 'Semana'
           WHEN 2 THEN 'Mês'
           WHEN 3 THEN 'Ano'
           ELSE ''
           END                                                          AS uGar,
       P.garantia                                                       AS tGar,
       P.qttyPackClosed / 1000                                          AS emb,
       IFNULL(N.ncm, '')                                                AS ncm,
       ''                                                               AS site,
       TRIM(MID(P.name, 37, 3))                                         AS unidade,
       IF((dereg & POW(2, 2)) = 0, 'N', 'S')                            AS foraLinha,
       MID(L.localizacao, 1, 4)                                         as localizacao
FROM sqldados.prd AS P
         INNER JOIN T_STK AS S ON S.prdno = P.no
         LEFT JOIN sqldados.prd2 AS P2 ON P.no = P2.prdno
         LEFT JOIN sqldados.vend AS V ON V.no = P.mfno
         LEFT JOIN sqldados.prdbar AS B ON S.prdno = B.prdno AND S.gradeOpt = IF(@GRADE = 'S', B.grade, '')
         LEFT JOIN sqldados.spedprd AS N ON N.prdno = P.no
         LEFT JOIN sqldados.prdloc AS L ON L.prdno = S.prdno AND S.gradeOpt = IF(@GRADE = 'S', L.grade, '')
WHERE (P.no = @PRDNO OR @CODIGO = 0)
  AND (FIND_IN_SET(P.mfno, @LISTVEND) OR @LISTVEND = '')
  AND (P.typeno = @TYPENO OR @TYPENO = 0)
  AND (P.clno = @CLNO OR P.deptno = @CLNO OR P.groupno = @CLNO OR @CLNO = 0)
  AND (P.taxno = @TRIBUTACAO OR @TRIBUTACAO = '')
  AND (S.estoque != 0 OR :todoEstoque = 'S')
  AND CASE :marca
          WHEN 'T' THEN TRUE
          WHEN 'N' THEN MID(P.name, 1, 1) NOT IN ('.', '*', '!', '*', ']', ':', '#')
          WHEN 'S' THEN MID(P.name, 1, 1) IN ('.', '*', '!', '*', ']', ':', '#')
          ELSE FALSE
    END
  AND CASE :inativo
          WHEN 'T' THEN TRUE
          WHEN 'S' THEN (P.dereg & POW(2, 2)) != 0
          WHEN 'N' THEN (P.dereg & POW(2, 2)) = 0
          ELSE FALSE
    END
  AND CASE :estoqueTotal
          WHEN 'T' THEN TRUE
          WHEN '>' THEN ROUND(estoque) > 0
          WHEN '<' THEN ROUND(estoque) < 0
          WHEN '=' THEN ROUND(estoque) = 0
          ELSE FALSE
    END
GROUP BY P.no, IFNULL(S.gradeOpt, '');

DROP TABLE IF EXISTS T_PRDVENDA;
CREATE TEMPORARY TABLE T_PRDVENDA
(
    PRIMARY KEY (prdno, grade)
)
SELECT R.prdno, R.grade, MAX(date) AS date, SUM(quant / 1000) AS qtty
FROM T_RESULT AS R
         INNER JOIN sqldados.vendaDate AS X ON X.prdno = R.prdno AND X.grade = R.grade
WHERE (X.storeno IN (1, 2, 3, 4, 5, 6))
  AND (X.storeno = @LOJA OR @LOJA = 0)
  AND date BETWEEN @DIVENDA AND @DFVENDA
GROUP BY R.prdno, R.grade;

DROP TABLE IF EXISTS T_PRDCOMPRA;
CREATE TEMPORARY TABLE T_PRDCOMPRA
(
    PRIMARY KEY (prdno, grade)
)
SELECT R.prdno, R.grade, MAX(date) AS date, SUM(quant / 1000) AS qtty
FROM T_RESULT AS R
         INNER JOIN sqldados.compraDate P ON P.prdno = R.prdno AND P.grade = R.grade
WHERE (storeno IN (1, 2, 3, 4, 5, 6))
  AND (storeno = @LOJA OR @LOJA = 0)
  AND date BETWEEN @DICOMPRA AND @DFCOMPRA
GROUP BY R.prdno, R.grade;

DO @PESQUISA := :pesquisa;
DO @PESQUISA_LIKE := CONCAT(:pesquisa, '%');

SELECT R.prdno,
       codigo,
       descricao,
       R.grade,
       forn,
       abrev,
       tipo,
       cl,
       codBar,
       tributacao,
       DS_VA,
       DS_AT,
       DS_TT,
       MR_VA,
       MR_AT,
       MR_TT,
       MF_VA,
       MF_AT,
       MF_TT,
       PK_VA,
       PK_AT,
       PK_TT,
       TM_VA,
       TM_AT,
       TM_TT,
       estoque,
       trib,
       refForn,
       pesoBruto,
       uGar,
       tGar,
       emb,
       ncm,
       site,
       unidade,
       foraLinha,
       CAST(V.date AS DATE)     AS ultVenda,
       CAST(C.date AS DATE)     AS ultCompra,
       ROUND(IFNULL(V.qtty, 0)) AS qttyVendas,
       ROUND(IFNULL(C.qtty, 0)) AS qttyCompra,
       localizacao
FROM T_RESULT AS R
         LEFT JOIN T_PRDVENDA AS V ON (R.prdno = V.prdno AND R.grade = V.grade)
         LEFT JOIN T_PRDCOMPRA AS C ON (R.prdno = C.prdno AND R.grade = C.grade)
WHERE :pesquisa = ''
   OR codigo LIKE @PESQUISA
   OR descricao LIKE @PESQUISA_LIKE
   OR R.grade LIKE @PESQUISA_LIKE
   OR fornStr LIKE @PESQUISA
   OR abrev LIKE @PESQUISA_LIKE
   OR tipo LIKE @PESQUISA
   OR cl LIKE @PESQUISA
   OR groupno LIKE @PESQUISA
   OR deptno LIKE @PESQUISA
   OR codBar LIKE @PESQUISA
