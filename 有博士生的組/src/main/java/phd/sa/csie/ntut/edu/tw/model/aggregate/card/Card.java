package phd.sa.csie.ntut.edu.tw.model.aggregate.card;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.domain.AggregateRoot;
import phd.sa.csie.ntut.edu.tw.model.aggregate.card.event.create.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.model.aggregate.card.event.edit.CardNameEditedEvent;

public class Card extends AggregateRoot {
    private String name;

    public Card(String name, String boardID) {
        super();
        this.addDomainEvent(new CardCreatedEvent(this.id.toString(), this, boardID));
        this.setName(name);
    }

    // For DTO Converter
    public Card(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public void updateName(String name) {
        this.setName(name);
        this.addDomainEvent(new CardNameEditedEvent(this.id.toString(), this.name));
    }

    private void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Card name should not be empty");
        }
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}