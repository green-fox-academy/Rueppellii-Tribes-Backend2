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
  army_id BIGINT,
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

CREATE TABLE resource (
  resource_id BIGINT auto_increment,
  resource_type VARCHAR(255) UNIQUE,
  amount INT,
  updated_at TIMESTAMP,
  kingdom_id BIGINT,
  PRIMARY KEY (resource_id)
);
ALTER TABLE kingdoms ADD resource_id BIGINT;

ALTER TABLE kingdoms ADD CONSTRAINT fk_resource_resource_id
  FOREIGN KEY (resource_id) REFERENCES resource(resource_id);

  INSERT INTO resource VALUES (3, 'gold', 20, null , 2);

