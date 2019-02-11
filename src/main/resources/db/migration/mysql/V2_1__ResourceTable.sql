CREATE TABLE IF NOT EXISTS resources (
  id BIGINT auto_increment,
  type VARCHAR(255),
  dtype VARCHAR(255),
  amount INT,
  updated_at BIGINT,
  resource_per_minute INT,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS kingdom_resources (
  kingdom_id BIGINT,
  resource_id BIGINT,
  CONSTRAINT fk_resources_kingdom FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id),
  CONSTRAINT fk_resources FOREIGN KEY (resource_id) REFERENCES resources(id)
);




