package phd.sa.csie.ntut.edu.tw.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.domain.Entity;

public class Column extends Entity {

    private String title;
    private int wip;
    private final List<UUID> cardIDs;
    private final List<UUID> preservedPosition;

    public Column(String title) {
        super();
        this.setTitle(title);
        this.wip = 0;
        this.cardIDs = new ArrayList<>();
        this.preservedPosition = new ArrayList<>();
    }

    public Column(Column col) {
        this.id = col.id;
        this.setTitle(col.title);
        this.wip = col.wip;
        this.cardIDs = new ArrayList<>();
        this.preservedPosition = new ArrayList<>();
        this.cardIDs.addAll(col.cardIDs);
    }

    public Column(UUID id, String title, List<UUID> cardIDs, List<UUID> preservedPosition, int wip) {
        this.id = id;
        this.setTitle(title);
        this.cardIDs = cardIDs;
        this.preservedPosition = preservedPosition;
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
        cardIDs.add(id);
    }

    public void removeCard(UUID id) {
        this.cardIDs.remove(id);
    }

    public boolean cardExist(UUID id) {
        if (this.cardIDs.indexOf(id) == -1) {
            return false;
        }
        return true;
    }

    public List<UUID> getCardIDs() {
        return this.cardIDs;
    }

    public List<UUID> getPreservedPosition() {
        return this.preservedPosition;
    }

    public void addPreservedPosition(UUID cardID) {
        this.preservedPosition.add(cardID);
    }

    public void releasePreservedPosition(UUID targetID) {
        this.preservedPosition.remove(targetID);
    }
}