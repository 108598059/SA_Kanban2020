package ddd.kanban.adapter.repository.board;

import ddd.kanban.adapter.database.DataBaseUtility;
import ddd.kanban.domain.model.Board.Board;
import ddd.kanban.usecase.repository.BoardRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SqliteBoardRepository implements BoardRepository {

    private DataBaseUtility dataBaseUtility;

    private List<Board> boards;

    public SqliteBoardRepository(){
        dataBaseUtility = new DataBaseUtility();
        boards = new ArrayList<>();
        boards.addAll(findAllBoardFromDatabase());
    }

    @Override
    public void add(Board board) {
        if (isExist(board))
            throw new RuntimeException("Board exist");
        boards.add(board);
    }

    private boolean isExist(Board board) {
        return boards.stream()
                .map(Board::getId)
                .anyMatch(id -> board.getId().equals(id));
    }

    @Override
    public Board findById(String boardId) {
        return boards.stream()
                .filter(board -> board.getId().equals(boardId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private static Predicate<Board> findBoardById(String boardId){
        return board -> board.getId().equals(boardId);
    }

    @Override
    public List<Board> findAll() {
        return boards;
    }

    @Override
    public void save() {
        boards.forEach(this::saveToDatabase);
    }

    private void saveToDatabase(Board board){
        String saveCommand = String.format("INSERT INTO Board (id, name, description) VALUES ('%1$s', '%2$s', '%3$s')" +
                "ON CONFLICT(id) DO UPDATE SET name = '%2$s'", board.getId(), board.getName(), board.getDescription());

        try {
            dataBaseUtility.getConnection();
            Statement statement = dataBaseUtility.createStatement();
            boolean resultInformation = statement.execute(saveCommand);
            dataBaseUtility.commitConnection();
        } catch (SQLException e) {
            dataBaseUtility.rollbackConnection();
            e.printStackTrace();
        } finally {
            dataBaseUtility.closeConnection();
        }
    }

    private List<Board> findAllBoardFromDatabase(){
        String queryCommand = String.format("SELECT * FROM Board");
        List<Board> resultBoards = new ArrayList<>();

        try{
            dataBaseUtility.getConnection();
            Statement statement = dataBaseUtility.createStatement();
            ResultSet resultSet = statement.executeQuery(queryCommand);

            while (resultSet.next()){
                Board board = new Board(resultSet.getString("id"), resultSet.getString("name"),resultSet.getString("description"));
                resultBoards.add(board);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
           dataBaseUtility.closeConnection();
        }
        return resultBoards;
    }
}
