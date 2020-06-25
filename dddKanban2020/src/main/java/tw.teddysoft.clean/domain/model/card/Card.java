package tw.teddysoft.clean.domain.model.card;

import tw.teddysoft.clean.domain.model.AggregateRoot;
import tw.teddysoft.clean.domain.model.card.event.CardCreated;


public class Card extends AggregateRoot {

    private String boardId;
    private String workflowId;

    public static String NOT_ASSIGNED = "";

    public Card(String name) {
        this(name, NOT_ASSIGNED, NOT_ASSIGNED, NOT_ASSIGNED);
    }


    public Card(String name, String boardId, String workflowId, String laneId) {
        super(name);
        this.boardId = boardId;
        this.workflowId = workflowId;

//        DomainEventPublisher
//                .instance()
//                .publish(new CardCreated(
//                        this.getId(),
//                        this.getName(),
//                        this.getBoardId(),
//                        this.getWorkflowId()));

        addDomainEvent(new CardCreated(this, laneId));
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId){
        this.workflowId = workflowId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
