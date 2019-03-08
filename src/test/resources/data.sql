INSERT INTO users(id, username, password)
  VALUES (1, 'TestUser1', 'password'),
         (2, 'TestUser2', 'password');

INSERT INTO roles(role_id, role_enum)
  VALUES (1, 'USER');

INSERT INTO app_user_roles(user_id, role_id)
  VALUES (1, 1),
         (2, 1);

INSERT INTO kingdoms(id, name, application_user_id)
  VALUES (1, 'TestVillage', 1),
         (2, 'TestKingdom', 2);

INSERT INTO resources(id, type, dtype, amount, updated_at, resource_per_minute, kingdom_id)
  VALUES (1, 'GOLD', 'Gold', 100, NULL, 5, 1),
         (2, 'FOOD', 'Food', 50, NULL, 5, 1),
         (3, 'GOLD', 'Gold', 100, NULL, 5, 2),
         (4, 'FOOD', 'Food', 50, NULL, 5, 2);

INSERT INTO buildings(id, type, level, hp, kingdom_id)
  VALUES (1, 'TownHall', 1, 10, 1),
         (2, 'Farm', 1, 1, 1),
         (3, 'Mine', 1, 5, 1),
         (4, 'Barracks', 1, 100, 1),
         (5, 'TownHall', 1, 10, 2),
         (6, 'Farm', 1, 1, 2),
         (7, 'Mine', 1, 5, 2),
         (8, 'Barracks', 1, 100, 2);

INSERT INTO troops (id, level, HP, attack, defense, kingdom_id)
  VALUES (1, 1, 10, 1, 1, 1),
         (2, 1, 10, 1, 1, 1),
         (3, 1, 10, 1, 1, 1),
         (4, 1, 10, 1, 1, 2),
         (5, 2, 10, 5, 5, 2),
         (6, 3, 10, 25, 25, 2);