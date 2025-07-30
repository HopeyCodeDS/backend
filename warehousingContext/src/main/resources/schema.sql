-- Create warehousing schema if it doesn't exist
DROP SCHEMA IF EXISTS warehousing;
DROP TABLE IF EXISTS warehousing.warehouses;
DROP TABLE IF EXISTS warehousing.warehouse_assignments;
CREATE SCHEMA IF NOT EXISTS warehousing;

-- Warehouses table
CREATE TABLE IF NOT EXISTS warehousing.warehouses (
    warehouse_id VARCHAR(37) PRIMARY KEY,
    warehouse_number VARCHAR(20) NOT NULL UNIQUE,
    seller_id VARCHAR(255) NOT NULL,
    raw_material_name VARCHAR(100) NOT NULL,
    raw_material_price_per_ton DOUBLE NOT NULL,
    raw_material_storage_price_per_ton_per_day DOUBLE NOT NULL,
    current_capacity DOUBLE NOT NULL DEFAULT 0.0,
    max_capacity DOUBLE NOT NULL DEFAULT 500000.0,
    INDEX idx_warehouse_number (warehouse_number),
    INDEX idx_seller_id (seller_id),
    INDEX idx_raw_material_name (raw_material_name),
    INDEX idx_current_capacity (current_capacity)
);

-- Warehouse assignments table (for tracking truck assignments)
CREATE TABLE IF NOT EXISTS warehousing.warehouse_assignments (
    assignment_id VARCHAR(37) PRIMARY KEY,
    warehouse_id VARCHAR(37) NOT NULL,
    license_plate VARCHAR(50) NOT NULL,
    truck_weight DOUBLE NOT NULL,
    raw_material_name VARCHAR(100) NOT NULL,
    assignment_time TIMESTAMP NOT NULL,
    status ENUM('ASSIGNED', 'COMPLETED', 'CANCELLED') NOT NULL DEFAULT 'ASSIGNED',
    INDEX idx_warehouse_id (warehouse_id),
    INDEX idx_license_plate (license_plate),
    INDEX idx_assignment_time (assignment_time),
    INDEX idx_status (status)
);