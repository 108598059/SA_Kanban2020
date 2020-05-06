package phd.sa.csie.ntut.edu.tw.domain.model.card;

import java.util.UUID;

import com.google.common.eventbus.Subscribe;

import phd.sa.csie.ntut.edu.tw.domain.model.AggregateRoot;
import phd.sa.csie.ntut.edu.tw.domain.model.board.event.ColumnEnteredEvent;
import phd.sa.csie.ntut.edu.tw.domain.model.board.event.ColumnLeavedEvent;
import phd.sa.csie.ntut.edu.tw.domain.model.card.event.CardCreatedEvent;

public class Card extends AggregateRoot {
  private String name;
  private UUID uuid;
  private UUID columnID;

  public Card(String name) {
    this.name = name;
    this.uuid = UUID.randomUUID();

    this.addDomainEvent(new CardCreatedEvent(this));
  }

  @Subscribe
  public void entered(ColumnEnteredEvent e) {
    this.columnID = e.getColumnId();
  }

  @Subscribe
  public void leaved(ColumnLeavedEvent e) {
    
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public UUID getId() {
    return this.uuid;
  }

  public UUID getColumnID() {
    return columnID;
  }

  public void setColumnID(UUID columnID) {
    this.columnID = columnID;
  }
}