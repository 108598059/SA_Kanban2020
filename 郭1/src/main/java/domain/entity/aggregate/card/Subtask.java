package domain.entity.aggregate.card;

import java.util.UUID;

public class Subtask {
    private String id;
    private String name;

    public Subtask(){
        this.id = UUID.randomUUID().toString();
    }

    public Subtask( String id , String name ) {
        this.id = id ;
        this.name = name ;
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
