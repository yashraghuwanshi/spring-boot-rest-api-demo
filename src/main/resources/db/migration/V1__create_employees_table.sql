CREATE TABLE employees (
  employee_id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  hire_date VARCHAR(255) NOT NULL,
  phone_number BIGINT NOT NULL UNIQUE,
  email_address VARCHAR(255) NOT NULL UNIQUE
);