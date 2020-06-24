package ddd.kanban.usecase.card.mapper;

import ddd.kanban.domain.model.card.Card;
import ddd.kanban.usecase.card.entity.CardEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardEntityMapperTest {
    @Test
    public void testMappingCardEntityFrom(){
        Card card = new Card("card-0001","test card entity from", "board-0001", "workflow-0001", "lane-0001");
        CardEntity cardEntity = CardEntityMapper.mappingCardEntityFrom(card);
        assertEquals("card-0001", cardEntity.getId());
        assertEquals("test card entity from" , cardEntity.getTitle());
        assertEquals("board-0001", cardEntity.getBoardId());
        assertEquals("workflow-0001", cardEntity.getWorkflowId());
        assertEquals("lane-0001", cardEntity.getLaneId());
    }

    @Test
    public void testMappingCardFrom(){
        Card card = new Card("card-0001","test card entity from", "board-0001", "workflow-0001", "lane-0001");
        CardEntity cardEntity = CardEntityMapper.mappingCardEntityFrom(card);
        Card cardMaped = CardEntityMapper.mappingCardFrom(cardEntity);
        assertEquals("card-0001", cardMaped.getId());
        assertEquals("test card entity from" , cardMaped.getTitle());
        assertEquals("board-0001", cardMaped.getBoardId());
        assertEquals("workflow-0001", cardMaped.getWorkflowId());
        assertEquals("lane-0001", cardMaped.getLaneId());
    }
}
