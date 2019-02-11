CREATE TABLE IF NOT EXISTS resources (
  id BIGINT auto_increment,
  type VARCHAR(255),
  dtype VARCHAR(255),
  amount INT,
  updated_at BIGINT,
  resource_per_minute INT,
  kingdom_id BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id)
);




