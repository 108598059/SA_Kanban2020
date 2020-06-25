package tw.teddysoft.clean.domain.model.kanbanboard.workflow;

import tw.teddysoft.clean.domain.model.AggregateRoot;
import tw.teddysoft.clean.domain.model.card.event.CardCommitted;
import tw.teddysoft.clean.domain.model.card.event.CardUncommitted;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.event.StageCreated;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.event.SwimlaneCreated;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.event.WorkflowCreated;

import java.util.*;

public class Workflow extends AggregateRoot {

    private final String boardId;
    private final List<Lane> stages;

    public Workflow(String name, String boardId){
        super(name);
        this.boardId = boardId;
        stages = new ArrayList<>();
        addDomainEvent(new WorkflowCreated(this));
    }

    public String getBoardId(){
        return boardId;
    }

    public List<Lane> getStages() {
        return Collections.unmodifiableList(stages);
    }

    public Lane addStage(String name) {
        Lane stage = LaneBuilder.getInstance()
                .name(name)
                .workflowId(getId())
                .stage()
                .build();
        stages.add(stage);


        addDomainEvent(new StageCreated(this));
        //TODO return a readonly version of stage
        return stage;
    }

    public Lane addStage(String parentId, String name){
        Lane parent = findLaneById(parentId);
        if (null == parent)
            throw new RuntimeException("Cannot find the parent lane '" +
                    parentId + "' to add the stage '" +
                    this.getName() + "' under it.");

        Lane stage = LaneBuilder.getInstance()
                .name(name)
                .workflowId(getId())
                .stage()
                .build();

        parent.addChildren(stage);
        addDomainEvent(new StageCreated(this));

        //TODO return a readonly version of stage
        return stage;
    }

    public Lane addSwimlane(String parentId, String name){
        Lane parent = findLaneById(parentId);
        if (null == parent)
            throw new RuntimeException("Cannot find the parent lane '" + parentId + "' to add the swimlane '" + this.getName() + "' under it.");

        Lane swimlane = LaneBuilder.getInstance()
                .name(name)
                .workflowId(getId())
                .swimlane()
                .build();

        parent.addChildren(swimlane);
        addDomainEvent(new SwimlaneCreated(this));

        //TODO return a readonly version of swimlane
        return swimlane;
    }

    public void commitCard(String cardId, String laneId) {
        Lane toLane = findLaneById(laneId);
        if (null == toLane)
            throw new RuntimeException("Cannot commit a card to a non-existing land '" + laneId + "'");

        toLane.commitCard(cardId);

//        DomainEventPublisher
//                .instance()
//                .publish(new CardCommitted(
//                        this.getBoardId(),
//                        this.getId(),
//                        toLane.getId(),
//                        cardId,
//                        "lane name -> '" + toLane.getName() + "'"));

        addDomainEvent(new CardCommitted(this.getBoardId(),
                        this.getId(),
                        toLane.getId(),
                        cardId,
                        "lane name -> '" + toLane.getName() + "'"));
    }

    public void uncommitCard(String cardId, String laneId) {
        Lane lane = findLaneById(laneId);
        if (null == lane)
            throw new RuntimeException("Cannot commit a card to be uncommitted from a non-existing land '" + laneId + "'");

        if (!lane.isCardCommitted(cardId))
            throw new RuntimeException("Cannot uncommit card '" + cardId + "' + which does not exist in land '" + laneId + "'");

        lane.uncommitCard(cardId);
//        DomainEventPublisher
//                .instance()
//                .publish(new CardUncommitted(
//                        this.getBoardId(),
//                        this.getId(),
//                        lane.getId(),
//                        cardId,
//                        lane.getName()));

        addDomainEvent(new CardUncommitted(
                this.getBoardId(),
                this.getId(),
                lane.getId(),
                cardId,
                lane.getName()));
    }

    public void moveCard(String cardId, String fromLaneId, String toLandId) {

        Lane fromLane = findLaneById(fromLaneId);
        if (null == fromLane)
            throw new RuntimeException("Cannot uncommit a card from a non-existing land '" + fromLaneId + "'");

        Lane toLane = findLaneById(toLandId);
        if (null == toLane)
            throw new RuntimeException("Cannot commit a card to a non-existing land '" + toLane + "'");

        if (!fromLane.isCardCommitted(cardId))
            throw new RuntimeException("Cannot move card '" + cardId + "' which does not belong to lane '" + fromLaneId + "'");

        fromLane.uncommitCard(cardId);
        toLane.commitCard(cardId);

        addDomainEvent(new CardUncommitted(
                this.getBoardId(),
                this.getId(),
                fromLane.getId(),
                cardId,
                fromLane.getName()));

        addDomainEvent(new CardCommitted(
                this.getBoardId(),
                this.getId(),
                toLane.getId(),
                cardId,
                toLane.getName()));
    }


    public void dumpLane() {
        for(Lane each : stages){
            dumpLane(each, 0);
        }
    }

    public int getTotalLaneSize() {
        int result = 0;
        for(Lane each : stages){
            result += getTotalLaneSize(each);
        }
        return result;
    }


    public Lane findLaneByCardId(String cardId){
        Lane result = null;
        for(Lane each : stages){
            if ( (result = findLaneByCardId(each, cardId)) != null)
                return result;
        }
        return result;
    }

    private Lane findLaneByCardId(Lane each, String cardId) {
        Lane result = null;

        if (each.isCardCommitted(cardId)){
            result = each;
        }
        else if (each.hasChildren()) {
            for (Lane next : each.getChildren()) {
                result = findLaneById(next, cardId);
            }
        }
        return result;
    }

    public Lane findLaneById(String laneId) {
        Lane result = null;
        for(Lane each : stages){
            if ( (result = findLaneById(each, laneId)) != null)
                return result;
        }
        return result;
    }

    private Lane findLaneById(Lane each, String parentId) {

//        System.out.println(each.getName() + " id = " + each.getId());
//        System.out.println(each.getName() + " has sublane = " + each.hasSubLane());

        Lane result = null;

        if (parentId == each.getId()){
            result = each;
        }
        else if (each.hasChildren()) {
            for (Lane next : each.getChildren()) {
//                System.out.println("==> " + next.getName());
                result = findLaneById(next, parentId);
            }
        }
        return result;
    }

    private int getTotalLaneSize(Lane each) {
        int result = 1;
        if (each.hasChildren()) {
            for (Lane next : each.getChildren()) {
                result += getTotalLaneSize(next);
            }
        }
        return result;
    }


    private void dumpLane(Lane each, int tabs) {
//        System.out.println("Lane ==>" + each.getName() );
//        System.out.println("each.hasSubLane() ==>" + each.hasSubLane());
        System.out.printf(getTabs(tabs) + "%-20s %n", each.getName());
        if (each.hasChildren()) {
            for (Lane next : each.getChildren()) {
                dumpLane(next, tabs+1);
            }
        }
    }

    private String getTabs(int tabs){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < tabs; i++){
            sb.append("\t");
        }
        return sb.toString();
    }
}
