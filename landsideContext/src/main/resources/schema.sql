-- Create landside schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS landside;

-- Trucks table
CREATE TABLE IF NOT EXISTS landside.trucks (
    truck_id VARCHAR(36) PRIMARY KEY,
    license_plate VARCHAR(50) NOT NULL UNIQUE,
    truck_type ENUM('SMALL', 'MEDIUM', 'LARGE') NOT NULL,
    capacity_in_tons DOUBLE NOT NULL,
    INDEX idx_license_plate (license_plate)
);

-- Appointments table
CREATE TABLE IF NOT EXISTS landside.appointments (
    appointment_id VARCHAR(36) PRIMARY KEY,
    seller_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    arrival_window_start TIMESTAMP NOT NULL,
    arrival_window_end TIMESTAMP NOT NULL,
    raw_material_name VARCHAR(100) NOT NULL,
    raw_material_price_per_ton DOUBLE NOT NULL,
    raw_material_storage_price_per_ton_per_day DOUBLE NOT NULL,
    truck_id VARCHAR(36) NOT NULL,
    status ENUM('SCHEDULED', 'ARRIVED', 'DEPARTED', 'CANCELLED') NOT NULL DEFAULT 'SCHEDULED',
    actual_arrival_time TIMESTAMP NULL,
    FOREIGN KEY (truck_id) REFERENCES landside.trucks(truck_id),
    INDEX idx_seller_id (seller_id),
    INDEX idx_arrival_window (arrival_window_start, arrival_window_end),
    INDEX idx_created_at (created_at),
    INDEX idx_status (status),
    INDEX idx_actual_arrival_time (actual_arrival_time)
); 