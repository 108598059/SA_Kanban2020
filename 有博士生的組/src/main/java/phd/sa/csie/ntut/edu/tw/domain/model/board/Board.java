package phd.sa.csie.ntut.edu.tw.domain.model.board;

import com.google.common.eventbus.Subscribe;
import phd.sa.csie.ntut.edu.tw.domain.model.AggregateRoot;
import phd.sa.csie.ntut.edu.tw.domain.model.board.event.ColumnEnteredEvent;
import phd.sa.csie.ntut.edu.tw.domain.model.board.event.ColumnLeavedEvent;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.domain.model.card.event.CardCreatedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class Board extends AggregateRoot {

  private UUID Id;
  private String name;
  private ArrayList<Column> columns;
  private Column startColumn;
  private Column endColumn;

  public Board(String name) {
    this.Id = UUID.randomUUID();
    this.name = name;
    this.columns = new ArrayList<Column>();
    this.startColumn = new Column("Backlog");
    this.endColumn = new Column("Archive");
  }

  @Subscribe
  public void commit(CardCreatedEvent e) {
    Card card = e.getEntity();
    this.startColumn.addCard(card.getId());
    card.setColumnID(this.startColumn.getId());
  }

  public int getNumberOfColumns() {
    return 2 + this.columns.size();
  }

  public Column get(int n) {
    if (n == 0) {
      return new Column(this.startColumn);
    } else if (n == this.getNumberOfColumns() - 1) {
      return new Column(this.endColumn);
    }
    return Collections.unmodifiableList(this.columns).get(n - 1);
  }

  public UUID getId() {
    return this.Id;
  }

  public String getName() {
    return this.name;
  }

  public UUID createColumn(String columnTitle) {
    Column column = new Column(columnTitle);
    this.columns.add(column);
    return column.getId();
  }

  public void addCardToColumn(UUID cardId, UUID columnId) {
    Column column = this.getColumnById(columnId);
    column.addCard(cardId);
  }

  public void setColumnWIP(UUID columnId, int wip) {
    if (wip < 0) {
      throw new IllegalArgumentException("Column WIP should be positive.");
    }
    Column column = this.getColumnById(columnId);
    column.setWIP(wip);
  }

  public String moveCard(UUID cardId, UUID fromColumnId, UUID toColumnId) {
    Column from = this.getColumnById(fromColumnId);
    Column to = this.getColumnById(toColumnId);
    from.removeCard(cardId);
    this.addDomainEvent(new ColumnLeavedEvent(UUID.randomUUID().toString(), fromColumnId.toString()));
    to.addCard(cardId);
    this.addDomainEvent(new ColumnEnteredEvent(UUID.randomUUID().toString(), toColumnId.toString()));
    return to.getId().toString();
  }

  private Column getColumnById(UUID id) {
    if (this.startColumn.getId().equals(id)) {
      return this.startColumn;
    }
    if (this.endColumn.getId().equals(id)) {
      return this.endColumn;
    }
    for (Column column : columns) {
      if (column.getId().equals(id)) {
        return column;
      }
    }
    throw new RuntimeException("Column Not Found");
  }

  public Column findColumnById(UUID id) {
    return new Column(this.getColumnById(id));
  }

}
