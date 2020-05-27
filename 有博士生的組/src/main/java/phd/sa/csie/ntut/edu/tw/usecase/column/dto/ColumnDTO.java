package phd.sa.csie.ntut.edu.tw.usecase.column.dto;

import java.util.List;

import phd.sa.csie.ntut.edu.tw.usecase.DTO;

public class ColumnDTO extends DTO {
    private String title;
    private int wip;
    private List<String> cardIDs;

    public String getTitle() {
        return title;
    }

    public List<String> getCardIDs() {
        return cardIDs;
    }

    public void setCardIDs(List<String> cardIDs) {
        this.cardIDs = cardIDs;
    }

    public int getWIP() {
        return wip;
    }

    public void setWIP(int wip) {
        this.wip = wip;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
