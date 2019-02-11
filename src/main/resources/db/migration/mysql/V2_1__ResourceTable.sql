CREATE TABLE IF NOT EXISTS resources (
  id BIGINT auto_increment,
  type VARCHAR(255),
  dtype VARCHAR(255),
  amount INT,
  updated_at BIGINT,
  resource_per_minute INT,
  kingdom_id BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id)
);

INSERT INTO resources(id, type, dtype, amount, updated_at, resource_per_minute, kingdom_id)
  VALUES (1, 'GOLD', 'Gold', 100, 1549895956111, 5, 1);
INSERT INTO resources(id, type, dtype, amount, updated_at, resource_per_minute, kingdom_id)
  VALUES (2, 'FOOD', 'Food', 50, 1549895956111, 5, 1);

INSERT INTO resources(id, type, dtype, amount, updated_at, resource_per_minute, kingdom_id)
  VALUES (3, 'GOLD', 'Gold', 100, 1549895956111, 5, 2);
INSERT INTO resources(id, type, dtype, amount, updated_at, resource_per_minute, kingdom_id)
  VALUES (4, 'FOOD', 'Food', 50, 1549895956111, 5, 2);

