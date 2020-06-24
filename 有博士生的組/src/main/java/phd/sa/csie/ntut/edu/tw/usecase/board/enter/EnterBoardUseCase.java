package phd.sa.csie.ntut.edu.tw.usecase.board.enter;

import phd.sa.csie.ntut.edu.tw.model.aggregate.board.Board;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.Column;
import phd.sa.csie.ntut.edu.tw.model.aggregate.card.Card;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EnterBoardUseCase extends UseCase<EnterBoardUseCaseInput, EnterBoardUseCaseOutput> {
    private BoardRepository boardRepository;
    private CardRepository cardRepository;

    public EnterBoardUseCase(DomainEventBus eventBus, BoardRepository boardRepository, CardRepository cardRepository) {
        super(eventBus);
        this.boardRepository = boardRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public void execute(EnterBoardUseCaseInput input, EnterBoardUseCaseOutput output) {
        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(input.getBoardID()));
        List<Column> columnList = board.enter();

        List<EnterBoardUseCaseOutput.ColumnViewObject> columnViewList = new ArrayList<>();
        for (Column column: columnList) {
            EnterBoardUseCaseOutput.ColumnViewObject columnViewObject = new EnterBoardUseCaseOutput.ColumnViewObject();
            columnViewObject.setID(column.getID().toString());
            columnViewObject.setTitle(column.getTitle());
            columnViewObject.setWIP(column.getWIP());

            List<EnterBoardUseCaseOutput.ColumnViewObject.CardViewObject> cardList = new ArrayList<>();
            List<UUID> cardIDList = column.getCardIDs();
            for (UUID cardID: cardIDList) {
                Card card = CardDTOConverter.toEntity(this.cardRepository.findByID(cardID.toString()));
                EnterBoardUseCaseOutput.ColumnViewObject.CardViewObject cardViewObject =
                        new EnterBoardUseCaseOutput.ColumnViewObject.CardViewObject();
                cardViewObject.setID(card.getID().toString());
                cardViewObject.setName(card.getName());
                cardList.add(cardViewObject);
            }
            columnViewObject.setCardList(cardList);

            columnViewList.add(columnViewObject);
        }

        this.eventBus.postAll(board);

        output.setColumnViewList(columnViewList);
    }
}
