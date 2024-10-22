
-- Clear the tables
DELETE FROM landside.appointments; -- Clear dependent records first
DELETE FROM landside.trucks;
DELETE FROM landside.slots;

-- Insert trucks with all fields specified
INSERT INTO landside.trucks (license_plate, warehouse_id, arrival_time, material_type, weighed)
VALUES
    ('BE_VUB-T95', 'WH1-001', '2024-11-01 08:05:00', 'IRON_ORE', false),
    ('BE_VUB-T91', 'WH-002', '2024-11-01 08:10:00', 'GYPSUM', false),
    ('BE_VUB-T92', 'WH-003', '2024-11-01 08:15:00', 'CEMENT', false),
    ('BE_VUB-T129', 'WH-004', '2024-11-01 08:55:00', 'CEMENT', false),
    ('BE_VUB-T130', 'WH-005', '2024-11-01 09:05:00', 'IRON_ORE', false),
    ('BE_VUB-T131', 'WH-006', '2024-11-01 09:10:00', 'GYPSUM', false),
    ('BE_VUB-T132', 'WH-007', '2024-11-01 09:15:00', 'CEMENT', false),
    ('BE_VUB-T169', 'WH-008', '2024-11-01 09:55:00', 'IRON_ORE', false),
    ('BE_VUB-T170', 'WH-009', '2024-11-01 10:05:00', 'IRON_ORE', false),
    ('BE_VUB-T171', 'WH-010', '2024-11-01 10:10:00', 'GYPSUM', false),
    ('BE_VUB-T172', 'WH-011', '2024-11-01 10:15:00', 'CEMENT', false),
    ('BE_VUB-T209', 'WH-012', '2024-11-01 10:55:00', 'CEMENT', false),
    ('BE_VUB-T210', 'WH-013', '2024-11-01 11:05:00', 'IRON_ORE', false),
    ('BE_VUB-T211', 'WH-014', '2024-11-01 11:10:00', 'GYPSUM', false),
    ('BE_VUB-T212', 'WH-015', '2024-11-01 11:15:00', 'CEMENT', false),
    ('BE_VUB-T249', 'WH-016', '2024-11-01 11:55:00', 'IRON_ORE', false),
    ('BE_VUB-T250', 'WH-017', '2024-11-01 12:05:00', 'IRON_ORE', false),
    ('BE_VUB-T251', 'WH-018', '2024-11-01 12:10:00', 'GYPSUM', false),
    ('BE_VUB-T252', 'WH-019', '2024-11-01 12:15:00', 'CEMENT', false),
    ('BE_VUB-T289', 'WH-020', '2024-11-01 12:55:00', 'CEMENT', false);

-- Insert slots
INSERT INTO landside.slots (slot_id, start_time, end_time)
VALUES
    (1, '2024-11-01T08:00:00', '2024-11-01T09:00:00'),
    (2, '2024-11-01T09:00:00', '2024-11-01T10:00:00'),
    (3, '2024-11-01T10:00:00', '2024-11-01T11:00:00'),
    (4, '2024-11-01T11:00:00', '2024-11-01T12:00:00'),
    (5, '2024-11-01T12:00:00', '2024-11-01T13:00:00');

-- For Slot 1 (8:00 AM - 9:00 AM)
INSERT INTO landside.appointments (appointment_id, license_plate, material_type, arrival_window, seller_id, slot_id)
VALUES
    ('c0a80101-0001-0001-0001-000000000001', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T95'), 'IRON_ORE', '2024-11-01T08:05:00', 'b78c50cd-a93b-4881-a37a-bc79408ef9d9', 1),
    ('c0a80101-0001-0001-0001-000000000002', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T91'), 'GYPSUM', '2024-11-01T08:10:00', 'b78c50cd-a93b-4881-a37a-bc79408ef9d6', 1),
    ('c0a80101-0001-0001-0001-000000000003', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T92'), 'CEMENT', '2024-11-01T08:15:00', 'b78c50cd-a93b-4881-a37a-bc79408ef9d7', 1),
    ('c0a80101-0001-0001-0001-000000000040', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T129'), 'CEMENT', '2024-11-01T08:55:00', 'b78c50cd-a93b-4881-a37a-bc79408ef9d8', 1);

-- For Slot 2 (9:00 AM - 10:00 AM)
INSERT INTO landside.appointments (appointment_id, license_plate, material_type, arrival_window, seller_id, slot_id)
VALUES
    ('c0a80101-0001-0001-0001-000000000041', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T130'), 'IRON_ORE', '2024-11-01T09:05:00', 'b78c50cd-a93b-4881-a37a-bc79408ef4a1', 2),
    ('c0a80101-0001-0001-0001-000000000042', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T131'), 'GYPSUM', '2024-11-01T09:10:00', 'b78c50cd-a93b-4881-a37a-bc79408ef3d1', 2),
    ('c0a80101-0001-0001-0001-000000000043', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T132'), 'CEMENT', '2024-11-01T09:15:00', 'b78c50cd-a93b-4881-a37a-bc79408ef4a8', 2),
    ('c0a80101-0001-0001-0001-000000000080', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T169'), 'IRON_ORE', '2024-11-01T09:55:00', 'b78c50cd-a93b-4881-a37a-bc79408ef4a9', 2);

-- For Slot 3 (10:00 AM - 11:00 AM)
INSERT INTO landside.appointments (appointment_id, license_plate, material_type, arrival_window, seller_id, slot_id)
VALUES
    ('c0a80101-0001-0001-0001-000000000081', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T170'), 'IRON_ORE', '2024-11-01T10:05:00', 'b78c50cd-a93b-4881-a37a-bc79408ef975', 3),
    ('c0a80101-0001-0001-0001-000000000082', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T171'), 'GYPSUM', '2024-11-01T10:10:00', 'b78c50cd-a93b-4881-a37a-bc79408ef976', 3),
    ('c0a80101-0001-0001-0001-000000000083', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T172'), 'CEMENT', '2024-11-01T10:15:00', 'b78c50cd-a93b-4881-a37a-bc79408ef977', 3),
    ('c0a80101-0001-0001-0001-000000000120', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T209'), 'CEMENT', '2024-11-01T10:55:00', 'b78c50cd-a93b-4881-a37a-bc79408ef978', 3);

-- For Slot 4 (11:00 AM - 12:00 PM)
INSERT INTO landside.appointments (appointment_id, license_plate, material_type, arrival_window, seller_id, slot_id)
VALUES
    ('c0a80101-0001-0001-0001-000000000121', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T210'), 'IRON_ORE', '2024-11-01T11:05:00', 'b78c50cd-a93b-4881-a37a-bc79408ef979', 4),
    ('c0a80101-0001-0001-0001-000000000122', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T211'), 'GYPSUM', '2024-11-01T11:10:00', 'b78c50cd-a93b-4881-a37a-bc79408ef980', 4),
    ('c0a80101-0001-0001-0001-000000000123', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T212'), 'CEMENT', '2024-11-01T11:15:00', 'b78c50cd-a93b-4881-a37a-bc79408ef981', 4),
    ('c0a80101-0001-0001-0001-000000000160', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T249'), 'IRON_ORE', '2024-11-01T11:55:00', 'b78c50cd-a93b-4881-a37a-bc79408ef982', 4);

-- For Slot 5 (12:00 PM - 1:00 PM)
INSERT INTO landside.appointments (appointment_id, license_plate, material_type, arrival_window, seller_id, slot_id)
VALUES
    ('c0a80101-0001-0001-0001-000000000161', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T250'), 'IRON_ORE', '2024-11-01T12:05:00', 'b78c50cd-a93b-4881-a37a-bc79408ef983', 5),
    ('c0a80101-0001-0001-0001-000000000162', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T251'), 'GYPSUM', '2024-11-01T12:10:00', 'b78c50cd-a93b-4881-a37a-bc79408ef984', 5),
    ('c0a80101-0001-0001-0001-000000000163', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T252'), 'CEMENT', '2024-11-01T12:15:00', 'b78c50cd-a93b-4881-a37a-bc79408ef985', 5),
    ('c0a80101-0001-0001-0001-000000000200', (SELECT license_plate FROM landside.trucks WHERE license_plate = 'BE_VUB-T289'), 'CEMENT', '2024-11-01T12:55:00', 'b78c50cd-a93b-4881-a37a-bc79408ef986', 5);
