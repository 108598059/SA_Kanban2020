package domain.model.aggregate.card.event;

import domain.model.DomainEvent;
import domain.model.common.DateProvider;

import java.util.Date;

public class TaskCreated implements DomainEvent {
    private String taskId;
    private String taskName;
    private String cardId;
    private Date occurredOn;

    public TaskCreated(String taskId, String taskName, String cardId) {
        this.cardId = cardId;
        this.taskId = taskId;
        this.taskName = taskName;
        occurredOn = DateProvider.now();
    }

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String detail() {
        return "TaskCreated " + "taskId = " + taskId;
    }
}
