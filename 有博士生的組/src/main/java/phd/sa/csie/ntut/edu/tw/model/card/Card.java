package phd.sa.csie.ntut.edu.tw.model.card;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.domain.AggregateRoot;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.event.edit.CardBelongsColumnSetEvent;
import phd.sa.csie.ntut.edu.tw.model.card.event.create.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.model.card.event.edit.CardNameSetEvent;
import phd.sa.csie.ntut.edu.tw.model.card.event.calculate.LeadTimeCalculatedEvent;

public class Card extends AggregateRoot {
    private String name;
    private long leadTime;

    public Card(String name, Board board) {
        super();
        this.addDomainEvent(new CardCreatedEvent(this.id.toString(), this, board.getID().toString()));
        this.setName(name);
        this.leadTime = -1;
    }

    // For DTO Converter
    public Card(UUID id, String name, long leadTime) {
        this.id = id;
        this.name = name;
        this.leadTime = leadTime;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Card name should not be empty");
        }
        this.name = name;
        this.addDomainEvent(new CardNameSetEvent(this.id.toString(), this.name));
    }

    public String getName() {
        return this.name;
    }

    public UUID getBelongsColumnID() {
        return UUID.randomUUID();
    }

    public void setBelongsColumnID(UUID belongsColumnID) {
        this.addDomainEvent(new CardBelongsColumnSetEvent(this.id.toString(), belongsColumnID.toString()));
    }

    public long getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(long leadTime) {
        this.leadTime = leadTime;
        this.addDomainEvent(new LeadTimeCalculatedEvent(this.id.toString(), this.leadTime));
    }
}