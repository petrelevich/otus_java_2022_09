-- Удаляем предыдущие эксперименты (для работы демо)
DELETE FROM actor WHERE first_name='Otus';
-- Проверми - записей быть не должно
SELECT * FROM actor WHERE first_name='Otus';

-- Успешное завершение транзакции
BEGIN TRANSACTION;
  INSERT INTO actor (first_name, last_name)
  VALUES ('Otus', 'Alex');

  SELECT * FROM actor WHERE first_name='Otus';

  INSERT INTO actor (first_name, last_name)
  VALUES ('Otus', 'Petr');

  SELECT * FROM actor WHERE first_name='Otus';
COMMIT TRANSACTION;

SELECT * FROM actor WHERE first_name='Otus';

-- Откат транзакции, ROLLBACK
BEGIN;
    SELECT * FROM actor WHERE first_name='Otus';

    INSERT INTO actor (first_name, last_name)
    VALUES ('Otus', 'Pavel');

    SELECT * FROM actor WHERE first_name='Otus';

ROLLBACK;

SELECT * FROM actor WHERE first_name='Otus';

-- SAVEPOINT
BEGIN TRANSACTION;
    SELECT * FROM actor WHERE first_name='Otus';

    INSERT INTO actor (first_name, last_name)
    VALUES ('Otus', 'Ivan');

    SELECT * FROM actor WHERE first_name='Otus';

SAVEPOINT INSERT_IVAN;

    INSERT INTO actor (first_name, last_name)
    VALUES ('Otus', 'Pavel');

    SELECT * FROM actor WHERE first_name='Otus';

ROLLBACK TO INSERT_IVAN;
    SELECT * FROM actor WHERE first_name='Otus';

COMMIT;

SELECT * FROM actor WHERE first_name='Otus';

