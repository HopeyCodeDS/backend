-- =====================================================
-- WAREHOUSING CONTEXT - COMPREHENSIVE TEST DATA
-- =====================================================

-- Insert 5 warehouses (one per material type) for KdG customers
-- Each warehouse can store up to 500 kt (500,000 tons) as per assignment
INSERT INTO warehousing.warehouses (warehouse_id, warehouse_number, seller_id, raw_material_name, raw_material_price_per_ton, raw_material_storage_price_per_ton_per_day, current_capacity, max_capacity) VALUES
-- Warehouse-01: Gypsum - seller-001 (low storage cost, high volume material)
(UNHEX('550e8400e29b41d4a716446655440501'), 'Warehouse-01', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'Gypsum', 13.0, 1.0, 250000.0, 500000.0), -- 50% full

-- Warehouse-02: Iron Ore - seller-001 (medium storage cost, high value material)
(UNHEX('550e8400e29b41d4a716446655440502'), 'Warehouse-02', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'Iron_Ore', 110.0, 5.0, 225000.0, 500000.0), -- 45% full

-- Warehouse-03: Cement - seller-001 (medium storage cost, medium value material)
(UNHEX('550e8400e29b41d4a716446655440503'), 'Warehouse-03', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'Cement', 95.0, 3.0, 195000.0, 500000.0), -- 39% full

-- Warehouse-04: Petcoke - seller-001 (high storage cost, high value material)
(UNHEX('550e8400e29b41d4a716446655440504'), 'Warehouse-04', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'Petcoke', 210.0, 10.0, 100000.0, 500000.0), -- 20% full

-- Warehouse-05: Slag - seller-001 (medium-high storage cost, medium value material)
(UNHEX('550e8400e29b41d4a716446655440505'), 'Warehouse-05', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'Slag', 160.0, 7.0, 95000.0, 500000.0); -- 19% full

-- =====================================================
-- WAREHOUSE ASSIGNMENTS (Truck to Warehouse)
-- =====================================================
INSERT INTO warehousing.warehouse_assignments (assignment_id, warehouse_id, license_plate, warehouse_number, raw_material_name, seller_id, truck_weight, assignment_time) VALUES
-- Gypsum assignments to Warehouse-01
(UNHEX('550e8400e29b41d4a716446655440601'), UNHEX('550e8400e29b41d4a716446655440501'), 'TRK-001', 'Warehouse-01', 'Gypsum', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 25000.0, '2025-08-28 08:35:00'),
(UNHEX('550e8400e29b41d4a716446655440602'), UNHEX('550e8400e29b41d4a716446655440501'), 'TRK-002', 'Warehouse-01', 'Gypsum', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 25000.0, '2025-08-28 20:35:00'),
(UNHEX('550e8400e29b41d4a716446655440603'), UNHEX('550e8400e29b41d4a716446655440501'), 'TRK-003', 'Warehouse-01', 'Gypsum', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 25000.0, '2025-08-29 08:35:00'),
(UNHEX('550e8400e29b41d4a716446655440606'), UNHEX('550e8400e29b41d4a716446655440501'), 'TRK-006', 'Warehouse-01', 'Gypsum', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 25000.0, '2025-08-30 14:35:00'),
(UNHEX('550e8400e29b41d4a716446655440612'), UNHEX('550e8400e29b41d4a716446655440501'), 'TRK-012', 'Warehouse-01', 'Gypsum', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 250.0, '2025-08-31 10:35:00'),

-- Iron Ore assignments to Warehouse-02
(UNHEX('550e8400e29b41d4a716446655440604'), UNHEX('550e8400e29b41d4a716446655440502'), 'TRK-004', 'Warehouse-02', 'Iron_Ore', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 25000.0, '2025-08-29 20:35:00'),
(UNHEX('550e8400e29b41d4a716446655440607'), UNHEX('550e8400e29b41d4a716446655440502'), 'TRK-007', 'Warehouse-02', 'Iron_Ore', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 25000.0, '2025-08-30 15:35:00'),
(UNHEX('550e8400e29b41d4a716446655440611'), UNHEX('550e8400e29b41d4a716446655440502'), 'TRK-011', 'Warehouse-02', 'Iron_Ore', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 10000.0, '2025-08-31 09:35:00'),

-- Cement assignments to Warehouse-03
(UNHEX('550e8400e29b41d4a716446655440605'), UNHEX('550e8400e29b41d4a716446655440503'), 'TRK-005', 'Warehouse-03', 'Cement', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 23000.0, '2025-08-30 08:35:00'),
(UNHEX('550e8400e29b41d4a716446655440608'), UNHEX('550e8400e29b41d4a716446655440503'), 'TRK-008', 'Warehouse-03', 'Cement', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 25000.0, '2025-08-30 16:35:00'),
(UNHEX('550e8400e29b41d4a716446655440613'), UNHEX('550e8400e29b41d4a716446655440503'), 'TRK-013', 'Warehouse-03', 'Cement', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 5000.0, '2025-08-31 11:35:00'),

-- Petcoke assignments to Warehouse-04
(UNHEX('550e8400e29b41d4a716446655440609'), UNHEX('550e8400e29b41d4a716446655440504'), 'TRK-009', 'Warehouse-04', 'Petcoke', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 25000.0, '2025-08-30 17:35:00'),

-- Slag assignments to Warehouse-05
(UNHEX('550e8400e29b41d4a716446655440610'), UNHEX('550e8400e29b41d4a716446655440505'), 'TRK-010', 'Warehouse-05', 'Slag', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 25000.0, '2025-08-30 18:35:00');

-- =====================================================
-- PAYLOAD DELIVERY TICKETS (PDTs)
-- =====================================================
INSERT INTO warehousing.payload_delivery_tickets (pdt_id, license_plate, raw_material_name, warehouse_number, conveyor_belt_number, payload_weight, seller_id, delivery_time, new_weighing_bridge_number) VALUES
-- Gypsum PDTs to Warehouse-01
(UNHEX('550e8400e29b41d4a716446655440701'), 'TRK-001', 'Gypsum', 'Warehouse-01', 'CB-GYPSUM-01', 25.0, UNHEX('ef01c728ce3646b5a11084f53fdd9668'), '2025-08-28 09:00:00', 'WB-EXIT-01'),
(UNHEX('550e8400e29b41d4a716446655440702'), 'TRK-002', 'Gypsum', 'Warehouse-01', 'CB-GYPSUM-01', 25.0, UNHEX('ef01c728ce3646b5a11084f53fdd9668'), '2025-08-28 21:00:00', 'WB-EXIT-02'),
(UNHEX('550e8400e29b41d4a716446655440703'), 'TRK-003', 'Gypsum', 'Warehouse-01', 'CB-GYPSUM-01', 25.0, UNHEX('ef01c728ce3646b5a11084f53fdd9668'), '2025-08-29 09:00:00', 'WB-EXIT-03'),
(UNHEX('550e8400e29b41d4a716446655440704'), 'TRK-006', 'Gypsum', 'Warehouse-01', 'CB-GYPSUM-01', 25.0, UNHEX('ef01c728ce3646b5a11084f53fdd9668'), '2025-08-30 14:00:00', 'WB-EXIT-04'),
(UNHEX('550e8400e29b41d4a716446655440705'), 'TRK-012', 'Gypsum', 'Warehouse-01', 'CB-GYPSUM-01', 0.25, UNHEX('ef01c728ce3646b5a11084f53fdd9668'), '2025-08-31 10:00:00', 'WB-EXIT-05'),

-- Iron Ore PDTs to Warehouse-02
(UNHEX('550e8400e29b41d4a716446655440706'), 'TRK-004', 'Iron_Ore', 'Warehouse-02', 'CB-IRON-01', 25.0, UNHEX('ef01c728ce3646b5a11084f53fdd9668'), '2025-08-29 21:00:00', 'WB-EXIT-06'),
(UNHEX('550e8400e29b41d4a716446655440707'), 'TRK-007', 'Iron_Ore', 'Warehouse-02', 'CB-IRON-01', 25.0, UNHEX('ef01c728ce3646b5a11084f53fdd9668'), '2025-08-30 15:00:00', 'WB-EXIT-07'),
(UNHEX('550e8400e29b41d4a716446655440708'), 'TRK-011', 'Iron_Ore', 'Warehouse-02', 'CB-IRON-01', 10.0, UNHEX('ef01c728ce3646b5a11084f53fdd9668'), '2025-08-31 09:00:00', 'WB-EXIT-08'),

-- Cement PDTs to Warehouse-03
(UNHEX('550e8400e29b41d4a716446655440709'), 'TRK-005', 'Cement', 'Warehouse-03', 'CB-CEMENT-01', 23.0, UNHEX('ef01c728ce3646b5a11084f53fdd9668'), '2025-08-30 09:00:00', 'WB-EXIT-09'),
(UNHEX('550e8400e29b41d4a716446655440710'), 'TRK-008', 'Cement', 'Warehouse-03', 'CB-CEMENT-01', 25.0, UNHEX('ef01c728ce3646b5a11084f53fdd9668'), '2025-08-30 16:00:00', 'WB-EXIT-10'),
(UNHEX('550e8400e29b41d4a716446655440711'), 'TRK-013', 'Cement', 'Warehouse-03', 'CB-CEMENT-01', 5.0, UNHEX('ef01c728ce3646b5a11084f53fdd9668'), '2025-08-30 12:00:00', 'WB-EXIT-11'),

-- Petcoke PDTs to Warehouse-04
(UNHEX('550e8400e29b41d4a716446655440712'), 'TRK-009', 'Petcoke', 'Warehouse-04', 'CB-PETCOKE-01', 25.0, UNHEX('ef01c728ce3646b5a11084f53fdd9668'), '2025-08-30 17:00:00', 'WB-EXIT-12'),

-- Slag PDTs to Warehouse-05
(UNHEX('550e8400e29b41d4a716446655440713'), 'TRK-010', 'Slag', 'Warehouse-05', 'CB-SLAG-01', 25.0, UNHEX('ef01c728ce3646b5a11084f53fdd9668'), '2025-08-30 18:00:00', 'WB-EXIT-13');

-- =====================================================
-- WAREHOUSE PROJECTIONS (Read Model)
-- =====================================================
INSERT INTO warehousing.warehouse_projections (warehouse_id, warehouse_number, seller_id, assigned_material, max_capacity, current_capacity) VALUES
(UNHEX('550e8400e29b41d4a716446655440501'), 'Warehouse-01', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'Gypsum', 500000.0, 250000.0), -- 50% full
(UNHEX('550e8400e29b41d4a716446655440502'), 'Warehouse-02', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'Iron_Ore', 500000.0, 225000.0), -- 45% full
(UNHEX('550e8400e29b41d4a716446655440503'), 'Warehouse-03', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'Cement', 500000.0, 195000.0), -- 39% full
(UNHEX('550e8400e29b41d4a716446655440504'), 'Warehouse-04', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'Petcoke', 500000.0, 100000.0), -- 20% full
(UNHEX('550e8400e29b41d4a716446655440505'), 'Warehouse-05', UNHEX('ef01c728ce3646b5a11084f53fdd9668'), 'Slag', 500000.0, 95000.0); -- 19% full