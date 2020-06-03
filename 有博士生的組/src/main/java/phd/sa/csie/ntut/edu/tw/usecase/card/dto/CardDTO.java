package phd.sa.csie.ntut.edu.tw.usecase.card.dto;

import phd.sa.csie.ntut.edu.tw.usecase.DTO;

public class CardDTO extends DTO {

    private String name;
    private long leadTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(long leadTime) {
        this.leadTime = leadTime;
    }
}