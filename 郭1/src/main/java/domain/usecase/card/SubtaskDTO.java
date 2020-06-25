package domain.usecase.card;

import java.util.UUID;

public class SubtaskDTO {
    private String id;
    private String name;

    public SubtaskDTO(){
        this.id = UUID.randomUUID().toString();
    }

    public SubtaskDTO( String id, String name ) {
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
