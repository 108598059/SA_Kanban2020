package domain.model.aggregate.card;


import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CardTest {
    private Card card;

    @Before
    public void SetUp(){
        card = new Card("cardName", "workflowId" ,"laneId");
    }

    @Test
    public void create_card_should_generate_CardCreated_event_in_the_domainEvent_list() {

        assertThat(card.getDomainEvents().size()).isEqualTo(1);
        assertThat(card.getDomainEvents().get(0).detail()).startsWith("CardCreated");

        card.clearDomainEvents();
        assertThat(card.getDomainEvents().size()).isEqualTo(0);
    }

    @Test
    public void create_task_should_generate_TaskCreated_event_in_the_domainEvent_list() throws CloneNotSupportedException {

        card.createTask(card.getCardId(),"taskName");

        assertThat(card.getDomainEvents().size()).isEqualTo(2);
        assertThat(card.getDomainEvents().get(1).detail()).startsWith("TaskCreated");

        card.clearDomainEvents();
        assertThat(card.getDomainEvents().size()).isEqualTo(0);
    }


}
