package phd.sa.csie.ntut.edu.tw.model.board;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BoardTest {
    @Test
    public void get_backlog_column_should_return_the_default_backlog_column() {
        Board board = new Board(UUID.randomUUID(), "Kanban");
        assertEquals("Backlog", board.getBacklogColumn().getTitle());
    }

    @Test
    public void add_duplicate_column_should_raise_illegal_argument_exception() {
        Board board = new Board(UUID.randomUUID(), "Kanban");
        try {
            board.createColumn("Backlog");
        } catch (IllegalArgumentException e) {
            assertEquals("Column title duplicated", e.getMessage());
            return;
        }
        fail("Column title duplicated should raise IllegalArgumentException");
    }
}
