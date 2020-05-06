package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CreateCardUseCaseTest {
  private CardRepository cardRepository;
  private Board board;
  private DomainEventBus eventBus;

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
    createCardUseCaseInput.setBoardID(this.board.getUUID().toString());

    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

    assertEquals("Create Card", createCardUseCaseOutput.getCardName());
    assertNotEquals("", createCardUseCaseOutput.getCardId());
    assertNotNull(createCardUseCaseOutput.getCardId());

    Card card = cardRepository.findCardByUUID(UUID.fromString(createCardUseCaseOutput.getCardId()));
    assertEquals(this.board.get(0).getId().toString(), card.getColumnID().toString());
  }
}