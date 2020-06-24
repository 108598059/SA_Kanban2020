package phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.domain.dto;

import phd.sa.csie.ntut.edu.tw.usecase.DTO;

import java.util.Date;

public class DomainEventDTO extends DTO {
    private int eventVersion;
    private Date occurredTime;
    private String detail;
    private String sourceID;
    private String sourceName;

    public int getEventVersion() {
        return eventVersion;
    }

    public void setEventVersion(int eventVersion) {
        this.eventVersion = eventVersion;
    }

    public Date getOccurredTime() {
        return occurredTime;
    }

    public void setOccurredTime(Date occurredTime) {
        this.occurredTime = occurredTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
