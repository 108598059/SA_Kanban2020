package domain.usecase.board.commit;

import domain.entity.board.Board;
import domain.usecase.board.BoardDTO;
import domain.usecase.board.BoardRepository;
import domain.usecase.board.BoardTransformer;

public class CommitWorkflowUseCase {

    private BoardRepository boardRepository;

    public CommitWorkflowUseCase(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void execute(CommitWorkflowInput commitWorkflowInput, CommitWorkflowOutput commitWorkflowOutput){

        BoardDTO boardDTO = boardRepository.getBoardById(commitWorkflowInput.getBoardId());


        Board board = BoardTransformer.toBoard(boardDTO);
        board.add(commitWorkflowInput.getWorkflowId());
        boardDTO = BoardTransformer.toDTO(board);

        boardRepository.save(boardDTO);

        commitWorkflowOutput.setNumberOfWorkflow(board.getNumberOfWorkflow());
    }
}
