package phd.sa.csie.ntut.edu.tw.usecase.column.read;

import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.board.Column;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GetColumnsByBoardIDUseCase extends UseCase<GetColumnsByBoardIDUseCaseInput, GetColumnsByBoardIDUseCaseOutput> {
    private BoardRepository boardRepository;
    private CardRepository cardRepository;

    public GetColumnsByBoardIDUseCase(BoardRepository boardRepository, CardRepository cardRepository) {
        this.boardRepository = boardRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public void execute(GetColumnsByBoardIDUseCaseInput input, GetColumnsByBoardIDUseCaseOutput output) {
        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(input.getBoardID()));
        List<Column> columnList = board.getColumns();

        List<GetColumnsByBoardIDUseCaseOutput.ColumnViewObject> columnViewList = new ArrayList<>();
        for (Column column: columnList) {
            GetColumnsByBoardIDUseCaseOutput.ColumnViewObject columnViewObject = new GetColumnsByBoardIDUseCaseOutput.ColumnViewObject();
            columnViewObject.setID(column.getID().toString());
            columnViewObject.setTitle(column.getTitle());
            columnViewObject.setWip(column.getWIP());

            List<GetColumnsByBoardIDUseCaseOutput.ColumnViewObject.CardViewObject> cardList = new ArrayList<>();
            List<UUID> cardIDList = column.getCardIDs();
            for (UUID cardID: cardIDList) {
                CardDTO cardDTO = this.cardRepository.findByID(cardID.toString());
                GetColumnsByBoardIDUseCaseOutput.ColumnViewObject.CardViewObject cardViewObject =
                        new GetColumnsByBoardIDUseCaseOutput.ColumnViewObject.CardViewObject();
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
