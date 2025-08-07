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
(UNHEX('550e8400e29b41d4a716446655440101'), 'Warehouse-01', 'seller-001', 'Gypsum', 13.0, 1.0, 0.0, 500000.0),
(UNHEX('550e8400e29b41d4a716446655440102'), 'Warehouse-02', 'seller-001', 'Iron Ore', 110.0, 5.0, 0.0, 500000.0),
(UNHEX('550e8400e29b41d4a716446655440103'), 'Warehouse-03', 'seller-001', 'Cement', 95.0, 3.0, 0.0, 500000.0),
(UNHEX('550e8400e29b41d4a716446655440104'), 'Warehouse-04', 'seller-001', 'Slag', 160.0, 7.0, 0.0, 500000.0),
(UNHEX('550e8400e29b41d4a716446655440105'), 'Warehouse-05', 'seller-001', 'Petcoke', 210.0, 10.0, 0.0, 500000.0); 