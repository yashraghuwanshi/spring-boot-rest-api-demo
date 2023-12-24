UPDATE employees
SET username = SUBSTRING(MD5(RAND()), 1, 10)
WHERE username IS NULL;