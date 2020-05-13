package phd.sa.csie.ntut.edu.tw.domain.model;

import java.util.UUID;

public abstract class Entity {

    protected UUID id;

    public Entity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return this.id;
    }

}
