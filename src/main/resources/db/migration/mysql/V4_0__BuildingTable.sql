CREATE TABLE IF NOT EXISTS users (
  user_id BIGINT auto_increment,
  username VARCHAR(255) UNIQUE,
  password VARCHAR(255),
  role VARCHAR(255),
  PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS kingdoms (
  id BIGINT auto_increment,
  name VARCHAR(255) UNIQUE,
  application_user_user_id BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS army (
  army_id BIGINT auto_increment,
  troop_type VARCHAR(255) UNIQUE,
  quantity INT,
  attack_power INT,
  defense_power INT,
  loot_capacity INT,
  PRIMARY KEY (army_id)
);

CREATE TABLE IF NOT EXISTS buildings (
  building_id BIGINT auto_increment,
  dtype VARCHAR(255),
  type VARCHAR(255),
  level INT,
  hp INT,
  started_at TIMESTAMP,
  finished_at TIMESTAMP,
  PRIMARY KEY (building_id)
);

CREATE TABLE IF NOT EXISTS kingdom_building (
  buildings_kingdom_id BIGINT,
  building_id BIGINT,
  CONSTRAINT fk_buildings FOREIGN KEY (building_id) REFERENCES buildings(building_id),
  CONSTRAINT fk_kingdoms FOREIGN KEY (buildings_kingdom_id) REFERENCES kingdoms(id)
);