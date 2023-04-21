USE sqldados;
ALTER TABLE sqldados.ultimaVenda
    ADD INDEX (prdno, grade);
ALTER TABLE sqldados.ultimaCompra
    ADD INDEX (prdno, grade);