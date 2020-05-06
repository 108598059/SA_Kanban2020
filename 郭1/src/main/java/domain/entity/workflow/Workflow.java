package domain.entity.workflow;

import com.sun.org.apache.xpath.internal.operations.Bool;
import domain.entity.Aggregate;
import domain.entity.workflow.event.WorkflowCreated;

import java.util.*;


public class Workflow extends Aggregate {
    private String name;
    private String id;
    private HashMap<String,Stage> stages;

    public Workflow(){
        this.stages = new HashMap<String, Stage>();
        this.id = UUID.randomUUID().toString();
    }

    public Workflow(String boardId){
        this.stages = new HashMap<String, Stage>();
        this.id = UUID.randomUUID().toString();
        addEvent(new WorkflowCreated(boardId, this.id));
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void addStage(Stage stage){
        this.stages.put(stage.getId(),stage);
    }

    public void addCard(String stageId, String swimlaneId, String cardId){
        for (Stage stage: stages.values()){
            if(stage.getId().equals(stageId)){
                for (Swimlane swimlane: stage.getSwimlaneMap().values()){
                    if (swimlane.getId().equals(swimlaneId)) {
                        swimlane.add(cardId);
                    }
                }
            }
        }
    }


    public String createStage(String stageName) {
        Stage stage = new Stage();
        stage.setName(stageName);
        this.addStage(stage);

        return stage.getId();
    }

    public String createSwimlane(String stageId, String swimlaneName){
        Swimlane swimlane = new Swimlane();
        swimlane.setName(swimlaneName);

        Stage stage = stages.get(stageId);
        stage.addSwimlane(swimlane);

        return swimlane.getId();
    }

    public String getName() {
        return this.name;
    }
    public HashMap<String,Stage> getStageMap(){
        return this.stages;
    }


    public String getCard(String cardId) {
        for (Stage stage: stages.values()){
            for (Swimlane swimlane: stage.getSwimlaneMap().values()){
                if(swimlane.isCardExist(cardId))
                    return cardId;
            }
        }
        return "";
    }
}
