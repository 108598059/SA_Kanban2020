package phd.sa.csie.ntut.edu.tw.usecase.card.dto;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.dto.DTO;

public class CardDTO extends DTO {

    private String name;
    private UUID columnId;

    public String getName() {
        return name;
    }

    public UUID getColumnId() {
        return columnId;
    }

    public void setColumnId(UUID columnId) {
        this.columnId = columnId;
    }

    public void setName(String name) {
        this.name = name;
    }

}