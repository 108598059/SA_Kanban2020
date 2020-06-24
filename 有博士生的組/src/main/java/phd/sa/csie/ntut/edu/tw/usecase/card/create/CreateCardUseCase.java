package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

public class CreateCardUseCase extends UseCase<CreateCardUseCaseInput, CreateCardUseCaseOutput> {
    private CardRepository cardRepository;
    private BoardRepository boardRepository;

    public CreateCardUseCase(DomainEventBus eventBus, CardRepository cardRepository, BoardRepository boardRepository) {
        super(eventBus);
        this.cardRepository = cardRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public void execute(CreateCardUseCaseInput input, CreateCardUseCaseOutput output) {
        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(input.getBoardID()));

        Card card = new Card(input.getCardName(), board);

        this.cardRepository.save(CardDTOConverter.toDTO(card));
        this.eventBus.postAll(card);

        output.setCardName(card.getName());
        output.setCardID(card.getID().toString());
    }
}