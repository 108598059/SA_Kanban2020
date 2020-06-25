package phd.sa.csie.ntut.edu.tw.model.service.cycletimecalculator;

import java.util.Date;

public class CycleTime {
    private final String cardID;
    private final String startColumnID;
    private final String endColumnID;
    private final Date enteredTime;
    private final Date leftTime;

    public CycleTime(String cardID, String startColumnID, String endColumnID, Date enteredTime, Date leftTime) {
        this.cardID = cardID;
        this.enteredTime = enteredTime;
        this.leftTime = leftTime;
        this.startColumnID = startColumnID;
        this.endColumnID = endColumnID;
    }

    public String getCardID() {
        return cardID;
    }

    public String getStartColumnID() {
        return startColumnID;
    }

    public String getEndColumnID() {
        return endColumnID;
    }

    public Date getEnteredTime() {
        return enteredTime;
    }

    public Date getLeftTime() {
        return leftTime;
    }

    public long getTime() {
        return this.leftTime.getTime() - this.enteredTime.getTime();
    }
}
