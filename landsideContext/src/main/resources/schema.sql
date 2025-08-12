-- Drop tables in correct order to avoid foreign key constraint issues
-- DROP TABLE IF EXISTS landside.appointments;
-- DROP TABLE IF EXISTS landside.truck_movements;
-- DROP TABLE IF EXISTS landside.trucks;
-- DROP TABLE IF EXISTS landside.weighing_bridges;

-- Trucks table
CREATE TABLE IF NOT EXISTS landside.trucks (
    truck_id VARCHAR(36) PRIMARY KEY,
    license_plate VARCHAR(50) NOT NULL UNIQUE,
    truck_type ENUM('SMALL', 'MEDIUM', 'LARGE') NOT NULL,
    capacity_in_tons DOUBLE NOT NULL,
    INDEX idx_license_plate (license_plate)
);

-- Weighing bridges table
CREATE TABLE IF NOT EXISTS landside.weighing_bridges (
    bridge_id VARCHAR(36) PRIMARY KEY,
    bridge_number VARCHAR(20) NOT NULL UNIQUE,
    is_available BOOLEAN NOT NULL DEFAULT TRUE,
    INDEX idx_bridge_number (bridge_number),
    INDEX idx_is_available (is_available)
);

-- Truck movements table (updated to match JPA entity)
CREATE TABLE IF NOT EXISTS landside.truck_movements (
    movement_id VARCHAR(36) PRIMARY KEY,
    license_plate VARCHAR(50) NOT NULL,
    entry_time TIMESTAMP NOT NULL,
    current_location ENUM('GATE', 'WEIGHING_BRIDGE', 'WAREHOUSE', 'EXIT') NOT NULL,
    assigned_bridge_number VARCHAR(20) NULL,
    bridge_assignment_time TIMESTAMP NULL,
    truck_weight DOUBLE NULL,
    assigned_warehouse VARCHAR(50) NULL,
    INDEX idx_license_plate (license_plate),
    INDEX idx_current_location (current_location),
    INDEX idx_assigned_bridge_number (assigned_bridge_number)
);

-- Appointments table
CREATE TABLE IF NOT EXISTS landside.appointments (
    appointment_id VARCHAR(36) PRIMARY KEY,
    seller_id VARCHAR(255) NOT NULL,
    truck_id VARCHAR(36) NOT NULL,
    arrival_window_start TIMESTAMP NOT NULL,
    arrival_window_end TIMESTAMP NOT NULL,
    raw_material_name VARCHAR(100) NOT NULL,
    raw_material_price_per_ton DOUBLE NOT NULL,
    raw_material_storage_price_per_ton_per_day DOUBLE NOT NULL,
    status ENUM('SCHEDULED', 'ARRIVED', 'CANCELLED') NOT NULL DEFAULT 'SCHEDULED',
    scheduled_time TIMESTAMP NULL,
    FOREIGN KEY (truck_id) REFERENCES landside.trucks(truck_id),
    INDEX idx_seller_id (seller_id),
    INDEX idx_arrival_window (arrival_window_start, arrival_window_end),
    INDEX idx_status (status)
);

-- Insert default weighing bridges
INSERT INTO landside.weighing_bridges (bridge_id, bridge_number, is_available) VALUES
('550e8400-e29b-41d4-a716-446655440001', 'WB-001', true),
('550e8400-e29b-41d4-a716-446655440002', 'WB-002', true),
('550e8400-e29b-41d4-a716-446655440003', 'WB-003', true),
('550e8400-e29b-41d4-a716-446655440004', 'WB-004', true),
('550e8400-e29b-41d4-a716-446655440005', 'WB-005', true);

-- Weighbridge tickets table
CREATE TABLE IF NOT EXISTS landside.weighbridge_tickets (
    ticket_id BINARY(16) PRIMARY KEY,
    license_plate VARCHAR(50) NOT NULL,
    gross_weight DOUBLE NOT NULL,
    tare_weight DOUBLE NOT NULL,
    net_weight DOUBLE NOT NULL,
    weighing_time TIMESTAMP NOT NULL,
    INDEX idx_license_plate (license_plate),
    INDEX idx_weighing_time (weighing_time)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;