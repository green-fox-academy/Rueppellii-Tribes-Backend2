
CREATE TABLE IF NOT EXISTS resource (
  resource_id BIGINT NOT NULL auto_increment,
  resource_type VARCHAR(255) UNIQUE,
  amount INT,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  kingdom_id BIGINT,
  PRIMARY KEY (resource_id)
);

CREATE TABLE IF NOT EXISTS kingdom_resource (
  kingdom_id BIGINT,
  resource_id BIGINT,
  CONSTRAINT fk_kingdoms_resource FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id),
  CONSTRAINT fk_resource FOREIGN KEY (resource_id) REFERENCES resource(resource_id)
);



