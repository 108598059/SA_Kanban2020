package domain.model.aggregate.card;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class CardTest {
    @Before
    public void SetUp(){

    }

    @Test
    public void create_card_should_generate_CardCreated_event_in_the_domainEvent_list() {
        Card card = new Card("cardName", "workflowId" ,"laneId");
        assertThat(card.getDomainEvents().size()).isEqualTo(1);
        assertThat(card.getDomainEvents().get(0).detail()).startsWith("CardCreated");

        card.clearDomainEvents();
        assertThat(card.getDomainEvents().size()).isEqualTo(0);
    }


}
