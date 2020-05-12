package phd.sa.csie.ntut.edu.tw.domain.model.board;

import java.util.ArrayList;
import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.Entity;

public class Column extends Entity {

  private String title;
  private int wip;
  private ArrayList<UUID> cardIds;

  public Column(String title) {
    this.title = title;
    this.wip = 0;
    this.cardIds = new ArrayList<UUID>();
  }

  public Column(Column col) {
    // TODO Not sure whether this is the correct way of copying an object.
    this.id = col.id;
    this.title = col.title;
    this.wip = col.wip;
    this.cardIds = new ArrayList<UUID>();
    for (UUID cardID : col.cardIds) {
      this.cardIds.add(cardID);
    }
  }

  public String getTitle() {
    return this.title;
  }

  public void setWIP(int wip) {
    this.wip = wip;
  }

  public int getWIP() {
    return this.wip;
  }

  public void addCard(UUID uuid) {
    if (this.wip != 0 && cardIds.size() == this.wip) {
      throw new IllegalStateException("The card cannot be moved to the column that has achieved its WIP limit.");
    } else {
      cardIds.add(uuid);
    }
  }

  public void removeCard(UUID uuid) {
    cardIds.remove(uuid);
  }

  public boolean cardExist(UUID id) {
    for (int i = 0; i < this.cardIds.size(); ++i) {
      if (id == this.cardIds.get(i)) {
        return true;
      }
    }
    return false;
  }

  public ArrayList<UUID> getCardIds() {
    return this.cardIds;
  }

}