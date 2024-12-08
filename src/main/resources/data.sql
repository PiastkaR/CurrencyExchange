INSERT INTO accounts (first_name, last_name, balance_pln)
SELECT 'Rafal', 'Piastka', 1000.00
WHERE NOT EXISTS (SELECT * FROM accounts);