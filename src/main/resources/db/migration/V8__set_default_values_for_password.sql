UPDATE employees
SET password = SUBSTRING(MD5(RAND()), 1, 10)
WHERE password IS NULL;
