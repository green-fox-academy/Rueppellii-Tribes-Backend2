CREATE TABLE IF NOT EXISTS buildings (
  id BIGINT auto_increment,
  type VARCHAR(255),
  level INT,
  hp INT,
  kingdom_id BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id)
);
