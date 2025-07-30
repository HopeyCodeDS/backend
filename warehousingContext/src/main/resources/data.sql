-- Insert default warehouses for testing
INSERT INTO warehousing.warehouses (
    warehouse_id, 
    warehouse_number, 
    seller_id, 
    raw_material_name, 
    raw_material_price_per_ton, 
    raw_material_storage_price_per_ton_per_day, 
    current_capacity, 
    max_capacity
) VALUES
('550e8400-e29b-41d4-a716-446655440101', 'WH-001', 'seller-001', 'Gypsum', 13.0, 1.0, 0.0, 500000.0),
('550e8400-e29b-41d4-a716-446655440102', 'WH-002', 'seller-002', 'Iron Ore', 110.0, 5.0, 0.0, 500000.0),
('550e8400-e29b-41d4-a716-446655440103', 'WH-003', 'seller-003', 'Cement', 95.0, 3.0, 0.0, 500000.0),
('550e8400-e29b-41d4-a716-446655440104', 'WH-004', 'seller-004', 'Slag', 160.0, 7.0, 0.0, 500000.0),
('550e8400-e29b-41d4-a716-446655440105', 'WH-005', 'seller-005', 'Petcoke', 210.0, 10.0, 0.0, 500000.0); 