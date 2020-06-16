package phd.sa.csie.ntut.edu.tw.model.card;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.domain.AggregateRoot;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.event.create.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.model.card.event.edit.CardNameSetEvent;

public class Card extends AggregateRoot {
    private String name;

    public Card(String name, Board board) {
        super();
        this.addDomainEvent(new CardCreatedEvent(this.id.toString(), this, board.getID().toString()));
        this.setName(name);
    }

    // For DTO Converter
    public Card(UUID id, String name) {
        this.id = id;
        this.name = name;
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
}