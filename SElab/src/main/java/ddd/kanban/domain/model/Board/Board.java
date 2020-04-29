package ddd.kanban.domain.model.Board;



public class Board {

    private String name;
    private final String id;
    private String description;

    public Board(String id, String  name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
