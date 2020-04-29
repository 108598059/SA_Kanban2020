package domain.entity.workflow;

import java.util.HashMap;
import java.util.UUID;

public class Stage {
    private String name;
    private String id;
    private HashMap<String, Swimlane> swimlanes;

    public Stage(){
        this.swimlanes = new HashMap<String, Swimlane>();
        this.id = UUID.randomUUID().toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id){this.id = id;}

    public void addSwimlane(Swimlane swimlane){
        swimlanes.put(swimlane.getId(),swimlane);
    }

    public String getName() {
        return this.name;
    }

    public HashMap<String, Swimlane> getSwimlaneMap(){
        return this.swimlanes;
    }
}
