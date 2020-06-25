package tw.teddysoft.clean.domain.model;

import java.io.Serializable;

public abstract class FlowEvent extends AbstractDomainEvent implements Serializable {

    private final String boardId;
    private final String workflowId;
    private final String leanId;
    private final String cardId;
    private final String summary;

    private static final long serialVersionUID = 1L;

    public FlowEvent(String boardId, String workflowId, String leadId, String cardId, String summary){
        super(leadId, summary);
        this.boardId = boardId;
        this.workflowId = workflowId;
        this.leanId = leadId;
        this.cardId = cardId;
        this.summary = summary;
    }

    @Override
    public String detail() {
        String formatDate = String.format("occurredOn='%1$tY-%1$tm-%1$td %1$tH:%1$tM", occurredOn());
        String format = String.format(
                "%s['%s', board id='%s',  workflow id='%s', lean id='%s', card id='%s', summary ='%s'] ",
                this.getClass().getSimpleName(),
                formatDate,
                boardId,
                workflowId, leanId,
                cardId, summary);
//        return format + formatDate;
        return format;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getLeanId() {
        return leanId;
    }

    public String getCardId() {
        return cardId;
    }

    public String getSummary() {
        return summary;
    }

    public String getBoardId() {
        return boardId;
    }
}
