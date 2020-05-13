package ddd.kanban.adapter.repository.board;

import ddd.kanban.domain.model.board.Board;
import ddd.kanban.usecase.DTO.BoardDTO;
import ddd.kanban.usecase.repository.BoardRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class InMemoryBoardRepository implements BoardRepository {
    private List<BoardDTO> boards;

    public InMemoryBoardRepository(){
        boards = new ArrayList<BoardDTO>();
    }


    @Override
    public void add(BoardDTO boardDTO) {
        boards.add(boardDTO);
    }

    @Override
    public BoardDTO findById(String boardId) {
        return boards.stream()
                .filter(findBoardById(boardId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private static Predicate<BoardDTO> findBoardById(String boardId){
        return board -> board.getId().equals(boardId);
    }

    @Override
    public void save(BoardDTO boardDTO) {
        for (BoardDTO each : boards){
            if (each.getId().equals(boardDTO.getId()))
                boards.set(boards.indexOf(each), boardDTO);
        }
    }

    @Override
    public List<BoardDTO> findAll() {
        return boards;
    }
}
