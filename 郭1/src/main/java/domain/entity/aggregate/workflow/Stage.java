package domain.entity.aggregate.workflow;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Stage {
    private String name;
    private String id;
    private Map<String, Swimlane> swimlanes;

    public Stage(){
        this.swimlanes = new HashMap<String, Swimlane>();
        this.id = UUID.randomUUID().toString();
    }

    public Stage( String id , String name, Map<String,Swimlane> swimlanes ) {
        this.id = id ;
        this.name = name ;
        this.swimlanes = swimlanes ;
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

    public Swimlane getSwimlaneById(String id){

        return swimlanes.get(id);
    }

    public Map<String, Swimlane> getSwimlanes() {
        return swimlanes;
    }


}
