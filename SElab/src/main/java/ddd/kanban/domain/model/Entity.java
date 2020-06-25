package ddd.kanban.domain.model;

public abstract class Entity {

    protected final String id;
    protected String title;

    protected Entity(final String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }




}
