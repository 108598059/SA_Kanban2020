package ddd.kanban.adapter.repository.board;

import ddd.kanban.adapter.database.DataBaseUtility;
import ddd.kanban.domain.model.board.Board;
import ddd.kanban.usecase.repository.BoardRepository;

import java.sql.*;
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
        Connection connection = dataBaseUtility.getConnection();
        String saveCommand = String.format("INSERT INTO Board (id, name, description) VALUES (?, ?, ?)" +
                "ON CONFLICT(id) DO UPDATE SET name = ?");

        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(saveCommand);
            preparedStatement.setString(1, board.getId());
            preparedStatement.setString(2, board.getName());
            preparedStatement.setString(3, board.getDescription());
            preparedStatement.setString(4, board.getName());
            boolean resultInformation = preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            dataBaseUtility.rollBack(connection);
        } finally {
            dataBaseUtility.close(connection);
        }
    }

    private List<Board> findAllBoardFromDatabase(){
        Connection connection = dataBaseUtility.getConnection();
        String queryCommand = String.format("SELECT * FROM Board");
        List<Board> resultBoards = new ArrayList<>();

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryCommand);

            while (resultSet.next()){
                Board board = new Board(resultSet.getString("id"), resultSet.getString("name"),resultSet.getString("description"));
                resultBoards.add(board);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
           dataBaseUtility.close(connection);
        }
        return resultBoards;
    }
}
