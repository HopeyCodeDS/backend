-- Create the landside schema
CREATE SCHEMA IF NOT EXISTS landside;

-- Create test user with password
CREATE USER IF NOT EXISTS 'test_user'@'%' IDENTIFIED BY 'test_password';

-- Grant all privileges to test user on both databases
GRANT ALL PRIVILEGES ON test_db.* TO 'test_user'@'%';
GRANT ALL PRIVILEGES ON landside.* TO 'test_user'@'%';
GRANT CREATE ON *.* TO 'test_user'@'%';  -- Allow creating schemas
GRANT DROP ON *.* TO 'test_user'@'%';    -- Allow dropping schemas

-- Flush privileges
FLUSH PRIVILEGES;