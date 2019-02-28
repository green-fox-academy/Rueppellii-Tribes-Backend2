CREATE TABLE IF NOT EXISTS buildings (
  id BIGINT auto_increment,
  type VARCHAR(255),
  level INT,
  hp INT,
  kingdom_id BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id)
);

INSERT INTO buildings(id, type, level, hp, kingdom_id)
  VALUES (1, 'Townhall', 1, 10, 1),
         (2, 'Farm', 1, 1, 1),
         (3, 'Mine', 1, 5, 1),
         (4, 'Barrack', 1, 100, 1),
         (5, 'Townhall', 1, 10, 2),
         (6, 'Farm', 1, 1, 2),
         (7, 'Mine', 1, 5, 2),
         (8, 'Barrack', 1, 100, 2);