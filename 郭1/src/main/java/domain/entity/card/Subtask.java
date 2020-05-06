package domain.entity.card;

import java.util.UUID;

public class Subtask {
    private String id;
    private String name;

    public Subtask(){
        this.id = UUID.randomUUID().toString();
    }

    public void setName(String taskName) {
        this.name = taskName;
    }

    public void setId(String id) {this.id = id;}

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }
}
