package domain.entity.aggregate.user.event;

import domain.entity.DomainEvent;

public class UserCreated implements DomainEvent {
    private String id;
    private String name;
    public UserCreated(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
