package phd.sa.csie.ntut.edu.tw.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.Entity;

public class Column extends Entity {

  private String title;
  private int wip;
  private List<UUID> cardIDs;

  public Column(String title) {
    super();
    this.setTitle(title);
    this.wip = 0;
    this.cardIDs = new ArrayList<>();
  }

  public Column(Column col) {
    this.id = col.id;
    this.setTitle(col.title);
    this.wip = col.wip;
    this.cardIDs = new ArrayList<>();
    for (UUID cardID : col.cardIDs) {
      this.cardIDs.add(cardID);
    }
  }

  public Column(UUID id, String title, List<UUID> cardIDs, int wip) {
    this.id = id;
    this.setTitle(title);
    this.cardIDs = cardIDs;
    this.wip = wip;
  }

  public void setTitle(String title) {
    if (title == null || title.isEmpty()){
      throw new IllegalArgumentException("Column title should not be empty");
    }
    this.title = title;
  }

  public String getTitle() {
    return this.title;
  }

  public void setWIP(int wip) {
    if (wip < 0) {
      throw new IllegalArgumentException("Column WIP should not be negative");
    }
    this.wip = wip;
  }

  public int getWIP() {
    return this.wip;
  }

  public void addCard(UUID id) {
    if (this.wip != 0 && cardIDs.size() == this.wip) {
      throw new IllegalStateException("The card cannot be moved to the column that has achieved its WIP limit.");
    } else {
      cardIDs.add(id);
    }
  }

  public void removeCard(UUID id) {
    cardIDs.remove(id);
  }

  public boolean cardExist(UUID id) {
    for (int i = 0; i < this.cardIDs.size(); ++i) {
      if (id.equals(this.cardIDs.get(i))) {
        return true;
      }
    }
    return false;
  }

  public List<UUID> getCardIDs() {
    return this.cardIDs;
  }

}