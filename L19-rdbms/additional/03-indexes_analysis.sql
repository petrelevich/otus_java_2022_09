-- Размеры индексов и др статистика по ним
-- http://www.dbrnd.com/2017/01/postgresql-script-to-find-index-size-index-usage-statistics-of-a-database-pg_stat_all_indexes/

SELECT
    pt.tablename AS TableName
     ,t.indexname AS IndexName
     ,to_char(pc.reltuples, '999,999,999,999') AS TotalRows
     ,pg_size_pretty(pg_relation_size(quote_ident(pt.tablename)::text)) AS TableSize
     ,pg_size_pretty(pg_relation_size(quote_ident(t.indexrelname)::text)) AS IndexSize
     ,to_char(t.idx_scan, '999,999,999,999') AS TotalNumberOfScan
     ,to_char(t.idx_tup_read, '999,999,999,999') AS TotalTupleRead
     ,to_char(t.idx_tup_fetch, '999,999,999,999') AS TotalTupleFetched
FROM pg_tables AS pt
         LEFT OUTER JOIN pg_class AS pc
                         ON pt.tablename=pc.relname
         LEFT OUTER JOIN
     (
         SELECT
             pc.relname AS TableName
              ,pc2.relname AS IndexName
              ,psai.idx_scan
              ,psai.idx_tup_read
              ,psai.idx_tup_fetch
              ,psai.indexrelname
         FROM pg_index AS pi
                  JOIN pg_class AS pc
                       ON pc.oid = pi.indrelid
                  JOIN pg_class AS pc2
                       ON pc2.oid = pi.indexrelid
                  JOIN pg_stat_all_indexes AS psai
                       ON pi.indexrelid = psai.indexrelid
     )AS T
     ON pt.tablename = T.TableName
WHERE pt.schemaname='public'
ORDER BY 1;

-- Поиск отсутствующих индексов
-- https://stackoverflow.com/a/12818168
SELECT
    relname                                               AS TableName,
    to_char(seq_scan, '999,999,999,999')                  AS TotalSeqScan,
    to_char(idx_scan, '999,999,999,999')                  AS TotalIndexScan,
    to_char(n_live_tup, '999,999,999,999')                AS TableRows,
    pg_size_pretty(pg_relation_size(relname :: regclass)) AS TableSize
FROM pg_stat_all_tables
WHERE schemaname = 'public'
  AND 50 * seq_scan > idx_scan -- more than 2%
  AND n_live_tup > 10000
  AND pg_relation_size(relname :: regclass) > 5000000
ORDER BY relname ASC;


-- Неиспользуемые индексы
SELECT
    indexrelid::regclass as index,
    relid::regclass as table,
    'DROP INDEX ' || indexrelid::regclass || ';' as drop_statement
FROM
    pg_stat_user_indexes
        JOIN
    pg_index USING (indexrelid)
WHERE
        idx_scan = 0
  AND indisunique is false;


-- PostgreSQL Wiki - Index Maintenance
-- https://wiki.postgresql.org/wiki/Index_Maintenance