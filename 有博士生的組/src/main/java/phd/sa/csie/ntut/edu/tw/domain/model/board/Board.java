package phd.sa.csie.ntut.edu.tw.domain.model.board;

import phd.sa.csie.ntut.edu.tw.domain.model.AggregateRoot;
import phd.sa.csie.ntut.edu.tw.domain.model.board.event.CardEnterColumn;
import phd.sa.csie.ntut.edu.tw.domain.model.board.event.CardLeaveColumn;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;

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
    backlog.addCard(card.getId());

    // TODO Set column id of the moved card by the board or the card itself?
    card.setColumnId(backlog.getId());
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

    // TODO Issue the event by the board or the column itself?
    from.removeCard(cardId);
    this.addDomainEvent(new CardLeaveColumn(UUID.randomUUID().toString(), fromColumnId.toString()));
    to.addCard(cardId);
    this.addDomainEvent(new CardEnterColumn(UUID.randomUUID().toString(), toColumnId.toString(), cardId.toString()));
    
    return to.getId().toString();
  }

  private Column getColumnById(UUID id) {
    for (Column column : this.columns) {
      if (column.getId().equals(id)) {
        return column;
      }
    }
    throw new RuntimeException("Column Not Found");
  }

  public Column findColumnById(UUID id) {
    return new Column(this.getColumnById(id));
  }

  public List<Column> getColumns() {
    return Collections.unmodifiableList(this.columns);
  }

}
