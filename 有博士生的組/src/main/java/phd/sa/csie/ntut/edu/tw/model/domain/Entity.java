package phd.sa.csie.ntut.edu.tw.model.domain;

import java.util.UUID;

public abstract class Entity {

    protected UUID id;

    public Entity() {
        this.id = UUID.randomUUID();
    }

    public UUID getID() {
        return this.id;
    }

}
