package rueppellii.backend2.tribes.building;

import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
public class TownHall extends Building {

    @Override
    public void setType(BuildingType.Type type) {
        super.setType(BuildingType.Type.TOWNHALL);
    }

    @Override
    public void setLevel(Integer level) {
        super.setLevel(level);
    }

    @Override
    public void setHP(Integer HP) {
        super.setHP(HP);
    }

    @Override
    public void setStarted_at(Timestamp started_at) {
        super.setStarted_at(started_at);
    }

    @Override
    public void setFinished_at(Timestamp finished_at) {
        super.setFinished_at(finished_at);
    }
}
