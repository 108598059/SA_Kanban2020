package ddd.kanban.domain;

import ddd.kanban.domain.model.card.card.Card;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CardTest {
    @Test
    public void testCreateCardShouldGenerateCardCreatedDomainEvent() {
        String cardId = UUID.randomUUID().toString();
        String boardId = UUID.randomUUID().toString();
        String workflowId = UUID.randomUUID().toString();
        Card card = new Card(cardId, "CardName", "Card Description", boardId, workflowId);
        
        assertEquals(1, card.getDomainEvents().size());
        card.clearDomainEvents();
        assertEquals(0, card.getDomainEvents().size());
    }
}
