package phd.sa.csie.ntut.edu.tw.usecase.card.dto;

import phd.sa.csie.ntut.edu.tw.usecase.dto.DTO;

public class CardDTO extends DTO {

    private String name;
    private String columnId;

    public String getName() {
        return name;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public void setName(String name) {
        this.name = name;
    }

}