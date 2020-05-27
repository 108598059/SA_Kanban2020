package domain.entity.workflow;

import domain.entity.Aggregate;
import domain.entity.workflow.event.CardCommitted;
import domain.entity.workflow.event.CardMoved;
import domain.entity.workflow.event.WorkflowCreated;
import domain.usecase.flowevent.FlowEventRepository;

import java.util.*;


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


    public void addCard(FlowEventRepository flowEventRepository, String stageId, String swimlaneId, String cardId){
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
        flowEventRepository.save(cardCommitted);
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

    public void moveCard(FlowEventRepository flowEventRepository, String fromStageId, String toStageId, String fromSwimLaneId, String toSwimLaneId, String cardId){

        stages.get(fromStageId).getSwimlaneById(fromSwimLaneId).getCards().remove(cardId);
        stages.get(toStageId).getSwimlaneById(toSwimLaneId).getCards().add(cardId);
        CardMoved cardMoved = new CardMoved(fromStageId, toStageId, fromSwimLaneId, toSwimLaneId, cardId);
        flowEventRepository.save(cardMoved);
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
