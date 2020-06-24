package domain.unit_test;


import domain.entity.aggregate.card.Card;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardTest {

    @Before
    public void setUp(){

    }



    @Test
    public void Create_card_should_create_domain_event(){
        Card card = new Card("1","2","3");
        assertEquals(1, card.getEvents().size());
    }

}
