package phd.sa.csie.ntut.edu.tw.model.card;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.AggregateRoot;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.event.CardBelongsColumnSetEvent;
import phd.sa.csie.ntut.edu.tw.model.card.event.CardCreatedEvent;

public class Card extends AggregateRoot {
  private String name;
  private UUID belongsColumnID;

  public Card(String name, Board board) {
    super();
    this.setName(name);
    this.addDomainEvent(new CardCreatedEvent(this.id.toString(), this, board.getID().toString()));
    this.setBelongsColumnID(board.getBacklogColumn().getID());
  }

  public Card(UUID id, String name, UUID belongsColumnID) {
    this.id = id;
    this.setName(name);
    this.belongsColumnID = belongsColumnID;
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

  public UUID getBelongsColumnID() {
    return this.belongsColumnID;
  }

  public void setBelongsColumnID(UUID belongsColumnID) {
    this.belongsColumnID = belongsColumnID;
    this.addDomainEvent(new CardBelongsColumnSetEvent(this.id.toString(), this.belongsColumnID.toString()));
  }
}