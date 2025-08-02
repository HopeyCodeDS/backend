-- Create warehousing schema if it doesn't exist
DROP SCHEMA IF EXISTS warehousing;
DROP TABLE IF EXISTS warehousing.warehouses;
DROP TABLE IF EXISTS warehousing.warehouse_assignments;
DROP TABLE IF EXISTS warehousing.payload_delivery_tickets;
DROP TABLE IF EXISTS warehousing.warehouse_activities;
CREATE SCHEMA IF NOT EXISTS warehousing;

-- Warehouses table
CREATE TABLE IF NOT EXISTS warehousing.warehouses (
    warehouse_id BINARY(16) PRIMARY KEY,
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
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Warehouse assignments table (for tracking truck assignments)
CREATE TABLE IF NOT EXISTS warehousing.warehouse_assignments (
    assignment_id BINARY(16) PRIMARY KEY,
    warehouse_id BINARY(16) NOT NULL,
    license_plate VARCHAR(50) NOT NULL,
    truck_weight DOUBLE NOT NULL,
    raw_material_name VARCHAR(100) NOT NULL,
    assignment_time TIMESTAMP NOT NULL,
    status ENUM('ASSIGNED', 'COMPLETED', 'CANCELLED') NOT NULL DEFAULT 'ASSIGNED',
    INDEX idx_warehouse_id (warehouse_id),
    INDEX idx_license_plate (license_plate),
    INDEX idx_assignment_time (assignment_time),
    INDEX idx_status (status)
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Payload Delivery Tickets table
CREATE TABLE IF NOT EXISTS warehousing.payload_delivery_tickets (
    pdt_id VARCHAR(36) PRIMARY KEY,
    license_plate VARCHAR(50) NOT NULL,
    raw_material_name VARCHAR(100) NOT NULL,
    warehouse_number VARCHAR(20) NOT NULL,
    conveyor_belt_number VARCHAR(50) NOT NULL,
    payload_weight DOUBLE NOT NULL,
    seller_id VARCHAR(255) NOT NULL,
    delivery_time TIMESTAMP NOT NULL,
    new_weighing_bridge_number VARCHAR(50) NOT NULL,
    INDEX idx_license_plate (license_plate),
    INDEX idx_warehouse_number (warehouse_number),
    INDEX idx_delivery_time (delivery_time),
    INDEX idx_seller_id (seller_id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Warehouse Activities table (Event Store)
CREATE TABLE IF NOT EXISTS warehousing.warehouse_activities (
    activity_id BINARY(16) PRIMARY KEY,
    warehouse_id BINARY(16) NOT NULL,
    amount DOUBLE NOT NULL,
    action VARCHAR(50) NOT NULL,
    point_in_time TIMESTAMP NOT NULL,
    material_type VARCHAR(100) NOT NULL,
    license_plate VARCHAR(50),
    description TEXT,
    INDEX idx_warehouse_id (warehouse_id),
    INDEX idx_point_in_time (point_in_time),
    INDEX idx_action (action),
    INDEX idx_material_type (material_type)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Purchase Order Fulfillment Tracking table
CREATE TABLE IF NOT EXISTS warehousing.purchase_order_fulfillment_tracking (
    tracking_id VARCHAR(36) PRIMARY KEY,
    purchase_order_number VARCHAR(255) UNIQUE NOT NULL,
    customer_number VARCHAR(255) NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    order_date TIMESTAMP NOT NULL,
    total_value DOUBLE NOT NULL,
    status VARCHAR(20) NOT NULL,
    fulfillment_date TIMESTAMP,
    vessel_number VARCHAR(255),
    INDEX idx_purchase_order_number (purchase_order_number),
    INDEX idx_customer_number (customer_number),
    INDEX idx_status (status),
    INDEX idx_order_date (order_date)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;