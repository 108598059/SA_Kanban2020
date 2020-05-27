package domain.entity.workflow;

import domain.entity.card.Card;

import java.util.*;

public class Swimlane {

    private String id;
    private String name;
    private List<String> cards;


    public Swimlane(){
        cards = new ArrayList<String>();
        this.id = UUID.randomUUID().toString();
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
