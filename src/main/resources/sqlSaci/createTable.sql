USE sqldados;
alter table sqldados.ultimaVenda
    add INDEX (prdno, grade);
alter table sqldados.ultimaCompra
    add INDEX (prdno, grade);