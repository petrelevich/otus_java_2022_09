-- https://blog.bullgare.com/wp-content/uploads/2015/04/understanding_explain.html

-- "reset"
DROP TABLE IF EXISTS foo;
DROP TABLE IF EXISTS bar;
SET work_mem TO '2MB';

-- Создаем тестовую таблицу
CREATE TABLE foo (c1 integer, c2 text);

INSERT INTO foo
SELECT i, md5(random()::text)
FROM generate_series(1, 1000000) AS i;

-- Посмотрим, что вставили
SELECT * FROM foo;

-- Разные EXPLAIN
EXPLAIN
SELECT * FROM foo;

-- EXPLAIN - предполагаемый план, запрос не выполняется
-- EXPLAIN ANALYZE - реальный план, запрос выполняется,
-- добавляется раздел "actual"
EXPLAIN ANALYZE
SELECT * FROM foo;
-- Добавилось actual time, ...

EXPLAIN (ANALYZE, BUFFERS)
SELECT * FROM foo;
-- Добавилась информация о памяти - Buffers: shared hit
-- Buffers: shared read — количество блоков, считанных с диска.
-- Buffers: shared hit — количество блоков, считанных из кэша.

EXPLAIN (ANALYZE, BUFFERS, FORMAT TEXT)
SELECT * FROM foo;

EXPLAIN (ANALYZE, BUFFERS, FORMAT XML)
SELECT * FROM foo;

EXPLAIN (ANALYZE, BUFFERS, FORMAT JSON)
SELECT * FROM foo;

EXPLAIN (ANALYZE, BUFFERS, FORMAT YAML)
SELECT * FROM foo;

-- Смотрим на операторы

-- Читаем данные
EXPLAIN
SELECT * FROM foo;
-- Seq Scan

-- Попробуем добавить 10 строк.
INSERT INTO foo
SELECT i, md5(random()::text)
FROM generate_series(1, 10) AS i;

-- Опять выполним запрос
SELECT count(*) FROM foo;

-- Смотрим на ожидаемые значения в плане выполнения
EXPLAIN
SELECT * FROM foo;
-- Видим старое значение estimated rows 1 000 000

-- Обновим статистику через ANALYZE
ANALYZE foo;

EXPLAIN
SELECT * FROM foo;
-- Теперь в rows актуальное значение.
-- Если разница предполагаемого количества и фактического большая (на порядок),
-- то обновляйте статистику.

-- ------------
-- WHERE
-- ------------
EXPLAIN ANALYZE
SELECT * FROM foo WHERE c1 > 500;
-- Seq Scan, т.к. индексов нет.
-- Filter: (c1 > 500)
-- Rows Removed by Filter: 510

-- Seq Scan on foo  (cost=0.00..20834.12 rows=999572 width=37) (actual time=0.043..114.583 rows=999500 loops=1)
--   Filter: (c1 > 500)
--   Rows Removed by Filter: 510
-- Planning time: 0.033 ms
-- Execution time: 146.705 ms

-- ------------
-- Индексы
-- ------------
-- Создадим индекс
CREATE INDEX ON foo(c1);
-- Выполним тот же запрос
EXPLAIN ANALYZE
SELECT * FROM foo WHERE c1 > 500;
-- Seq Scan, хотя индекс есть

-- Отфильтровано 510 строк из более чем миллиона (Rows Removed by Filter: 510)

-- Seq Scan on foo  (cost=0.00..20834.12 rows=999479 width=37) (actual time=0.049..118.681 rows=999500 loops=1)
--   Filter: (c1 > 500)
--   Rows Removed by Filter: 510
-- Planning time: 0.176 ms
-- Execution time: 152.221 ms



-- Запретим Seq Scan и попробуем использовать индекс принудительно:
SET enable_seqscan TO off;

EXPLAIN ANALYZE
SELECT * FROM foo WHERE c1 > 500;

SET enable_seqscan TO on;
-- Index Scan, Index Cond
-- А что со стоимостью?

-- Изменим запрос - выбираем мало записей
EXPLAIN ANALYZE
SELECT * FROM foo WHERE c1 < 500;
-- Index Scan

-- Усложним условие. Используем текстовое поле.
EXPLAIN ANALYZE
SELECT * FROM foo
WHERE c1 < 500 AND c2 LIKE 'abcd%';

-- Если в условии только текстовое поле:
EXPLAIN ANALYZE
SELECT * FROM foo WHERE c2 LIKE 'abcd%';
-- Seq Scan, индексов на c2 нет

-- Создадим индекс по c2
CREATE INDEX ON foo(c2 text_pattern_ops);
-- Выполняем запрос
EXPLAIN ANALYZE
SELECT * FROM foo
WHERE c2 LIKE 'abcd%';
-- Bitmap Index Scan, используется индекс foo_c2_idx1

-- Будет ли использоваться индекс?
EXPLAIN (ANALYZE,BUFFERS)
SELECT * FROM foo WHERE c2 LIKE 'abc%';

-- А здесь?
EXPLAIN (ANALYZE,BUFFERS)
SELECT * FROM foo WHERE c2 LIKE '%abc';

-- Что со стоимостью и временем?

-- Покрывающий индекс - Index Only Scan
EXPLAIN ANALYZE
SELECT c1 FROM foo WHERE c1 < 500;

-- ------------
-- Сортировка
-- ------------
-- Попробуем сначала с индексом
EXPLAIN (ANALYZE, BUFFERS)
SELECT * FROM foo
ORDER BY c1;

-- А потом удалим индекс
DROP INDEX foo_c1_idx;
-- CREATE INDEX ON foo(c1);

EXPLAIN (ANALYZE, BUFFERS)
SELECT * FROM foo
ORDER BY c1;
-- есть:
-- Sort Method: external sort
-- temp read=5751 written=57451 (это страницы)

-- DROP INDEX foo_c1_idx;

-- Попробуем увеличить work_mem:
SET work_mem TO '200MB';

-- И выполним предыдущий запрос
EXPLAIN (ANALYZE, BUFFERS)
SELECT * FROM foo
ORDER BY c1;
-- Sort Method: quicksort - все в памяти


-- end






-- -----------
-- JOIN
-- -----------

-- Создадим новую таблицу bar, вставим данные.

CREATE TABLE bar (c1 integer, c2 boolean);

INSERT INTO bar
SELECT i, i%2 = 1
FROM generate_series(1, 500000) AS i;

ANALYZE bar;

-- Посмотрим, что там
SELECT * FROM bar;

-- Запрос по двум таблицам
EXPLAIN ANALYZE
SELECT *
FROM foo
JOIN bar ON foo.c1 = bar.c1;
-- Hash Join
-- Запомним стоимость и время:

-- Hash Join  (cost=13463.00..40547.14 rows=500000 width=42) (actual time=147.369..642.557 rows=500010 loops=1)
--   Hash Cond: (foo.c1 = bar.c1)
--   ->  Seq Scan on foo  (cost=0.00..18334.10 rows=1000010 width=37) (actual time=0.008..93.619 rows=1000010 loops=1)
--   ->  Hash  (cost=7213.00..7213.00 rows=500000 width=5) (actual time=147.125..147.125 rows=500000 loops=1)
--         Buckets: 524288  Batches: 1  Memory Usage: 22163kB
--         ->  Seq Scan on bar  (cost=0.00..7213.00 rows=500000 width=5) (actual time=0.014..45.728 rows=500000 loops=1)
-- Planning time: 0.197 ms
-- Execution time: 658.612 ms


-- Добавим индексы
CREATE INDEX ON foo(c1); -- этот мы удаляли
CREATE INDEX ON bar(c1);

-- Тот же запрос - какой будет тип JOIN?
EXPLAIN ANALYZE
SELECT *
FROM foo
JOIN bar ON foo.c1 = bar.c1;

DROP INDEX foo_c1_idx;
DROP INDEX bar_c1_idx;

-- Merge Join  (cost=1.14..39895.14 rows=500000 width=42) (actual time=0.010..319.921 rows=500010 loops=1)
--   Merge Cond: (foo.c1 = bar.c1)
--   ->  Index Scan using foo_c1_idx on foo  (cost=0.42..34327.57 rows=1000010 width=37) (actual time=0.004..89.986 rows=500011 loops=1)
--   ->  Index Scan using bar_c1_idx on bar  (cost=0.42..15212.42 rows=500000 width=5) (actual time=0.003..76.883 rows=500010 loops=1)
-- Planning time: 0.264 ms
-- Execution time: 336.493 ms

