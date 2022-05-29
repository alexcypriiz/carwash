INSERT INTO client(id, email, username, brand_car, number_car, password)
VALUES (0, 'admin', 'admin', 'admin', 'admin', '$2a$10$YLGEv0BWROrlOL53EC2ldOMClQDPpnMMICeNZECf5BR/1/nJF7EjO');

INSERT INTO status(id, name)
VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

INSERT INTO client_roles(client_id, roles_id)
VALUES (0, 2);