CREATE TABLE IF NOT EXISTS troops (
  id BIGINT auto_increment,
  level INT,
  HP INTEGER,
  attack INTEGER,
  defense INTEGER,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS kingdom_troops (
  kingdom_id BIGINT,
  troop_id BIGINT,
  CONSTRAINT fk_troops_kingdom FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id),
  CONSTRAINT fk_troops FOREIGN KEY (troop_id) REFERENCES troops(id)
);

