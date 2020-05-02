package phd.sa.csie.ntut.edu.tw.domain.model.card;

import phd.sa.csie.ntut.edu.tw.domain.model.AggregateRoot;
import phd.sa.csie.ntut.edu.tw.domain.model.Entity;
import phd.sa.csie.ntut.edu.tw.domain.model.card.event.CardCreatedEvent;

import java.util.UUID;

public class Card extends AggregateRoot {
  private String name;
  private UUID uuid;
  private UUID columnID;

  public Card(String name) {
    this.name = name;
    this.uuid = UUID.randomUUID();

    this.addDomainEvent(new CardCreatedEvent(this));
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public UUID getUUID() {
    return this.uuid;
  }

  public UUID getColumnID() {
    return columnID;
  }

  public void setColumnID(UUID columnID) {
    this.columnID = columnID;
  }
}