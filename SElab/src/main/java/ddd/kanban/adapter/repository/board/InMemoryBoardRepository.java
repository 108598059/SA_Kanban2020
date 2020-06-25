package ddd.kanban.adapter.repository.board;

import ddd.kanban.usecase.kanbanboard.board.entity.BoardEntity;
import ddd.kanban.usecase.repository.BoardRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class InMemoryBoardRepository implements BoardRepository {
    private List<BoardEntity> boards;

    public InMemoryBoardRepository(){
        boards = new ArrayList<BoardEntity>();
    }


    @Override
    public void add(BoardEntity boardEntity) {
        boards.add(boardEntity);
    }

    @Override
    public BoardEntity findById(String boardId) {
        return boards.stream()
                .filter(findBoardById(boardId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private static Predicate<BoardEntity> findBoardById(String boardId){
        return board -> board.getId().equals(boardId);
    }

    @Override
    public void save(BoardEntity boardEntity) {
        for (BoardEntity each : boards){
            if (each.getId().equals(boardEntity.getId()))
                boards.set(boards.indexOf(each), boardEntity);
        }
    }

    @Override
    public List<BoardEntity> findAll() {
        return boards;
    }
}
