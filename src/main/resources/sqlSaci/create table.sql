#### Backup da precificação
CREATE TABLE sqldados.prpbak03
SELECT *
FROM sqldados.prp
WHERE storeno = 10;