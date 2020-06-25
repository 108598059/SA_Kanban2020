package tw.teddysoft.clean.domain.model;

import java.io.Serializable;
import java.util.Date;

public class PersistentDomainEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private int eventVersion;

    private String detail;

    private Date occurredOn;

    PersistentDomainEvent() {super();}

    public PersistentDomainEvent(int eventVersion, String detail, Date occurredOn){
        super();
        this.eventVersion = eventVersion;
        this.detail = detail;
        this.occurredOn = occurredOn;
    }

    public PersistentDomainEvent(DomainEvent event){
        super();
        this.eventVersion = event.eventVersion();
        this.detail = event.detail();
        this.occurredOn = event.occurredOn();
    }

    public int getEventVersion() {
        return eventVersion;
    }

    public String getDetail() {
        return detail;
    }

    public Date getOccurredOn() {
        return occurredOn;
    }

    public Long getId() {
        return id;
    }

    public void setEventVersion(int eventVersion) {
        this.eventVersion = eventVersion;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }

    public void setId(Long is) {
        this.id = id;
    }


    @Override
    public String toString() {
        return String.format(
                "PersistentDomainEvent[id=%d, detail='%s', occurredOn='%s']",
                id, detail, occurredOn);
    }

}
