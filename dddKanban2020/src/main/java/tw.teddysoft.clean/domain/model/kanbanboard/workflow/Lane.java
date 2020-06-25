package tw.teddysoft.clean.domain.model.kanbanboard.workflow;

import tw.teddysoft.clean.domain.model.Entity;

import java.util.*;

abstract public class Lane extends Entity {
    public static final String DEFAULT_NAME = "";

    private final String workflowId;
    private final List<Lane> children;
    private final List<CommittedCard> committedCards;
    private final LaneOrientation orientation;
    private int wipLimit;

    Lane(String name, String workflowId, LaneOrientation orientation) {
        super(name);
        this.workflowId = workflowId;
        this.orientation = orientation;
        wipLimit = 0;
        children = new ArrayList<>();
        committedCards = new ArrayList<>();
    }

    Lane(String name, String workflowId, LaneOrientation orientation, int wipLimit) {
        this(name, workflowId, orientation);
        this.wipLimit = wipLimit;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public LaneOrientation getOrientation(){
        return orientation;
    }

    public List<Lane> getChildren(){
        return Collections.unmodifiableList(children);
    }

    public boolean hasChildren() {
        return 0 == children.size() ? false : true;
    }

    public void addChildren(Lane lane) {
        children.add(lane);
    }

    public int getWipLimit() {
        return wipLimit;
    }

    public void setWipLimit(int wipLimit) {
        if (wipLimit < 0)
            throw new RuntimeException("WIP Limit cannot be negative.");

        this.wipLimit = wipLimit;
    }

    public void commitCard(String cardId) {
//        if (isReachWipLimit())
//            throw new WipLimitExceedException("Exceed WIP limit : " + wipLimit);
        committedCards.add(new CommittedCard(cardId, committedCards.size()+1));
    }

    public boolean uncommitCard(String cardId) {
        for(CommittedCard each : committedCards){
            if (each.getCardId().equalsIgnoreCase(cardId))
                return committedCards.remove(each);
        }
        return false;
    }

    public List<CommittedCard> getCommittedCards() {
        return Collections.unmodifiableList(committedCards);
    }

    public boolean isCardCommitted(String cardId) {
        for(CommittedCard each : committedCards){
            if (each.getCardId().equalsIgnoreCase(cardId) )
                return true;
        }
        return false;
    }


}
