package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.domain.model.card.event.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CreateCardUseCaseTest {
  private CardRepository cardRepository;
  private Board board;
  private DomainEventBus eventBus;

  private class MockCardCreatedEventListener {

    private int count = 0;

    @Subscribe
    public void cardCreatedListener(CardCreatedEvent e) {
      this.count += 1;
    }

    public int getEventCount() {
      return this.count;
    }
  }

  @Before
  public void given_a_board() {
    this.cardRepository = new MemoryCardRepository();

    this.board = new Board("Kanban");
    this.eventBus = new DomainEventBus();
    this.eventBus.register(this.board);
  }

  @Test
  public void creating_a_new_card_should_commit_the_card_to_the_backlog_column() {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, this.eventBus);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

    createCardUseCaseInput.setCardName("Create Card");
    createCardUseCaseInput.setBoardID(this.board.getId().toString());

    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

    assertEquals("Create Card", createCardUseCaseOutput.getCardName());
    assertNotEquals("", createCardUseCaseOutput.getCardId());
    assertNotNull(createCardUseCaseOutput.getCardId());

    Card card = cardRepository.findCardByUUID(UUID.fromString(createCardUseCaseOutput.getCardId()));
    assertEquals(this.board.get(0).getId().toString(), card.getColumnID().toString());
  }

  @Test
  public void create_card_event_should_be_issued_when_a_card_being_created() {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, this.eventBus);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

    createCardUseCaseInput.setCardName("Create Card");
    createCardUseCaseInput.setBoardID(this.board.getId().toString());

    MockCardCreatedEventListener mockListener = new MockCardCreatedEventListener();
    this.eventBus.register(mockListener);

    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
    assertEquals(mockListener.getEventCount(), 1);
  }
}