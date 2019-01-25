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

CREATE TABLE army (
  army_id BIGINT auto_increment,
  troop_type VARCHAR(255) UNIQUE,
  quantity INT,
  attack_power INT,
  defense_power INT,
  loot_capacity INT,
  PRIMARY KEY (army_id)
);

ALTER TABLE kingdoms ADD army_id BIGINT;

ALTER TABLE kingdoms ADD CONSTRAINT fk_army_army_id
  FOREIGN KEY (army_id) REFERENCES army(army_id);