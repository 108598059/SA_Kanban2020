package domain.entity.aggregate.user;

import domain.entity.aggregate.Aggregate;
import domain.entity.aggregate.user.event.UserCreated;

import java.util.UUID;

public class User extends Aggregate {
    private String id;
    private String name;

    public User(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        addEvent(new UserCreated(this.id, this.name));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
