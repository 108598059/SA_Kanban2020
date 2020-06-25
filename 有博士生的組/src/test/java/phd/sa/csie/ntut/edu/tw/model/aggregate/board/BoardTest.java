package phd.sa.csie.ntut.edu.tw.model.aggregate.board;

import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.create.BoardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.create.ColumnCreatedEvent;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.enter.BoardEnteredEvent;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.move.CardEnteredColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.move.CardLeftColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.wip.ColumnWIPSetEvent;
import phd.sa.csie.ntut.edu.tw.model.aggregate.card.Card;

import java.util.UUID;

import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void create_board_should_issue_board_created_event() {
        Board board = new Board(UUID.randomUUID(), "Kanban");
        assertEquals(1, board.getDomainEvents().size());
        assertEquals(BoardCreatedEvent.class, board.getDomainEvents().get(0).getClass());
    }

    @Test
    public void board_name_empty_exception() {
        try {
            new Board(UUID.randomUUID(), "");
        } catch (IllegalArgumentException e) {
            assertEquals("Board name should not be empty", e.getMessage());
            return;
        }
        fail("Board name is empty should raise IllegalArgumentException");
    }

    @Test
    public void create_column_should_between_backlog_and_archive() {
        Board board = new Board(UUID.randomUUID(), "Kanban");
        board.createColumn("Develop", 0);
        assertEquals("Develop", board.get(0).getTitle());
    }

    @Test
    public void add_duplicate_column_exception() {
        Board board = new Board(UUID.randomUUID(), "Kanban");
        board.createColumn("Backlog", 0);
        assertEquals("Backlog", board.get(0).getTitle());
        try {
            board.createColumn("Backlog", 1);
        } catch (IllegalArgumentException e) {
            assertEquals("Column title duplicated", e.getMessage());
            return;
        }
        fail("Column title duplicated should raise IllegalArgumentException");
    }

    @Test
    public void commit_card() {
        Board board = new Board(UUID.randomUUID(), "Kanban");
        board.createColumn("Backlog", 0);
        Card card = new Card("Implement a card", board.getID().toString());
        assertEquals(2, board.getDomainEvents().size());
        board.commitCard(card, board.get(0).getID());
        assertTrue(board.get(0).cardExist(card.getID()));
        assertEquals(3, board.getDomainEvents().size());
        assertEquals(CardEnteredColumnEvent.class, board.getDomainEvents().get(2).getClass());
    }

    @Test
    public void move_card(){
        Board board = new Board(UUID.randomUUID(), "Kanban");
        board.createColumn("Backlog", 0);
        board.createColumn("Archive", 1);
        Card card = new Card("Implement a card", board.getID().toString());
        board.addCardToColumn(card.getID(), board.get(0).getID());

        assertEquals(3, board.getDomainEvents().size());

        board.moveCard(card.getID(), board.get(0).getID(), board.get(1).getID());

        assertEquals(5, board.getDomainEvents().size());
        assertEquals(CardLeftColumnEvent.class, board.getDomainEvents().get(3).getClass());
        assertEquals(CardEnteredColumnEvent.class, board.getDomainEvents().get(4).getClass());
        assertTrue(board.get(1).cardExist(card.getID()));
    }

    @Test
    public void get_column_by_id() {
        Board board = new Board(UUID.randomUUID(), "Kanban");
        board.createColumn("Backlog", 0);
        UUID columnID = board.get(0).getID();
        assertEquals("Backlog", board.findColumnByID(columnID).getTitle());
    }

    @Test
    public void get_column_by_id_not_found() {
        try {
            new Board(UUID.randomUUID(), "Kanban").findColumnByID(UUID.randomUUID());
        } catch (RuntimeException e) {
            assertEquals("Column Not Found", e.getMessage());
        }
    }

    @Test
    public void create_column_should_issue_column_created_event() {
        Board board = new Board(UUID.randomUUID(), "Kanban");
        assertEquals(1, board.getDomainEvents().size());

        board.createColumn("develop", 0);
        assertEquals(2, board.getDomainEvents().size());
        assertEquals(ColumnCreatedEvent.class, board.getDomainEvents().get(1).getClass());
    }

    @Test
    public void set_column_wip_should_issue_column_wip_set_event() {
        Board board = new Board(UUID.randomUUID(), "Kanban");
        UUID columnID = board.createColumn("develop", 0);
        assertEquals(2, board.getDomainEvents().size());

        board.setColumnWIP(columnID, 3);
        assertEquals(3, board.getDomainEvents().size());
        assertEquals(ColumnWIPSetEvent.class, board.getDomainEvents().get(2).getClass());
    }

    @Test
    public void enter_board_should_issue_board_entered_event() {
        Board board = new Board(UUID.randomUUID(), "Kanban");
        assertEquals(1, board.getDomainEvents().size());

        board.enter();
        assertEquals(2, board.getDomainEvents().size());
        assertEquals(BoardEnteredEvent.class, board.getDomainEvents().get(1).getClass());
    }
}
