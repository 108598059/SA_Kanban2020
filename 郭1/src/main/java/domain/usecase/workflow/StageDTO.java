package domain.usecase.workflow;

import domain.entity.aggregate.workflow.Swimlane;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StageDTO {
    private String name;
    private String id;
    private Map<String, SwimlaneDTO> swimlanes;

    public StageDTO(){
        this.swimlanes = new HashMap<String, SwimlaneDTO>();
        this.id = UUID.randomUUID().toString();
    }

    public StageDTO(String id , String name, Map<String, SwimlaneDTO> swimlanes ){
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

    public void addSwimlane(SwimlaneDTO swimlane){
        swimlanes.put(swimlane.getId(),swimlane);
    }

    public String getName() {
        return this.name;
    }

    public SwimlaneDTO getSwimlaneById(String id){

        return swimlanes.get(id);
    }

    public Map<String, SwimlaneDTO> getSwimlanes() {
        return swimlanes;
    }

}
