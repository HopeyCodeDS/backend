-- Waterside Context Database Schema

-- Shipping Orders table
CREATE TABLE IF NOT EXISTS waterside.shipping_orders (
    shipping_order_id VARCHAR(36) PRIMARY KEY,
    shipping_order_number VARCHAR(255) UNIQUE NOT NULL,
    purchase_order_reference VARCHAR(255) NOT NULL,
    vessel_number VARCHAR(255) NOT NULL,
    customer_number VARCHAR(255) NOT NULL,
    estimated_arrival_date TIMESTAMP NOT NULL,
    estimated_departure_date TIMESTAMP NOT NULL,
    actual_arrival_date TIMESTAMP,
    actual_departure_date TIMESTAMP,
    foreman_signature VARCHAR(255),
    validation_date TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    inspection_planned_date TIMESTAMP,
    inspection_completed_date TIMESTAMP,
    inspector_signature VARCHAR(255),
    inspection_status VARCHAR(20),
    bunkering_planned_date TIMESTAMP,
    bunkering_completed_date TIMESTAMP,
    bunkering_status VARCHAR(20),
    INDEX idx_shipping_order_number (shipping_order_number),
    INDEX idx_vessel_number (vessel_number),
    INDEX idx_purchase_order_reference (purchase_order_reference),
    INDEX idx_status (status)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci; 