package ddd.kanban.usecase.card.create;

import ddd.kanban.domain.model.card.CardType;

import java.util.Date;
import java.util.List;

public class CreateCardInput {

    private String cardTitle;
    private String boardId;
    private String workflowId;
    private String laneId;

    public CreateCardInput(String cardTitle, String boardId, String workflowId, String laneId){
        this.cardTitle = cardTitle;
        this.boardId = boardId;
        this.workflowId = workflowId;
        this.laneId = laneId;
    }

    public String getCardTitle() {
        return cardTitle;
    }


    public String getBoardId() {
        return boardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getLaneId(){
        return laneId;
    }

}
