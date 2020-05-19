package phd.sa.csie.ntut.edu.tw.model.board;

import phd.sa.csie.ntut.edu.tw.model.AggregateRoot;
import phd.sa.csie.ntut.edu.tw.model.board.event.CardEnterColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.board.event.CardLeaveColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Board extends AggregateRoot {

  private String name;
  private List<Column> columns;

  public Board(String name) {
    super();
    this.name = name;
    this.columns = new ArrayList<Column>();
    Column backlog = new Column("Backlog");
    Column archive = new Column("Archive");
    this.columns.add(backlog);
    this.columns.add(archive);
  }

  public Board(UUID id, String name, List<Column> columns) {
    this.id = id;
    this.name = name;
    this.columns = columns;
  }

  public void commitCard(Card card) {
    Column backlog = this.columns.get(0);
    backlog.addCard(card.getID());
  }

  public int getColumnNumber() {
    return this.columns.size();
  }

  public Column get(int n) {
    return Collections.unmodifiableList(this.columns).get(n);
  }

  public String getName() {
    return this.name;
  }

  public UUID createColumn(String columnTitle) {
    Column column = new Column(columnTitle);
    this.columns.add(column);
    return column.getID();
  }

  public void addCardToColumn(UUID cardID, UUID columnID) {
    Column column = this.getColumnByID(columnID);
    column.addCard(cardID);
  }

  public void setColumnWIP(UUID columnID, int wip) {
    if (wip < 0) {
      throw new IllegalArgumentException("Column WIP should be positive.");
    }
    Column column = this.getColumnByID(columnID);
    column.setWIP(wip);
  }

  public String moveCard(UUID cardID, UUID fromColumnID, UUID toColumnID) {
    Column from = this.getColumnByID(fromColumnID);
    Column to = this.getColumnByID(toColumnID);

    from.removeCard(cardID);
    this.addDomainEvent(new CardLeaveColumnEvent(UUID.randomUUID().toString(), fromColumnID.toString()));
    to.addCard(cardID);
    this.addDomainEvent(new CardEnterColumnEvent(UUID.randomUUID().toString(), toColumnID.toString(), cardID.toString()));
    
    return to.getID().toString();
  }

  private Column getColumnByID(UUID id) {
    for (Column column : this.columns) {
      if (column.getID().equals(id)) {
        return column;
      }
    }
    throw new RuntimeException("Column Not Found");
  }

  public Column findColumnByID(UUID id) {
    return new Column(this.getColumnByID(id));
  }

  public List<Column> getColumns() {
    return Collections.unmodifiableList(this.columns);
  }

}
