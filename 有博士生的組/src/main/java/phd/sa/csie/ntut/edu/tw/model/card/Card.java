package phd.sa.csie.ntut.edu.tw.model.card;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.AggregateRoot;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.event.CardBelongsColumnSetEvent;
import phd.sa.csie.ntut.edu.tw.model.card.event.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.model.card.event.CardNameSetEvent;
import phd.sa.csie.ntut.edu.tw.model.card.event.LeadTimeCalculatedEvent;

public class Card extends AggregateRoot {
  private String name;
  private UUID belongsColumnID;
  private long leadTime;

  public Card(String name, Board board) {
    super();
    this.addDomainEvent(new CardCreatedEvent(this.id.toString(), this, board.getID().toString()));
    this.setName(name);
    this.setBelongsColumnID(board.getBacklogColumn().getID());
    this.leadTime = -1;
  }

  // For DTO Converter
  public Card(UUID id, String name, UUID belongsColumnID, long leadTime) {
    this.id = id;
    this.name = name;
    this.belongsColumnID = belongsColumnID;
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
    return this.belongsColumnID;
  }

  public void setBelongsColumnID(UUID belongsColumnID) {
    this.belongsColumnID = belongsColumnID;
    this.addDomainEvent(new CardBelongsColumnSetEvent(this.id.toString(), this.belongsColumnID.toString()));
  }

  public long getLeadTime() {
    return leadTime;
  }

  public void setLeadTime(long leadTime) {
    this.leadTime = leadTime;
    this.addDomainEvent(new LeadTimeCalculatedEvent(this.id.toString(), this.leadTime));
  }
}