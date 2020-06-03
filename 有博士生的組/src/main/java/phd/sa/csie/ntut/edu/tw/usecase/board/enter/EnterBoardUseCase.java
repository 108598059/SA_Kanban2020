package phd.sa.csie.ntut.edu.tw.usecase.board.enter;

import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.board.Column;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EnterBoardUseCase extends UseCase<EnterBoardUseCaseInput, EnterBoardUseCaseOutput> {
    private BoardRepository boardRepository;
    private CardRepository cardRepository;

    public EnterBoardUseCase(BoardRepository boardRepository, CardRepository cardRepository) {
        this.boardRepository = boardRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public void execute(EnterBoardUseCaseInput input, EnterBoardUseCaseOutput output) {
        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(input.getBoardID()));
        List<Column> columnList = board.getColumns();

        List<EnterBoardUseCaseOutput.ColumnViewObject> columnViewList = new ArrayList<>();
        for (Column column: columnList) {
            EnterBoardUseCaseOutput.ColumnViewObject columnViewObject = new EnterBoardUseCaseOutput.ColumnViewObject();
            columnViewObject.setID(column.getID().toString());
            columnViewObject.setTitle(column.getTitle());
            columnViewObject.setWIP(column.getWIP());

            List<EnterBoardUseCaseOutput.ColumnViewObject.CardViewObject> cardList = new ArrayList<>();
            List<UUID> cardIDList = column.getCardIDs();
            for (UUID cardID: cardIDList) {
                CardDTO cardDTO = this.cardRepository.findByID(cardID.toString());
                EnterBoardUseCaseOutput.ColumnViewObject.CardViewObject cardViewObject =
                        new EnterBoardUseCaseOutput.ColumnViewObject.CardViewObject();
                cardViewObject.setID(cardDTO.getID());
                cardViewObject.setName(cardDTO.getName());
                cardList.add(cardViewObject);
            }
            columnViewObject.setCardList(cardList);

            columnViewList.add(columnViewObject);
        }
        output.setColumnList(columnViewList);
    }
}
