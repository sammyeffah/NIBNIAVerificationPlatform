CREATE TABLE IF NOT EXISTS passed_on (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  picLocation VARCHAR(255) NOT NULL UNIQUE
);