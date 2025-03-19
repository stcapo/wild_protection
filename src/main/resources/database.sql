-- Create database
CREATE DATABASE IF NOT EXISTS wp_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE wp_db;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert sample user (username: admin, password: admin123)
INSERT INTO users (username, password, email, full_name) 
VALUES ('admin', 'admin123', 'admin@example.com', '系统管理员')
ON DUPLICATE KEY UPDATE username = 'admin';

-- Insert sample user (username: user, password: user123)
INSERT INTO users (username, password, email, full_name) 
VALUES ('user', 'user123', 'user@example.com', '普通用户')
ON DUPLICATE KEY UPDATE username = 'user'; 