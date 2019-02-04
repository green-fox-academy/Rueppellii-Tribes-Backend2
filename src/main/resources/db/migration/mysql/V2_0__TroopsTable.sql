CREATE TABLE IF NOT EXISTS troops (
  troop_id BIGINT auto_increment,
  dtype VARCHAR(255),
  type VARCHAR(255),
  HP INTEGER,
  attack INTEGER,
  defense INTEGER,
  started_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  finished_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  finished BOOLEAN,
  PRIMARY KEY (troop_id)
);

CREATE TABLE IF NOT EXISTS kingdom_troops (
  kingdom_id BIGINT,
  troop_id BIGINT,
  CONSTRAINT fk_troops FOREIGN KEY (troop_id) REFERENCES troops(troop_id),
  CONSTRAINT fk_kingdoms FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id)
);

