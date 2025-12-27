CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

-- Optional: Seed the database with some starting data
INSERT INTO users (name, email) VALUES ('user', 'user@example.com');
INSERT INTO users (name, email) VALUES ('admin', 'admin@webapp.com');