INSERT INTO trucks (license_plate, material_type)
VALUES ('ABC-123', 'IRON_ORE'),
       ('XYZ-789', 'COAL'),
       ('LMN-456', 'SAND');

INSERT INTO appointments (license_plate, arrival_window, seller_id, material_type, weighed)
VALUES ('ABC-123', '2024-10-06T10:00:00', '550e8400-e29b-41d4-a716-446655440000', 'IRON_ORE', false),
       ('XYZ-789', '2024-10-06T11:00:00', '550e8400-e29b-41d4-a716-446655440001', 'COAL', false),
       ('LMN-456', '2024-10-06T09:30:00', '550e8400-e29b-41d4-a716-446655440002', 'SAND', false);