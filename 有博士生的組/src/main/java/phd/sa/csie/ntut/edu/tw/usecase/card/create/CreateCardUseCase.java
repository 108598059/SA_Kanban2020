package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CreateCardUseCase extends UseCase<CreateCardUseCaseInput, CreateCardUseCaseOutput> {
  private CardRepository cardRepository;
  private BoardRepository boardRepository;

  public CreateCardUseCase(DomainEventBus eventBus, CardRepository cardRepository, BoardRepository boardRepository) {
    super(eventBus);
    this.cardRepository = cardRepository;
    this.boardRepository = boardRepository;
  }

  @Override
  public void execute(CreateCardUseCaseInput createCardInput, CreateCardUseCaseOutput createCardOutput) {
    Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(createCardInput.getBoardID()));
    Card card = new Card(createCardInput.getCardName(), board);

    this.cardRepository.save(CardDTOConverter.toDTO(card));
    this.eventBus.postAll(card);

    createCardOutput.setCardName(card.getName());
    createCardOutput.setCardID(card.getID());
  }
}