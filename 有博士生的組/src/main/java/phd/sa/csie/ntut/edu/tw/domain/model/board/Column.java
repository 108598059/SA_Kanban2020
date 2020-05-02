package phd.sa.csie.ntut.edu.tw.domain.model.board;

import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Column {

  private UUID id;
  private String title;
  private int wip;
  private ArrayList<UUID> cardIds;

  public Column(String title) {
    this.id = UUID.randomUUID();
    this.title = title;
    this.wip = 0;
    this.cardIds = new ArrayList<>();
  }

  public Column(Column col) {
    this.id = col.id;
    this.title = col.title;
    this.wip = wip;
    this.cardIds = new ArrayList<>();
    for (UUID cardID: col.cardIds) {
      this.cardIds.add(cardID);
    }
  }

  public UUID getId() {
    return this.id;
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
    cardIds.add(uuid);
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

}