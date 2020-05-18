package phd.sa.csie.ntut.edu.tw.usecase.column.read;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.board.Column;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GetColumnsByBoardIDUseCase extends UseCase<GetColumnsByBoardIDUsecaseInput, GetColumnsByBoardIDUsecaseOutput> {
    private BoardRepository boardRepository;
    private CardRepository cardRepository;

    public GetColumnsByBoardIDUseCase(@Autowired BoardRepository boardRepository, @Autowired CardRepository cardRepository) {
        this.boardRepository = boardRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public void execute(GetColumnsByBoardIDUsecaseInput input, GetColumnsByBoardIDUsecaseOutput output) {
        BoardDTO boardDTO = this.boardRepository.findByID(input.getBoardID());
        Board board = BoardDTOConverter.toEntity(boardDTO);
        List<Column> columnList = board.getColumns();

        List<GetColumnsByBoardIDUsecaseOutput.ColumnViewObject> columnViewList = new ArrayList<>();
        for (Column column: columnList) {
            GetColumnsByBoardIDUsecaseOutput.ColumnViewObject columnViewObject = new GetColumnsByBoardIDUsecaseOutput.ColumnViewObject();
            columnViewObject.setID(column.getID().toString());
            columnViewObject.setTitle(column.getTitle());
            columnViewObject.setWip(column.getWIP());

            List<GetColumnsByBoardIDUsecaseOutput.ColumnViewObject.CardViewObject> cardList = new ArrayList<>();
            List<UUID> cardIDList = column.getCardIDs();
            for (UUID cardID: cardIDList) {
                CardDTO cardDTO = this.cardRepository.findByID(cardID.toString());
                GetColumnsByBoardIDUsecaseOutput.ColumnViewObject.CardViewObject cardViewObject =
                        new GetColumnsByBoardIDUsecaseOutput.ColumnViewObject.CardViewObject();
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
