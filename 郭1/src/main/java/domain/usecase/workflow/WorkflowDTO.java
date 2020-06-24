package domain.usecase.workflow;

import domain.entity.aggregate.team.Team;
import domain.entity.aggregate.workflow.Stage;
import domain.entity.aggregate.workflow.Swimlane;
import domain.entity.aggregate.workflow.Workflow;
import domain.entity.aggregate.workflow.event.*;

import java.util.*;

public class WorkflowDTO {
    private String name;
    private String id;
    private Map<String, Stage> stages;

    public WorkflowDTO(Workflow workflow){
        this.name = workflow.getName();
        this.id = workflow.getId();
        this.stages = workflow.getStages();
    }


    public WorkflowDTO(String boardId){
        this.stages = new HashMap<String, Stage>();
        this.id = boardId;

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

    public void moveCard(String fromStageId, String toStageId, String fromSwimLaneId, String toSwimLaneId, String cardId){
        stages.get(fromStageId).getSwimlaneById(fromSwimLaneId).getCards().remove(cardId);
        stages.get(toStageId).getSwimlaneById(toSwimLaneId).getCards().add(cardId);

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
