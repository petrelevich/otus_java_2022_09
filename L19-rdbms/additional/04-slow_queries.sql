-- Должно быть включено расширение pg_stat_statements
-- http://www.postgresql.org/docs/current/static/pgstatstatements.html
-- https://habr.com/ru/post/488968/

CREATE EXTENSION pg_stat_statements;

-- TOP 10 самых медленных запросов
SELECT query AS short_query,
       round(total_time::numeric, 2) AS total_time,
       calls,
       round(mean_time::numeric, 2) AS mean,
       round((100 * total_time / sum(total_time::numeric) OVER ())::numeric, 2) AS percentage_cpu
FROM pg_stat_statements
ORDER BY total_time DESC
LIMIT 10;