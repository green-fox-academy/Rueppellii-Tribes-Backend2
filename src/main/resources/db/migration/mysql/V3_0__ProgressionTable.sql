CREATE TABLE IF NOT EXISTS progression (
  id BIGINT auto_increment,
  type VARCHAR(255),
  game_object_id BIGINT,
  time_to_progress BIGINT,
  kingdom_id BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (kingdom_id) REFERENCES kingdoms(id)
);
