package phd.sa.csie.ntut.edu.tw.adapter.repository.memory;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.card.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.Board;
import phd.sa.csie.ntut.edu.tw.model.aggregate.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

public class CardRepositoryTest {

    @Test
    public void createCard() {
        CardRepository cardRepository = new MemoryCardRepository();
        Card card = new Card("test card", new Board(UUID.randomUUID(), "Kanban").getID().toString());

        CardDTO cardDTO = CardDTOConverter.toDTO(card);

        cardRepository.save(cardDTO);

        CardDTO resultCardDTO = cardRepository.findByID(card.getID().toString());
        Card resultCard = CardDTOConverter.toEntity(resultCardDTO);

        assertEquals(card.getName(), resultCard.getName());
        assertEquals(card.getID(), resultCard.getID());
    }
}