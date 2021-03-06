CREATE TABLE IF NOT EXISTS troops (
  id BIGINT auto_increment,
  level INT,
  HP INTEGER,
  attack INTEGER,
  defense INTEGER,
  kingdom_id BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id)
);

INSERT INTO troops(id, level, HP, attack, defense, kingdom_id)
  VALUES (1, 1, 10, 1, 1, 1),
         (2, 1, 10, 1, 1, 2);