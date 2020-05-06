package domain.usecase.board;



public interface BoardRepository {

    BoardDTO getBoardById(String id);
    void add(BoardDTO boardDTO);
    void save(BoardDTO boardDTO);
}
