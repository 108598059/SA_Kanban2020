package phd.sa.csie.ntut.edu.tw.usecase.column.dto;

import java.util.List;
import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.dto.DTO;

public class ColumnDTO extends DTO {
    private String title;
    private int wip;
    private List<String> cardIds;

    public String getTitle() {
        return title;
    }

    public List<String> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<String> cardIds) {
        this.cardIds = cardIds;
    }

    public int getWip() {
        return wip;
    }

    public void setWip(int wip) {
        this.wip = wip;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
