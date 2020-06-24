package phd.sa.csie.ntut.edu.tw.usecase.cycletime;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

import java.util.Date;

public class CalculateCycleTimeUseCaseInput implements UseCaseInput {
    private String cardID;
    private String startColumnID;
    private String endColumnID;
    private String boardID;
    private Date startTime;
    private Date endTime;

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getStartColumnID() {
        return startColumnID;
    }

    public void setStartColumnID(String startColumnID) {
        this.startColumnID = startColumnID;
    }

    public String getEndColumnID() {
        return endColumnID;
    }

    public void setEndColumnID(String endColumnID) {
        this.endColumnID = endColumnID;
    }
}
