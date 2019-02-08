CREATE TABLE IF NOT EXISTS buildings (
  id BIGINT auto_increment,
  type VARCHAR(255),
  level INT,
  hp INT,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS kingdom_buildings (
  kingdom_id BIGINT,
  building_id BIGINT,
  CONSTRAINT fk_buildings_kingdom FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id),
  CONSTRAINT fk_buildings FOREIGN KEY (building_id) REFERENCES buildings(id)

);