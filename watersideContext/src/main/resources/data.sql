-- =====================================================
-- WATERSIDE CONTEXT - COMPREHENSIVE TEST DATA
-- =====================================================

-- Insert shipping orders with diverse statuses and scenarios
INSERT INTO waterside.shipping_orders (shipping_order_id, shipping_order_number, purchase_order_reference, vessel_number, customer_number, estimated_arrival_date, estimated_departure_date, actual_arrival_date, actual_departure_date, foreman_signature, validation_date, status, inspection_planned_date, inspection_completed_date, inspector_signature, inspection_status, bunkering_planned_date, bunkering_completed_date, bunkering_status) VALUES
-- Completed shipping orders (all operations finished)
(UNHEX('550e8400e29b41d4a716446655440301'), 'SO-2025-001', 'PO-2025-001', 'VESSEL-001', 'CUST-001', '2025-09-03 08:00:00', '2025-09-05 08:00:00', '2025-09-04 10:00:00', '2025-09-05 16:00:00', 'Foreman_Michael_Johnson', '2025-09-04 10:30:00', 'DEPARTED', '2025-09-04 12:00:00', '2025-09-04 14:00:00', 'Inspector_Adrian_Van_Hecke', 'COMPLETED', '2025-09-04 14:00:00', '2025-09-04 18:00:00', 'COMPLETED'),

(UNHEX('550e8400e29b41d4a716446655440302'), 'SO-2025-002', 'PO-2025-002', 'VESSEL-002', 'CUST-002', '2025-09-03 10:00:00', '2025-09-05 12:00:00', '2025-09-04 10:00:00', '2025-09-05 14:00:00', 'Foreman_Michael_Johnson', '2025-09-04 10:30:00', 'DEPARTED', '2025-09-04 12:00:00', '2025-09-04 14:00:00', 'Inspector_Adrian_Van_Hecke', 'COMPLETED', '2025-09-04 14:00:00', '2025-09-04 18:00:00', 'COMPLETED'),

-- Shipping orders ready for loading
(UNHEX('550e8400e29b41d4a716446655440303'), 'SO-2025-003', 'PO-2025-003', 'VESSEL-003', 'CUST-003', '2025-09-04 08:00:00', '2025-09-06 08:00:00', '2025-09-04 08:00:00', '2025-09-06 10:00:00', 'Foreman_Michael_Johnson', '2025-09-04 08:30:00', 'READY_FOR_LOADING', '2025-09-04 10:00:00', '2025-09-04 12:00:00', 'Inspector_Adrian_Van_Hecke', 'COMPLETED', '2025-09-04 12:00:00', '2025-09-04 16:00:00', 'COMPLETED'),

(UNHEX('550e8400e29b41d4a716446655440304'), 'SO-2025-004', 'PO-2025-004', 'VESSEL-004', 'CUST-004', '2025-09-04 10:00:00', '2025-09-06 10:00:00', '2025-09-04 10:00:00', '2025-09-06 12:00:00', 'Foreman_Michael_Johnson', '2025-09-04 10:30:00', 'READY_FOR_LOADING', '2025-09-04 12:00:00', '2025-09-04 14:00:00', 'Inspector_Adrian_Van_Hecke', 'COMPLETED', '2025-09-04 14:00:00', '2025-09-04 18:00:00', 'COMPLETED'),

-- Shipping orders with operations in progress
(UNHEX('550e8400e29b41d4a716446655440305'), 'SO-2025-005', 'PO-2025-005', 'VESSEL-005', 'CUST-005', '2025-09-04 12:00:00', '2025-09-06 12:00:00', '2025-09-04 12:00:00', '2025-09-06 14:00:00', 'Foreman_Michael_Johnson', '2025-09-04 12:30:00', 'INSPECTING', '2025-09-04 14:00:00', '2025-09-04 16:00:00', 'Inspector_Adrian_Van_Hecke', 'IN_PROGRESS', '2025-09-04 16:00:00', '2025-09-04 18:00:00', 'PLANNED'),

(UNHEX('550e8400e29b41d4a716446655440306'), 'SO-2025-006', 'PO-2025-006', 'VESSEL-006', 'CUST-006', '2025-09-04 14:00:00', '2025-09-06 14:00:00', '2025-09-04 14:00:00', '2025-09-06 16:00:00', 'Foreman_Michael_Johnson', '2025-09-04 14:30:00', 'BUNKERING', '2025-09-04 16:00:00', '2025-09-04 18:00:00', 'Inspector_Adrian_Van_Hecke', 'COMPLETED', '2025-09-04 18:00:00', '2025-09-04 20:00:00', 'IN_PROGRESS'),

-- Validated shipping orders waiting for operations
(UNHEX('550e8400e29b41d4a716446655440307'), 'SO-2025-007', 'PO-2025-007', 'VESSEL-007', 'CUST-007', '2025-09-04 16:00:00', '2025-09-06 16:00:00', '2025-09-04 16:00:00', '2025-09-06 18:00:00', 'Foreman_Michael_Johnson', '2025-09-04 16:30:00', 'VALIDATED', '2025-09-04 18:00:00', '2025-09-04 20:00:00', 'Inspector_Adrian_Van_Hecke', 'PLANNED', '2025-09-04 20:00:00', '2025-09-04 22:00:00', 'PLANNED'),

(UNHEX('550e8400e29b41d4a716446655440308'), 'SO-2025-008', 'PO-2025-008', 'VESSEL-008', 'CUST-008', '2025-09-04 18:00:00', '2025-09-06 18:00:00', '2025-09-04 18:00:00', '2025-09-06 20:00:00', 'Foreman_Michael_Johnson', '2025-09-04 18:30:00', 'VALIDATED', '2025-09-04 20:00:00', '2025-09-04 22:00:00', 'Inspector_Adrian_Van_Hecke', 'PLANNED', '2025-09-04 22:00:00', '2025-09-05 00:00:00', 'PLANNED'),

-- Arrived shipping orders waiting for foreman validation
(UNHEX('550e8400e29b41d4a716446655440309'), 'SO-2025-009', 'PO-2025-009', 'VESSEL-009', 'CUST-009', '2025-09-05 08:00:00', '2025-09-07 08:00:00', '2025-09-05 08:00:00', '2025-09-07 10:00:00', 'Foreman_Michael_Johnson', '2025-09-05 08:30:00', 'ARRIVED', '2025-09-05 10:00:00', '2025-09-05 12:00:00', 'Inspector_Adrian_Van_Hecke', 'PLANNED', '2025-09-05 12:00:00', '2025-09-05 14:00:00', 'PLANNED'),

(UNHEX('550e8400e29b41d4a716446655440310'), 'SO-2025-010', 'PO-2025-010', 'VESSEL-010', 'CUST-010', '2025-09-05 10:00:00', '2025-09-07 10:00:00', '2025-09-05 10:00:00', '2025-09-07 12:00:00', 'Foreman_Michael_Johnson', '2025-09-05 10:30:00', 'ARRIVED', '2025-09-05 12:00:00', '2025-09-05 14:00:00', 'Inspector_Adrian_Van_Hecke', 'PLANNED', '2025-09-05 14:00:00', '2025-09-05 16:00:00', 'PLANNED'),

-- Future shipping orders (not yet arrived)
(UNHEX('550e8400e29b41d4a716446655440311'), 'SO-2025-011', 'PO-2025-011', 'VESSEL-011', 'CUST-011', '2025-09-06 08:00:00', '2025-09-08 08:00:00', '2025-09-06 08:00:00', '2025-09-08 10:00:00', 'Foreman_Michael_Johnson', '2025-09-06 08:30:00', 'ARRIVED', '2025-09-06 10:00:00', '2025-09-06 12:00:00', 'Inspector_Adrian_Van_Hecke', 'PLANNED', '2025-09-06 12:00:00', '2025-09-06 14:00:00', 'PLANNED'),

(UNHEX('550e8400e29b41d4a716446655440312'), 'SO-2025-012', 'PO-2025-012', 'VESSEL-012', 'CUST-012', '2025-09-06 10:00:00', '2025-09-08 10:00:00', '2025-09-06 10:00:00', '2025-09-08 12:00:00', 'Foreman_Michael_Johnson', '2025-09-06 10:30:00', 'ARRIVED', '2025-09-06 12:00:00', '2025-09-06 14:00:00', 'Inspector_Adrian_Van_Hecke', 'PLANNED', '2025-09-06 14:00:00', '2025-09-06 16:00:00', 'PLANNED'),

-- High-value cargo shipping orders
(UNHEX('550e8400e29b41d4a716446655440313'), 'SO-2025-013', 'PO-2025-013', 'VESSEL-013', 'CUST-013', '2025-09-05 12:00:00', '2025-09-07 12:00:00', '2025-09-05 12:00:00', '2025-09-07 14:00:00', 'Foreman_Michael_Johnson', '2025-09-05 12:30:00', 'VALIDATED', '2025-09-05 14:00:00', '2025-09-05 16:00:00', 'Inspector_Adrian_Van_Hecke', 'PLANNED', '2025-09-05 16:00:00', '2025-09-05 18:00:00', 'PLANNED'),

(UNHEX('550e8400e29b41d4a716446655440314'), 'SO-2025-014', 'PO-2025-014', 'VESSEL-014', 'CUST-014', '2025-09-05 14:00:00', '2025-09-07 14:00:00', '2025-09-05 14:00:00', '2025-09-07 16:00:00', 'Foreman_Michael_Johnson', '2025-09-05 14:30:00', 'VALIDATED', '2025-09-05 16:00:00', '2025-09-05 18:00:00', 'Inspector_Adrian_Van_Hecke', 'PLANNED', '2025-09-05 18:00:00', '2025-09-05 20:00:00', 'PLANNED'),

-- Bulk cargo shipping orders
(UNHEX('550e8400e29b41d4a716446655440315'), 'SO-2025-015', 'PO-2025-015', 'VESSEL-015', 'CUST-015', '2025-09-05 16:00:00', '2025-09-07 16:00:00', '2025-09-05 16:00:00', '2025-09-07 18:00:00', 'Foreman_Michael_Johnson', '2025-09-05 16:30:00', 'VALIDATED', '2025-09-05 18:00:00', '2025-09-05 20:00:00', 'Inspector_Adrian_Van_Hecke', 'PLANNED', '2025-09-05 20:00:00', '2025-09-05 22:00:00', 'PLANNED'),

-- Emergency/priority shipping orders
(UNHEX('550e8400e29b41d4a716446655440316'), 'SO-2025-016', 'PO-2025-016', 'VESSEL-016', 'CUST-016', '2025-09-04 20:00:00', '2025-09-06 20:00:00', '2025-09-04 20:00:00', '2025-09-06 22:00:00', 'Foreman_Michael_Johnson', '2025-09-04 20:30:00', 'READY_FOR_LOADING', '2025-09-04 22:00:00', '2025-09-05 00:00:00', 'Inspector_Adrian_Van_Hecke', 'COMPLETED', '2025-09-05 00:00:00', '2025-09-05 04:00:00', 'COMPLETED'),

-- Delayed shipping orders
(UNHEX('550e8400e29b41d4a716446655440317'), 'SO-2025-017', 'PO-2025-017', 'VESSEL-017', 'CUST-017', '2025-09-03 06:00:00', '2025-09-05 06:00:00', '2025-09-04 14:00:00', '2025-09-05 08:00:00', 'Foreman_Michael_Johnson', '2025-09-04 14:30:00', 'VALIDATED', '2025-09-04 16:00:00', '2025-09-04 18:00:00', 'Inspector_Adrian_Van_Hecke', 'PLANNED', '2025-09-04 18:00:00', '2025-09-04 20:00:00', 'PLANNED'),

-- Multi-material shipping orders
(UNHEX('550e8400e29b41d4a716446655440318'), 'SO-2025-018', 'PO-2025-018', 'VESSEL-018', 'CUST-018', '2025-09-05 18:00:00', '2025-09-07 18:00:00', '2025-09-05 18:00:00', '2025-09-07 20:00:00', 'Foreman_Michael_Johnson', '2025-09-05 18:30:00', 'VALIDATED', '2025-09-05 20:00:00', '2025-09-05 22:00:00', 'Inspector_Adrian_Van_Hecke', 'PLANNED', '2025-09-05 22:00:00', '2025-09-06 00:00:00', 'PLANNED'),

-- Small cargo shipping orders
(UNHEX('550e8400e29b41d4a716446655440319'), 'SO-2025-019', 'PO-2025-019', 'VESSEL-019', 'CUST-019', '2025-09-05 20:00:00', '2025-09-07 20:00:00', '2025-09-05 20:00:00', '2025-09-07 22:00:00', 'Foreman_Michael_Johnson', '2025-09-05 20:30:00', 'VALIDATED', '2025-09-05 22:00:00', '2025-09-06 00:00:00', 'Inspector_Adrian_Van_Hecke', 'PLANNED', '2025-09-06 00:00:00', '2025-09-06 02:00:00', 'PLANNED'),

-- Weekend arrivals
(UNHEX('550e8400e29b41d4a716446655440320'), 'SO-2025-020', 'PO-2025-020', 'VESSEL-020', 'CUST-020', '2025-09-06 08:00:00', '2025-09-08 08:00:00', '2025-09-06 08:00:00', '2025-09-08 10:00:00', 'Foreman_Michael_Johnson', '2025-09-06 08:30:00', 'ARRIVED', '2025-09-06 10:00:00', '2025-09-06 12:00:00', 'Inspector_Adrian_Van_Hecke', 'PLANNED', '2025-09-06 12:00:00', '2025-09-06 14:00:00', 'PLANNED');
