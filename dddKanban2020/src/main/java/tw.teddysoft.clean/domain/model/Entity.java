package tw.teddysoft.clean.domain.model;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String name;
    protected final String id;

    public Entity(String name) {
        this.name = name;
        id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getId() {
        return id;
    }

}
