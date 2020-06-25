package domain.entity.aggregate.workflow;

import domain.entity.aggregate.Aggregate;
import domain.entity.aggregate.workflow.event.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class Workflow extends Aggregate {
    private String name;
    private String id;
    private Map<String,Stage> stages;

    public Workflow(){
        this.stages = new HashMap<String, Stage>();
        this.id = UUID.randomUUID().toString();
    }

    public Workflow(String boardId){
        this.stages = new HashMap<String, Stage>();
        this.id = UUID.randomUUID().toString();
        addEvent(new WorkflowCreated(boardId, this.id));
    }

    public Workflow(String workflowId, String name, Map<String,Stage> stages){
        this.name = name;
        this.id = workflowId;
        this.stages = stages;
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
                for (Swimlane swimlane: stage.getSwimlanes().values()){
                    if (swimlane.getId().equals(swimlaneId)) {
                        swimlane.addCard(cardId);
                    }
                }
            }
        }
        CardCommitted cardCommitted = new CardCommitted(stageId,stageId,swimlaneId,swimlaneId,cardId);
        addEvent(cardCommitted);
    }


    public String createStage(String stageName) {
        Stage stage = new Stage();
        stage.setName(stageName);
        this.addStage(stage);

        StageCreated stageCreated = new StageCreated(this.id, stage.getId(),stage.getName()) ;
        addEvent(stageCreated);

        return stage.getId();
    }

    public String createSwimlane(String stageId, String swimlaneName){
        Swimlane swimlane = new Swimlane();
        swimlane.setName(swimlaneName);

        Stage stage = stages.get(stageId);
        stage.addSwimlane(swimlane);

        SwimlaneCreated swimlaneCreated = new SwimlaneCreated( this.id,stage.getId(),swimlane.getId(),swimlane.getName() ) ;
        addEvent(swimlaneCreated);

        return swimlane.getId();
    }

    public void moveCard(String fromStageId, String toStageId, String fromSwimLaneId, String toSwimLaneId, String cardId){
        stages.get(fromStageId).getSwimlaneById(fromSwimLaneId).getCards().remove(cardId);
        stages.get(toStageId).getSwimlaneById(toSwimLaneId).getCards().add(cardId);
        CardMoved cardMoved = new CardMoved(fromStageId, toStageId, fromSwimLaneId, toSwimLaneId, cardId);
        addEvent(cardMoved);

    }

    public String getName() {
        return this.name;
    }

    public Stage getStageById(String id){
        return stages.get(id);
    }

    public Map<String,Stage> getStages(){
        return this.stages;
    }


}
