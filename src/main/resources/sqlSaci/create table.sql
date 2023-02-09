#### Backup da precificação
create table sqldados.prpbak03
select * from sqldados.prp
where storeno = 10;