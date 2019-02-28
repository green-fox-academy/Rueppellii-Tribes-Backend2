INSERT INTO roles(role_id, role_enum) VALUES (9, 'USER');
INSERT INTO users(id, username, password) VALUES (2, 'TestUser', '$2a$10$qWbBKC9GbFtGP10i4U2DUe778bM347qFHjSXl2ONAjHjnz.yGQKVS');
INSERT INTO app_user_roles(user_id, role_id) VALUES(2,9);
INSERT INTO kingdoms(id, name, application_user_id) VALUES (2, 'TestVillage', 2);
INSERT INTO troops(id, level, HP, attack, defense, kingdom_id) VALUES (4, 1, 100, 20, 10, 2);
INSERT INTO resources(id, type, dtype, amount, updated_at, resource_per_minute, kingdom_id)
VALUES (5, 'GOLD', 'Gold', 100, 1549895956111, 5, 2);
INSERT INTO resources(id, type, dtype, amount, updated_at, resource_per_minute, kingdom_id)
VALUES (7, 'FOOD', 'Food', 50, 1549895956111, 5, 2);
INSERT INTO buildings(id, type, level, hp, kingdom_id)
VALUES (8, 'Townhall', 1, 10, 2);
INSERT INTO buildings(id, type, level, hp, kingdom_id)
VALUES (9, 'Farm', 1, 1, 2);
INSERT INTO buildings(id, type, level, hp, kingdom_id)
VALUES (10, 'Mine', 1, 5, 2);
INSERT INTO buildings(id, type, level, hp, kingdom_id)
VALUES (11, 'Barrack', 1, 100, 2);
