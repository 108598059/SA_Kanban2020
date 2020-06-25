package domain.usecase.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SwimlaneDTO {
    private String id;
    private String name;
    private List<String> cards;

    public SwimlaneDTO(){
        cards = new ArrayList<String>();
        this.id = UUID.randomUUID().toString();
    }

    public SwimlaneDTO( String id , String name, List<String> cards ){
        this.id = id ;
        this.name = name ;
        this.cards = new ArrayList<String>(cards) ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCard(String id){
        this.cards.add(id);
    }



    public List<String> getCards() {
        return this.cards;
    }
}
