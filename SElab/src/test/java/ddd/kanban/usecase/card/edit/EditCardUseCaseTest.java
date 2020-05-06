package ddd.kanban.usecase.card.edit;

import ddd.kanban.adapter.repository.card.InMemoryCardRepository;
import ddd.kanban.domain.model.card.Card;
import ddd.kanban.domain.model.card.CardType;
import ddd.kanban.usecase.card.edit.EditCardUseCase;
import ddd.kanban.usecase.card.edit.EditCardUseCaseInput;
import ddd.kanban.usecase.card.edit.EditCardUseCaseOutput;
import org.junit.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class EditCardUseCaseTest {
    private InMemoryCardRepository cardRepository;

    @Before
    public void setUp(){
        cardRepository = new InMemoryCardRepository();
    }

    @Test
    public void testEditCardUseCase() {
        String cardName = "TestCard";
        String cardDescription = "CardDescription";
        CardType cardCardType = new CardType();
        List<String> cardTags = new ArrayList<String>(); cardTags.add("cardTag1"); cardTags.add("cardTag2");
        List<String> cardAssignUsers = new ArrayList<String>(); cardAssignUsers.add("108598034"); cardAssignUsers.add("108598087");
        Date cardPlannedStartDate = new Date();
        Date cardPlannedFinishDate = new Date();
        int cardPriority = 1;

        String newCardName = "cardNewName";
        String newCardDescription = "cardNewDescription";
        CardType newCardCardType = new CardType();
        List<String> newCardTags = new ArrayList<String>(); cardTags.add("cardNewTag1"); cardTags.add("cardNewTag2");
        List<String> newCardAssignUsers = new ArrayList<String>(); cardAssignUsers.add("108598001"); cardAssignUsers.add("108598099");
        Date newCardPlannedStartDate = new Date();
        Date newCardPlannedFinishDate = new Date();
        int newCardPriority = 87;

        Card card = new Card(UUID.randomUUID().toString(), cardName, cardDescription,  cardCardType, cardTags, cardAssignUsers, cardPlannedStartDate, cardPlannedFinishDate, cardPriority);
        cardRepository.add(card);

        EditCardUseCase editCardUseCase = new EditCardUseCase(cardRepository);
        EditCardUseCaseInput editCardUseCaseInput = new EditCardUseCaseInput("workflow001", card.getId(), newCardName, newCardDescription, newCardCardType, newCardTags, newCardAssignUsers, newCardPlannedStartDate, newCardPlannedFinishDate, newCardPriority);
        EditCardUseCaseOutput editCardUseCaseOutput = new EditCardUseCaseOutput();

        editCardUseCase.execute(editCardUseCaseInput, editCardUseCaseOutput);

        card = cardRepository.findById(card.getId());

        assertEquals(editCardUseCaseOutput.getCardName(), card.getTitle());
        assertEquals(editCardUseCaseOutput.getCardDescription(), card.getDescription());
    }

}
