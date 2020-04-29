package domain.entity.workflow;

import domain.entity.card.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

    public void add(String id){
        this.cards.add(id);
    }

    public Boolean isCardExist(String cardId){
        for (String card: cards){
            if (card.equals(cardId)){
                return true;
            }
        }
        return false;
    }

    public List<String> getCard() {
        return this.cards;
    }
}
