package phd.sa.csie.ntut.edu.tw.model.card;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.AggregateRoot;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.event.CardCreatedEvent;

public class Card extends AggregateRoot {
  private String name;
  private UUID columnId;

  public Card(String name, Board board) {
    super();
    this.name = name;
    this.setColumnId(board.get(0).getId());
    this.addDomainEvent(new CardCreatedEvent(this, board.getId()));
  }

  public Card(UUID id, String name, UUID columnId) {
    this.id = id;
    this.name = name;
    this.columnId = columnId;
    // TODO Should the CardCreated event be issued when the card object is
    // reconstituted?
    //
    // -> James: It depends on how we store the entity status and events.
    // this.addDomainEvent(new CardCreatedEvent(this));
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public UUID getColumnId() {
    return this.columnId;
  }

  public void setColumnId(UUID columnId) {
    this.columnId = columnId;
    // TODO issue an event.
  }
}