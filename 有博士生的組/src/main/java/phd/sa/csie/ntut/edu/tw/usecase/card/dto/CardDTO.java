package phd.sa.csie.ntut.edu.tw.usecase.card.dto;

import phd.sa.csie.ntut.edu.tw.usecase.DTO;

public class CardDTO extends DTO {

    private String name;
    private String columnID;

    public String getName() {
        return name;
    }

    public String getColumnID() {
        return columnID;
    }

    public void setColumnID(String columnID) {
        this.columnID = columnID;
    }

    public void setName(String name) {
        this.name = name;
    }

}