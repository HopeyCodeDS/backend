-- INSERT INTO trucks (license_plate, material_type)
-- VALUES ('ABC-123', 'IRON_ORE'),
--        ('XYZ-789', 'GYPSUM'),
--        ('LMN-456', 'SAND');
--

# -- Drop the foreign key constraint
# ALTER TABLE appointments DROP FOREIGN KEY FKf8qrv9g386dae81yfkj1qgs77;
#
# -- Modify the `slot_id` column in both tables (ensure correct type)
# ALTER TABLE appointments MODIFY slot_id VARCHAR(255);
# ALTER TABLE slots MODIFY slot_id VARCHAR(255) NOT NULL;
#
# -- Recreate the foreign key constraint
# ALTER TABLE appointments
#     ADD CONSTRAINT FKf8qrv9g386dae81yfkj1qgs77 FOREIGN KEY (slot_id) REFERENCES slots(slot_id);


-- Seeding Slots (SlotEntity) that can handle up to 40 trucks per hour
-- Adding more slots from 8:00 AM to 12:00 PM
INSERT INTO slots (slot_id, start_time, end_time)
VALUES
    (1, '2024-11-01T08:00:00', '2024-11-01T09:00:00'),
    (2, '2024-11-01T09:00:00', '2024-11-01T10:00:00'),
    (3, '2024-11-01T10:00:00', '2024-11-01T11:00:00'),
    (4, '2024-11-01T11:00:00', '2024-11-01T12:00:00'),
    (5, '2024-11-01T12:00:00', '2024-11-01T13:00:00');


-- For Slot 1 (8:00 AM - 9:00 AM)
INSERT INTO appointments (appointment_id, license_plate, material_type, arrival_window, seller_id, slot_id)
VALUES
    ('c0a80101-0001-0001-0001-000000000001', 'BE_VUB-T95', 'IRON_ORE', '2024-11-01T08:05:00', 'b78c50cd-a93b-4881-a37a-bc79408ef9d9', 1),
    ('c0a80101-0001-0001-0001-000000000002', 'BE_VUB-T91', 'GYPSUM', '2024-11-01T08:10:00', 'b78c50cd-a93b-4881-a37a-bc79408ef9d6', 1),
    ('c0a80101-0001-0001-0001-000000000003', 'BE_VUB-T92', 'CEMENT', '2024-11-01T08:15:00', 'b78c50cd-a93b-4881-a37a-bc79408ef9d7', 1),
    ('c0a80101-0001-0001-0001-000000000040', 'BE_VUB-T129', 'CEMENT', '2024-11-01T08:55:00', 'b78c50cd-a93b-4881-a37a-bc79408ef9d8', 1);

-- For Slot 2 (9:00 AM - 10:00 AM)
INSERT INTO appointments (appointment_id, license_plate, material_type, arrival_window, seller_id, slot_id)
VALUES
    ('c0a80101-0001-0001-0001-000000000041', 'BE_VUB-T130', 'IRON_ORE', '2024-11-01T09:05:00', 'b78c50cd-a93b-4881-a37a-bc79408ef4a1', 2),
    ('c0a80101-0001-0001-0001-000000000042', 'BE_VUB-T131', 'GYPSUM', '2024-11-01T09:10:00', 'b78c50cd-a93b-4881-a37a-bc79408ef3d1', 2),
    ('c0a80101-0001-0001-0001-000000000043', 'BE_VUB-T132', 'CEMENT', '2024-11-01T09:15:00', 'b78c50cd-a93b-4881-a37a-bc79408ef4a8', 2),
    ('c0a80101-0001-0001-0001-000000000080', 'BE_VUB-T169', 'IRON_ORE', '2024-11-01T09:55:00', 'b78c50cd-a93b-4881-a37a-bc79408ef4a9', 2);

-- For Slot 3 (10:00 AM - 11:00 AM)
INSERT INTO appointments (appointment_id, license_plate, material_type, arrival_window, seller_id, slot_id)
VALUES
    ('c0a80101-0001-0001-0001-000000000081', 'BE_VUB-T170', 'IRON_ORE', '2024-11-01T10:05:00', 'b78c50cd-a93b-4881-a37a-bc79408ef975', 3),
    ('c0a80101-0001-0001-0001-000000000082', 'BE_VUB-T171', 'GYPSUM', '2024-11-01T10:10:00', 'b78c50cd-a93b-4881-a37a-bc79408ef976', 3),
    ('c0a80101-0001-0001-0001-000000000083', 'BE_VUB-T172', 'CEMENT', '2024-11-01T10:15:00', 'b78c50cd-a93b-4881-a37a-bc79408ef977', 3),
    ('c0a80101-0001-0001-0001-000000000120', 'BE_VUB-T209', 'CEMENT', '2024-11-01T10:55:00', 'b78c50cd-a93b-4881-a37a-bc79408ef978', 3);

-- For Slot 4 (11:00 AM - 12:00 PM)
INSERT INTO appointments (appointment_id, license_plate, material_type, arrival_window, seller_id, slot_id)
VALUES
    ('c0a80101-0001-0001-0001-000000000121', 'BE_VUB-T210', 'IRON_ORE', '2024-11-01T11:05:00', 'b78c50cd-a93b-4881-a37a-bc79408ef979', 4),
    ('c0a80101-0001-0001-0001-000000000122', 'BE_VUB-T211', 'GYPSUM', '2024-11-01T11:10:00', 'b78c50cd-a93b-4881-a37a-bc79408ef980', 4),
    ('c0a80101-0001-0001-0001-000000000123', 'BE_VUB-T212', 'CEMENT', '2024-11-01T11:15:00', 'b78c50cd-a93b-4881-a37a-bc79408ef981', 4),
    ('c0a80101-0001-0001-0001-000000000160', 'BE_VUB-T249', 'IRON_ORE', '2024-11-01T11:55:00', 'b78c50cd-a93b-4881-a37a-bc79408ef982', 4);

-- For Slot 5 (12:00 PM - 1:00 PM)
INSERT INTO appointments (appointment_id, license_plate, material_type, arrival_window, seller_id, slot_id)
VALUES
    ('c0a80101-0001-0001-0001-000000000161', 'BE_VUB-T250', 'IRON_ORE', '2024-11-01T12:05:00', 'b78c50cd-a93b-4881-a37a-bc79408ef983', 5),
    ('c0a80101-0001-0001-0001-000000000162', 'BE_VUB-T251', 'GYPSUM', '2024-11-01T12:10:00', 'b78c50cd-a93b-4881-a37a-bc79408ef984', 5),
    ('c0a80101-0001-0001-0001-000000000163', 'BE_VUB-T252', 'CEMENT', '2024-11-01T12:15:00', 'b78c50cd-a93b-4881-a37a-bc79408ef985', 5),
    ('c0a80101-0001-0001-0001-000000000200', 'BE_VUB-T289', 'CEMENT', '2024-11-01T12:55:00', 'b78c50cd-a93b-4881-a37a-bc79408ef986', 5);
