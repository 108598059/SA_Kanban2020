package tw.teddysoft.clean.domain.model.kanbanboard.workflow;

import tw.teddysoft.clean.domain.model.Entity;

public class CommittedCard extends Entity {

    public static final String DEFAULT_NAME = "";
    private int ordering;

    private String boardId;
    private String workflowId;
    private String cardId;

    public CommittedCard(String cardId, int ordering) {
        super(DEFAULT_NAME);
        this.cardId = cardId;
//        this.boardId = boardId;
//        this.workflowId = workflowId;
        this.ordering = ordering;
    }

    //TODO implement equals and hashCode

    public void setOrdering(int arg) {
        ordering = arg;
    }

    public void moveBackward(){
        ordering++;
    }

    public void moveForward(){
        ordering--;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public int getOrdering() {
        return ordering;
    }

    public String getCardId() {
        return cardId;
    }
}
