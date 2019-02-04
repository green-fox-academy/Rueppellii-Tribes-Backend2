
CREATE TABLE IF NOT EXISTS buildings (
  building_id BIGINT auto_increment,
  dtype VARCHAR(255),
  type VARCHAR(255),
  level INT,
  hp INT,
  started_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  finished_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (building_id)
);

CREATE TABLE IF NOT EXISTS kingdom_building (
  buildings_kingdom_id BIGINT,
  building_id BIGINT,
  CONSTRAINT fk_buildings FOREIGN KEY (building_id) REFERENCES buildings(building_id),
  CONSTRAINT fk_kingdoms_buildings FOREIGN KEY (buildings_kingdom_id) REFERENCES kingdoms(id)
);