package phd.sa.csie.ntut.edu.tw.domain.model.card;

import java.util.UUID;

import com.google.common.eventbus.Subscribe;

import phd.sa.csie.ntut.edu.tw.domain.model.AggregateRoot;
import phd.sa.csie.ntut.edu.tw.domain.model.board.event.CardEnterColumnEvent;
import phd.sa.csie.ntut.edu.tw.domain.model.board.event.CardLeaveColumnEvent;
import phd.sa.csie.ntut.edu.tw.domain.model.card.event.CardCreatedEvent;

public class Card extends AggregateRoot {
  private String name;
  private UUID columnId;
  private UUID boardId;

  public Card(String name, UUID boardId) {
    this.name = name;
    this.boardId = boardId;
    this.addDomainEvent(new CardCreatedEvent(this));
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

  @Subscribe
  public void entered(CardEnterColumnEvent e) {
    this.columnId = e.getColumnId();
  }

  @Subscribe
  public void leaved(CardLeaveColumnEvent e) {

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

  public UUID getBoardId() {
    return boardId;
  }

  public void setBoardId(UUID boardId) {
    this.boardId = boardId;
  }
}