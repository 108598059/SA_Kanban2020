package domain.aggregate.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

abstract public class Lane {
    private String workflowId;
    private String laneId;
    private String laneName;
    private List<Lane> laneList;
    private List<String> cardIdList;
    private String laneDirection;

    public Lane(String laneName, String workflowId, LaneDirection laneDirection) {
        cardIdList = new ArrayList<String>();
        laneList = new ArrayList<Lane>();
        this.workflowId = workflowId;
        this.laneName = laneName;
        laneId = UUID.randomUUID().toString();
        this.laneDirection = laneDirection.toString();
    }

    public void setLaneId(String laneId){
        this.laneId = laneId;
    }

    public String getLaneId(){
        return laneId;
    }

    public void setLaneName(String laneName){
        this.laneName = laneName;
    }

    public String getLaneName(){
        return laneName;
    }

    public void setWorkflowId(String workflowId){
        this.workflowId = workflowId;
    }

    public String getWorkflowId(){
        return workflowId;
    }

    public void addCardId(String cardId){
        cardIdList.add(cardId);
    }

    public List<String> getCardIdList(){
        return cardIdList;
    }

    public void setCardIdList(List<String> cardIdList){
        this.cardIdList = cardIdList;
    }

    public List<Lane> getLaneList(){
        return laneList;
    }

    public void setLaneList(List<Lane> laneList){
        this.laneList = laneList;
    }

    public String getLaneDirection() {
        return laneDirection;
    }

    public void setLaneDirection(String laneDirection) {
        this.laneDirection = laneDirection;
    }
}
