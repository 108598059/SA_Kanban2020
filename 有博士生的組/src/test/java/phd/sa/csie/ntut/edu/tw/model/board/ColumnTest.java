package phd.sa.csie.ntut.edu.tw.model.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.model.board.Column;

public class ColumnTest {
  @Test
  public void column_title_is_empty_should_raise_illegal_argument_exception() {
    try {
      Column column = new Column("");
    } catch (IllegalArgumentException e){
      assertEquals("Column title should not be empty", e.getMessage());
      return;
    }
    fail("Column title is empty should raise IllegalArgumentException.");
  }
}