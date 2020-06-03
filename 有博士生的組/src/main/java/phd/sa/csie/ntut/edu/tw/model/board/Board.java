package phd.sa.csie.ntut.edu.tw.model.board;

import phd.sa.csie.ntut.edu.tw.model.domain.AggregateRoot;
import phd.sa.csie.ntut.edu.tw.model.board.event.create.BoardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.model.board.event.create.ColumnCreatedEvent;
import phd.sa.csie.ntut.edu.tw.model.board.event.move.CardEnteredColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.board.event.move.CardLeftColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.board.event.wip.ColumnWIPSetEvent;
import phd.sa.csie.ntut.edu.tw.model.card.Card;

import java.util.*;

public class Board extends AggregateRoot {
    private UUID workspaceID;
    private String name;
    private List<Column> columns;

    public Board(UUID workspaceID, String name) {
        super();
        this.workspaceID = workspaceID;
        this.setName(name);
        this.columns = new ArrayList<Column>();
        this.addDomainEvent(new BoardCreatedEvent(this.id.toString(), this.name));
    }

    public Board(UUID id, UUID workspaceID, String name, List<Column> columns) {
        this.id = id;
        this.workspaceID = workspaceID;
        this.name = name;
        this.columns = columns;
    }


    public void commitCard(Card card) {
        Column backlog = this.columns.get(0);
        backlog.addCard(card.getID());
        this.addDomainEvent(new CardEnteredColumnEvent(this.id.toString(), card.getID().toString(), backlog.getID().toString()));
    }

    public int getColumnNumber() {
        return this.columns.size();
    }

    public Column get(int n) {
        return Collections.unmodifiableList(this.columns).get(n);
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Board name should not be empty");
        }
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Column getBacklogColumn() {
        return new Column(this.columns.get(0));
    }

    public Column getArchiveColumn() {
        return new Column(this.columns.get(this.columns.size()-1));
    }

    public UUID createColumn(String columnTitle, int index) {
        for (Column column: this.getColumns()) {
            if (column.getTitle().equals(columnTitle)) {
                throw new IllegalArgumentException("Column title duplicated");
            }
        }
        Column column = new Column(columnTitle);
        this.columns.add(index, column);
        this.addDomainEvent(new ColumnCreatedEvent(this.id.toString(), column.getID().toString(), column.getTitle()));

        return column.getID();
    }

    public void addCardToColumn(UUID cardID, UUID columnID) {
        Column column = this.getColumnByID(columnID);
        column.addCard(cardID);
    }

    public void setColumnWIP(UUID columnID, int wip) {
        this.getColumnByID(columnID).setWIP(wip);
        this.addDomainEvent(new ColumnWIPSetEvent(this.id.toString(), columnID.toString(), wip));
    }

    public String moveCard(UUID cardID, UUID fromColumnID, UUID toColumnID) {
        Column from = this.getColumnByID(fromColumnID);
        Column to = this.getColumnByID(toColumnID);

        from.removeCard(cardID);
        this.addDomainEvent(new CardLeftColumnEvent(this.id.toString(), cardID.toString(), fromColumnID.toString()));

        to.addCard(cardID);
        this.addDomainEvent(new CardEnteredColumnEvent(this.id.toString(), cardID.toString(), toColumnID.toString()));

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

    public UUID getWorkspaceID() {
        return workspaceID;
    }

    public void setWorkspaceID(UUID workspaceID) {
        this.workspaceID = workspaceID;
    }
}
