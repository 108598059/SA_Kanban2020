package phd.sa.csie.ntut.edu.tw.model.aggregate.board;

import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.model.aggregate.card.Card;

import java.util.UUID;

import static org.junit.Assert.*;

public class ColumnTest {
    @Test
    public void column_title_is_empty_should_raise_illegal_argument_exception() {
        try {
            new Column("");
        } catch (IllegalArgumentException e){
            assertEquals("Column title should not be empty", e.getMessage());
            return;
        }
        fail("Column title is empty should raise IllegalArgumentException.");
    }

    @Test
    public void default_wip_of_column_should_be_zero() {
        assertEquals(0, new Column("Develop").getWIP());
    }

    @Test
    public void test_set_negative_wip() {
        try {
            new Column("Develop").setWIP(-1);
        } catch (IllegalArgumentException e) {
            assertEquals("Column WIP should not be negative", e.getMessage());
        }
    }

    @Test
    public void card_exist() {
        Board board = new Board(UUID.randomUUID(), "Kanban");
        Column column = new Column("Develop");
        Card card = new Card("Implement a card", board.getID().toString());
        column.addCard(card.getID());
        assertTrue(column.cardExist(card.getID()));
    }

    @Test
    public void card_not_exist() {
        assertFalse(new Column("Develop").cardExist(UUID.randomUUID()));
    }
}