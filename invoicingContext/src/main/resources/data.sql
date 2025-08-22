-- =====================================================
-- INVOICING CONTEXT - COMPREHENSIVE TEST DATA
-- =====================================================

-- Insert purchase orders with diverse scenarios
INSERT INTO invoicing.purchase_orders (purchase_order_id, purchase_order_number, customer_number, customer_name, seller_id, seller_name, order_date, status, total_value) VALUES
-- Basic purchase orders
(UNHEX('550e8400e29b41d4a716446655440401'), 'PO-2025-001', 'CUST-001', 'Steel Manufacturing Co.', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'De klant van KDG', '2025-09-01 08:00:00', 'FULFILLED', 2040.0),
(UNHEX('550e8400e29b41d4a716446655440402'), 'PO-2025-002', 'CUST-002', 'Cement Industries Ltd.', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'De klant van KDG', '2025-09-01 08:00:00', 'FULFILLED', 4650.0),
(UNHEX('550e8400e29b41d4a716446655440403'), 'PO-2025-003', 'CUST-003', 'Construction Corp.', UNHEX('ef01c728ce3646b5a11084f53fdd9679'), 'Cement Supplier Ltd.', '2025-09-01 09:00:00', 'PENDING', 2850.0),
(UNHEX('550e8400e29b41d4a716446655440404'), 'PO-2025-004', 'CUST-004', 'Power Generation Ltd.', UNHEX('ef01c728ce3646b5a11084f53fdd9726'), 'Energy Minerals Co.', '2025-09-01 10:00:00', 'PENDING', 5250.0),
(UNHEX('550e8400e29b41d4a716446655440405'), 'PO-2025-005', 'CUST-005', 'Road Construction Co.', UNHEX('ef01c728ce3646b5a11084f53fdd9697'), 'Industrial Slag Ltd.', '2025-09-01 11:00:00', 'PENDING', 4000.0),

-- High-value purchase orders
(UNHEX('550e8400e29b41d4a716446655440406'), 'PO-2025-006', 'CUST-006', 'Steel Works Inc.', UNHEX('ef01c728ce3646b5a11084f53fdd9678'), 'Iron Ore Mining Co.', '2025-08-25 08:00:00', 'FULFILLED', 2750.0),
(UNHEX('550e8400e29b41d4a716446655440407'), 'PO-2025-007', 'CUST-007', 'Cement Factory Ltd.', UNHEX('ef01c728ce3646b5a11084f53fdd9679'), 'Cement Supplier Ltd.', '2025-08-26 09:00:00', 'FULFILLED', 1900.0),
(UNHEX('550e8400e29b41d4a716446655440408'), 'PO-2025-008', 'CUST-008', 'Industrial Minerals Co.', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'De klant van KDG', '2025-08-27 10:00:00', 'FULFILLED', 4435.0),

-- Bulk purchase orders
(UNHEX('550e8400e29b41d4a716446655440409'), 'PO-2025-009', 'CUST-009', 'Bulk Steel Corp.', UNHEX('ef01c728ce3646b5a11084f53fdd9678'), 'Iron Ore Mining Co.', '2025-09-01 12:00:00', 'CONFIRMED', 11000.0),
(UNHEX('550e8400e29b41d4a716446655440410'), 'PO-2025-010', 'CUST-010', 'Mega Construction Ltd.', UNHEX('ef01c728ce3646b5a11084f53fdd9679'), 'Cement Supplier Ltd.', '2025-09-01 13:00:00', 'CONFIRMED', 9500.0),

-- Small purchase orders
(UNHEX('550e8400e29b41d4a716446655440411'), 'PO-2025-011', 'CUST-011', 'Small Workshop', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'De klant van KDG', '2025-09-01 14:00:00', 'PENDING', 325.0),
(UNHEX('550e8400e29b41d4a716446655440412'), 'PO-2025-012', 'CUST-012', 'Local Builder', UNHEX('ef01c728ce3646b5a11084f53fdd9697'), 'Industrial Slag Ltd.', '2025-09-01 15:00:00', 'PENDING', 800.0),

-- Cancelled purchase orders
(UNHEX('550e8400e29b41d4a716446655440413'), 'PO-2025-013', 'CUST-013', 'Cancelled Order Co.', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'De klant van KDG', '2025-08-30 08:00:00', 'CANCELLED', 1300.0),
(UNHEX('550e8400e29b41d4a716446655440414'), 'PO-2025-014', 'CUST-014', 'Failed Delivery Ltd.', UNHEX('ef01c728ce3646b5a11084f53fdd9678'), 'Iron Ore Mining Co.', '2025-08-30 09:00:00', 'CANCELLED', 2200.0),

-- Future purchase orders
(UNHEX('550e8400e29b41d4a716446655440415'), 'PO-2025-015', 'CUST-015', 'Future Steel Co.', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'De klant van KDG', '2025-09-02 08:00:00', 'PENDING', 3000.0),
(UNHEX('550e8400e29b41d4a716446655440416'), 'PO-2025-016', 'CUST-016', 'Next Gen Construction', UNHEX('ef01c728ce3646b5a11084f53fdd9679'), 'Cement Supplier Ltd.', '2025-09-02 09:00:00', 'PENDING', 4750.0),

-- Emergency purchase orders
(UNHEX('550e8400e29b41d4a716446655440417'), 'PO-2025-017', 'CUST-017', 'Emergency Repair Co.', UNHEX('ef01c728ce3646b5a11084f53fdd9726'), 'Energy Minerals Co.', '2025-09-01 16:00:00', 'CONFIRMED', 4200.0),
(UNHEX('550e8400e29b41d4a716446655440418'), 'PO-2025-018', 'CUST-018', 'Urgent Supply Ltd.', UNHEX('ef01c728ce3646b5a11084f53fdd9697'), 'Industrial Slag Ltd.', '2025-09-01 17:00:00', 'CONFIRMED', 3200.0),

-- Multi-material purchase orders
(UNHEX('550e8400e29b41d4a716446655440419'), 'PO-2025-019', 'CUST-019', 'Multi-Material Corp.', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'De klant van KDG', '2025-09-01 18:00:00', 'PENDING', 5800.0),
(UNHEX('550e8400e29b41d4a716446655440420'), 'PO-2025-020', 'CUST-020', 'Complex Construction Ltd.', UNHEX('ef01c728ce3646b5a11084f53fdd9678'), 'Iron Ore Mining Co.', '2025-09-01 19:00:00', 'PENDING', 7200.0);

-- Insert purchase order lines
INSERT INTO invoicing.purchase_order_lines (line_id, purchase_order_id, line_number, raw_material_name, amount_in_tons, price_per_ton) VALUES
-- PO-2025-001 lines
(UNHEX('550e8400e29b41d4a716446655440501'), UNHEX('550e8400e29b41d4a716446655440401'), 1, 'Gypsum', 30.0, 13.0),
(UNHEX('550e8400e29b41d4a716446655440502'), UNHEX('550e8400e29b41d4a716446655440401'), 2, 'Iron_Ore', 15.0, 110.0),

-- PO-2025-002 lines
(UNHEX('550e8400e29b41d4a716446655440503'), UNHEX('550e8400e29b41d4a716446655440402'), 1, 'Iron_Ore', 25.0, 110.0),
(UNHEX('550e8400e29b41d4a716446655440504'), UNHEX('550e8400e29b41d4a716446655440402'), 2, 'Cement', 20.0, 95.0),

-- PO-2025-003 lines
(UNHEX('550e8400e29b41d4a716446655440505'), UNHEX('550e8400e29b41d4a716446655440403'), 1, 'Cement', 30.0, 95.0),

-- PO-2025-004 lines
(UNHEX('550e8400e29b41d4a716446655440506'), UNHEX('550e8400e29b41d4a716446655440404'), 1, 'Petcoke', 25.0, 210.0),

-- PO-2025-005 lines
(UNHEX('550e8400e29b41d4a716446655440507'), UNHEX('550e8400e29b41d4a716446655440405'), 1, 'Slag', 25.0, 160.0),

-- PO-2025-006 lines
(UNHEX('550e8400e29b41d4a716446655440508'), UNHEX('550e8400e29b41d4a716446655440406'), 1, 'Iron_Ore', 25.0, 110.0),

-- PO-2025-007 lines
(UNHEX('550e8400e29b41d4a716446655440509'), UNHEX('550e8400e29b41d4a716446655440407'), 1, 'Cement', 20.0, 95.0),

-- PO-2025-008 lines
(UNHEX('550e8400e29b41d4a716446655440510'), UNHEX('550e8400e29b41d4a716446655440408'), 1, 'Gypsum', 20.0, 13.0),
(UNHEX('550e8400e29b41d4a716446655440511'), UNHEX('550e8400e29b41d4a716446655440408'), 2, 'Iron_Ore', 25.0, 110.0),
(UNHEX('550e8400e29b41d4a716446655440512'), UNHEX('550e8400e29b41d4a716446655440408'), 3, 'Cement', 15.0, 95.0),

-- Additional PO lines for bulk orders
(UNHEX('550e8400e29b41d4a716446655440513'), UNHEX('550e8400e29b41d4a716446655440409'), 1, 'Iron_Ore', 100.0, 110.0),
(UNHEX('550e8400e29b41d4a716446655440514'), UNHEX('550e8400e29b41d4a716446655440410'), 1, 'Cement', 100.0, 95.0),

-- Small PO lines
(UNHEX('550e8400e29b41d4a716446655440515'), UNHEX('550e8400e29b41d4a716446655440411'), 1, 'Gypsum', 25.0, 13.0),
(UNHEX('550e8400e29b41d4a716446655440516'), UNHEX('550e8400e29b41d4a716446655440412'), 1, 'Slag', 5.0, 160.0),

-- Cancelled PO lines
(UNHEX('550e8400e29b41d4a716446655440517'), UNHEX('550e8400e29b41d4a716446655440413'), 1, 'Gypsum', 100.0, 13.0),
(UNHEX('550e8400e29b41d4a716446655440518'), UNHEX('550e8400e29b41d4a716446655440414'), 1, 'Iron_Ore', 20.0, 110.0),

-- Future PO lines
(UNHEX('550e8400e29b41d4a716446655440519'), UNHEX('550e8400e29b41d4a716446655440415'), 1, 'Gypsum', 50.0, 13.0),
(UNHEX('550e8400e29b41d4a716446655440520'), UNHEX('550e8400e29b41d4a716446655440415'), 2, 'Iron_Ore', 20.0, 110.0),
(UNHEX('550e8400e29b41d4a716446655440521'), UNHEX('550e8400e29b41d4a716446655440416'), 1, 'Cement', 50.0, 95.0),

-- Emergency PO lines
(UNHEX('550e8400e29b41d4a716446655440522'), UNHEX('550e8400e29b41d4a716446655440417'), 1, 'Petcoke', 20.0, 210.0),
(UNHEX('550e8400e29b41d4a716446655440523'), UNHEX('550e8400e29b41d4a716446655440418'), 1, 'Slag', 20.0, 160.0),

-- Multi-material PO lines
(UNHEX('550e8400e29b41d4a716446655440524'), UNHEX('550e8400e29b41d4a716446655440419'), 1, 'Gypsum', 100.0, 13.0),
(UNHEX('550e8400e29b41d4a716446655440525'), UNHEX('550e8400e29b41d4a716446655440419'), 2, 'Iron_Ore', 30.0, 110.0),
(UNHEX('550e8400e29b41d4a716446655440526'), UNHEX('550e8400e29b41d4a716446655440419'), 3, 'Cement', 20.0, 95.0),
(UNHEX('550e8400e29b41d4a716446655440527'), UNHEX('550e8400e29b41d4a716446655440420'), 1, 'Iron_Ore', 50.0, 110.0),
(UNHEX('550e8400e29b41d4a716446655440528'), UNHEX('550e8400e29b41d4a716446655440420'), 2, 'Cement', 20.0, 95.0);

-- Insert commission fees for fulfilled purchase orders
INSERT INTO invoicing.commission_fees (commission_fee_id, purchase_order_number, customer_number, seller_id, amount, calculation_date) VALUES
-- Commission fees for fulfilled orders (1% of total value)
(UNHEX('550e8400e29b41d4a716446655440601'), 'PO-2025-001', 'CUST-001', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 20.40, '2025-09-05 16:00:00'),
(UNHEX('550e8400e29b41d4a716446655440602'), 'PO-2025-002', 'CUST-002', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 46.50, '2025-09-05 14:00:00'),
(UNHEX('550e8400e29b41d4a716446655440603'), 'PO-2025-006', 'CUST-006', UNHEX('ef01c728ce3646b5a11084f53fdd9678'), 27.50, '2025-09-03 14:00:00'),
(UNHEX('550e8400e29b41d4a716446655440604'), 'PO-2025-007', 'CUST-007', UNHEX('ef01c728ce3646b5a11084f53fdd9679'), 19.00, '2025-09-03 16:00:00'),
(UNHEX('550e8400e29b41d4a716446655440605'), 'PO-2025-008', 'CUST-008', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 44.35, '2025-09-04 10:00:00');

-- Insert storage fees for daily calculations
-- INSERT INTO invoicing.storage_fees (storage_fee_id, calculation_date, warehouse_number, material_type, seller_id, total_daily_fee, total_delivery_batches) VALUES
-- -- Storage fees for 2025-09-03 (pre-loading)
-- (UNHEX('550e8400e29b41d4a716446655440701'), '2025-09-03', 'Warehouse-01', 'Gypsum', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 75.00, 3),
-- (UNHEX('550e8400e29b41d4a716446655440702'), '2025-09-03', 'Warehouse-02', 'Iron_Ore', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 625.00, 1),
-- (UNHEX('550e8400e29b41d4a716446655440703'), '2025-09-03', 'Warehouse-03', 'Cement', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 267.00, 1),
-- (UNHEX('550e8400e29b41d4a716446655440706'), '2025-09-03', 'Warehouse-11', 'Petcoke', UNHEX('ef01c728ce3646b5a11084f53fdd9726'), 750.00, 1),
-- (UNHEX('550e8400e29b41d4a716446655440707'), '2025-09-03', 'Warehouse-13', 'Slag', UNHEX('ef01c728ce3646b5a11084f53fdd9697'), 623.00, 1),

-- -- Storage fees for 2025-09-04 (post-loading)
-- (UNHEX('550e8400e29b41d4a716446655440708'), '2025-09-04', 'Warehouse-01', 'Gypsum', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 45.00, 3),
-- (UNHEX('550e8400e29b41d4a716446655440709'), '2025-09-04', 'Warehouse-02', 'Iron_Ore', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 610.00, 1),
-- (UNHEX('550e8400e29b41d4a716446655440710'), '2025-09-04', 'Warehouse-03', 'Cement', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 267.00, 1),
-- (UNHEX('550e8400e29b41d4a716446655440711'), '2025-09-04', 'Warehouse-06', 'Iron_Ore', UNHEX('ef01c728ce3646b5a11084f53fdd9678'), 875.00, 1),
-- (UNHEX('550e8400e29b41d4a716446655440712'), '2025-09-04', 'Warehouse-09', 'Cement', UNHEX('ef01c728ce3646b5a11084f53fdd9679'), 430.00, 1),
-- (UNHEX('550e8400e29b41d4a716446655440713'), '2025-09-04', 'Warehouse-11', 'Petcoke', UNHEX('ef01c728ce3646b5a11084f53fdd9726'), 750.00, 1),
-- (UNHEX('550e8400e29b41d4a716446655440714'), '2025-09-04', 'Warehouse-13', 'Slag', UNHEX('ef01c728ce3646b5a11084f53fdd9697'), 623.00, 1),

-- -- Storage fees for different dates showing accumulation
-- (UNHEX('550e8400e29b41d4a716446655440715'), '2025-08-28', 'Warehouse-01', 'Gypsum', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 25.00, 1),
-- (UNHEX('550e8400e29b41d4a716446655440716'), '2025-08-29', 'Warehouse-01', 'Gypsum', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 50.00, 2),
-- (UNHEX('550e8400e29b41d4a716446655440717'), '2025-08-30', 'Warehouse-01', 'Gypsum', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 75.00, 3),
-- (UNHEX('550e8400e29b41d4a716446655440718'), '2025-08-31', 'Warehouse-01', 'Gypsum', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 100.00, 4),
-- (UNHEX('550e8400e29b41d4a716446655440719'), '2025-09-01', 'Warehouse-01', 'Gypsum', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 125.00, 5),
-- (UNHEX('550e8400e29b41d4a716446655440720'), '2025-09-02', 'Warehouse-01', 'Gypsum', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 150.00, 6);

-- Insert storage tracking records for material movement tracking
-- =====================================================
-- STORAGE TRACKING RECORDS
-- =====================================================
-- INSERT INTO invoicing.storage_tracking (tracking_id, warehouse_number, customer_number, material_type, tons_stored, remaining_tons, delivery_time, pdt_id, storage_cost_calculation_date, number_of_days, cost_in_dollars, storage_cost, is_seller_tracking) VALUES
-- -- Gypsum tracking records (FIFO order) - Warehouse-01: 125.25 tons total
-- (UNHEX('550e8400e29b41d4a716446655440801'), 'Warehouse-01', 'ef01c728-ce36-46b5-a110-84f53fdd9668', 'Gypsum', 25.0, 0.0, '2025-08-28 09:00:00', UNHEX('550e8400e29b41d4a716446655440701'), '2025-09-03', 6, 150.0, 150.0, TRUE),
-- (UNHEX('550e8400e29b41d4a716446655440802'), 'Warehouse-01', 'ef01c728-ce36-46b5-a110-84f53fdd9668', 'Gypsum', 25.0, 0.0, '2025-08-28 21:00:00', UNHEX('550e8400e29b41d4a716446655440702'), '2025-09-03', 5, 125.0, 125.0, TRUE),
-- (UNHEX('550e8400e29b41d4a716446655440803'), 'Warehouse-01', 'ef01c728-ce36-46b5-a110-84f53fdd9668', 'Gypsum', 25.0, 25.0, '2025-08-29 09:00:00', UNHEX('550e8400e29b41d4a716446655440703'), '2025-09-03', 4, 100.0, 100.0, TRUE),
-- (UNHEX('550e8400e29b41d4a716446655440804'), 'Warehouse-01', 'ef01c728-ce36-46b5-a110-84f53fdd9668', 'Gypsum', 25.0, 25.0, '2025-08-30 14:00:00', UNHEX('550e8400e29b41d4a716446655440704'), '2025-09-03', 3, 75.0, 75.0, TRUE),
-- (UNHEX('550e8400e29b41d4a716446655440805'), 'Warehouse-01', 'ef01c728-ce36-46b5-a110-84f53fdd9668', 'Gypsum', 25.25, 25.25, '2025-08-31 10:00:00', UNHEX('550e8400e29b41d4a716446655440705'), '2025-09-03', 2, 50.5, 50.5, TRUE),

-- -- Iron Ore tracking records (FIFO order) - Warehouse-02: 60 tons total
-- (UNHEX('550e8400e29b41d4a716446655440806'), 'Warehouse-02', 'ef01c728-ce36-46b5-a110-84f53fdd9668', 'Iron_Ore', 25.0, 25.0, '2025-08-29 21:00:00', UNHEX('550e8400e29b41d4a716446655440706'), '2025-09-03', 4, 500.0, 500.0, TRUE),
-- (UNHEX('550e8400e29b41d4a716446655440807'), 'Warehouse-02', 'ef01c728-ce36-46b5-a110-84f53fdd9668', 'Iron_Ore', 25.0, 25.0, '2025-08-30 15:00:00', UNHEX('550e8400e29b41d4a716446655440707'), '2025-09-03', 3, 375.0, 375.0, TRUE),
-- (UNHEX('550e8400e29b41d4a716446655440808'), 'Warehouse-02', 'ef01c728-ce36-46b5-a110-84f53fdd9668', 'Iron_Ore', 10.0, 10.0, '2025-08-31 09:00:00', UNHEX('550e8400e29b41d4a716446655440708'), '2025-09-03', 2, 100.0, 100.0, TRUE),

-- -- Cement tracking records (FIFO order) - Warehouse-03: 53 tons total
-- (UNHEX('550e8400e29b41d4a716446655440809'), 'Warehouse-03', 'ef01c728-ce36-46b5-a110-84f53fdd9668', 'Cement', 23.0, 23.0, '2025-08-30 09:00:00', UNHEX('550e8400e29b41d4a716446655440709'), '2025-09-03', 3, 207.0, 207.0, TRUE),
-- (UNHEX('550e8400e29b41d4a716446655440810'), 'Warehouse-03', 'ef01c728-ce36-46b5-a110-84f53fdd9668', 'Cement', 25.0, 25.0, '2025-08-30 16:00:00', UNHEX('550e8400e29b41d4a716446655440710'), '2025-09-03', 3, 225.0, 225.0, TRUE),
-- (UNHEX('550e8400e29b41d4a716446655440811'), 'Warehouse-03', 'ef01c728-ce36-46b5-a110-84f53fdd9668', 'Cement', 5.0, 5.0, '2025-08-30 12:00:00', UNHEX('550e8400e29b41d4a716446655440711'), '2025-09-03', 3, 45.0, 45.0, TRUE),

-- -- Petcoke tracking records - Warehouse-04: 25 tons total
-- (UNHEX('550e8400e29b41d4a716446655440812'), 'Warehouse-04', 'ef01c728-ce36-46b5-a110-84f53fdd9668', 'Petcoke', 25.0, 25.0, '2025-08-30 17:00:00', UNHEX('550e8400e29b41d4a716446655440712'), '2025-09-03', 3, 750.0, 750.0, TRUE),

-- -- Slag tracking records - Warehouse-05: 25 tons total
-- (UNHEX('550e8400e29b41d4a716446655440813'), 'Warehouse-05', 'ef01c728-ce36-46b5-a110-84f53fdd9668', 'Slag', 25.0, 25.0, '2025-08-30 18:00:00', UNHEX('550e8400e29b41d4a716446655440713'), '2025-09-03', 3, 525.0, 525.0, TRUE);