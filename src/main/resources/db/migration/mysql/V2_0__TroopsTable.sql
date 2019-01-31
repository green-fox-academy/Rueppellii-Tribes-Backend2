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

CREATE TABLE troops (
  troop_id BIGINT auto_increment,
  type VARCHAR(255),
  HP BIGINT,
  attack BIGINT,
  defense BIGINT,
  started_at TIMESTAMP,
  finished_at TIMESTAMP,
  finished BOOLEAN,
  PRIMARY KEY (troop_id)
);

CREATE TABLE kingdom_troops (
  kingdom_id BIGINT,
  troop_id BIGINT,
  CONSTRAINT fk_troops FOREIGN KEY (troop_id) REFERENCES troops(troop_id),
  CONSTRAINT fk_kingdoms FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id)
);