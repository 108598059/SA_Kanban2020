package phd.sa.csie.ntut.edu.tw.usecase.card.calculate.cycletime;

import java.util.Date;

public class CycleTime {
    private final String cardID;
    private final String columnID;
    private final Date enteredTime;
    private final Date leftTime;

    public CycleTime(String cardID, String columnID, Date enteredTime, Date leftTime) {
        this.cardID = cardID;
        this.enteredTime = enteredTime;
        this.leftTime = leftTime;
        this.columnID = columnID;
    }

    public String getCardID() {
        return cardID;
    }

    public String getColumnID() {
        return columnID;
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
