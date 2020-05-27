package phd.sa.csie.ntut.edu.tw.model.card;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.AggregateRoot;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.event.CardCreatedEvent;

public class Card extends AggregateRoot {
  private String name;
  private UUID columnID;

  public Card(String name, Board board) {
    super();
    this.setName(name);
    this.setColumnID(board.get(0).getID());
    this.addDomainEvent(new CardCreatedEvent(this.id.toString(), this, board.getID().toString()));
  }

  public Card(UUID id, String name, UUID columnID) {
    this.id = id;
    this.name = name;
    this.columnID = columnID;
  }

  public void setName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Card name should not be empty");
    }
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public UUID getColumnID() {
    return this.columnID;
  }

  public void setColumnID(UUID columnID) {
    this.columnID = columnID;
  }
}