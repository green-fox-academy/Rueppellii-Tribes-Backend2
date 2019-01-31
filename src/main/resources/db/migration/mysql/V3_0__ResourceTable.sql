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
  resource_id BIGINT NOT NULL auto_increment,
  resource_type VARCHAR(255) UNIQUE,
  amount INT,
  updated_at TIMESTAMP,
  kingdom_id BIGINT,
  PRIMARY KEY (resource_id)
);

CREATE TABLE kingdomresource (
  kingdom_id BIGINT,
  resource_id BIGINT,
  PRIMARY KEY (resource_id),
  KEY resource_id (resource_id),
  CONSTRAINT fk_kingdoms FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id),
  CONSTRAINT fk_resource FOREIGN KEY (resource_id) REFERENCES resource(resource_id)
);

INSERT INTO users VALUES (3, 'TestUsasder1', 'passas1', 'useasdr');
INSERT INTO users VALUES (4, 'TestUasdser2', 'passfds2', 'usewqer');
INSERT INTO kingdoms VALUES (3, 'Testking', 3, null);
INSERT INTO resource VALUES (1, 'gold', 20, null, 2);

INSERT INTO kingdomresource (resource_id, kingdom_id) SELECT resource_id, kingdom_id FROM resource;





