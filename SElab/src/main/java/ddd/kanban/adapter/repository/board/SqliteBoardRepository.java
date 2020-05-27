package ddd.kanban.adapter.repository.board;

import ddd.kanban.adapter.database.DataBaseUtility;
import ddd.kanban.domain.model.board.Board;
import ddd.kanban.usecase.board.entity.BoardEntity;
import ddd.kanban.usecase.repository.BoardRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SqliteBoardRepository implements BoardRepository {

    private DataBaseUtility dataBaseUtility;

    private List<BoardEntity> boards;

    public SqliteBoardRepository(){
        dataBaseUtility = new DataBaseUtility();
        boards = new ArrayList<>();
        boards.addAll(findAllBoardFromDatabase());
    }

    @Override
    public void add(BoardEntity boardEntity) {
        if (isExist(boardEntity))
            throw new RuntimeException("Board exist");
        boards.add(boardEntity);
        saveToDatabase(boardEntity);
    }

    private boolean isExist(BoardEntity boardEntity) {
        return boards.stream()
                .map(BoardEntity::getId)
                .anyMatch(id -> boardEntity.getId().equals(id));
    }

    @Override
    public BoardEntity findById(String boardId) {
        return boards.stream()
                .filter(board -> board.getId().equals(boardId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private static Predicate<Board> findBoardById(String boardId){
        return board -> board.getId().equals(boardId);
    }

    @Override
    public List<BoardEntity> findAll() {
        return boards;
    }

    @Override
    public void save(BoardEntity boardEntity) {
        for (BoardEntity board : boards){
            if (board.getId().equals(boardEntity.getId())){
                boards.set(boards.indexOf(board), boardEntity);
            }
        }
        boards.forEach(this::saveToDatabase);
    }

    private void saveToDatabase(BoardEntity boardEntity){
        Connection connection = dataBaseUtility.getConnection();
        String saveCommand = String.format("INSERT INTO Board (id, name, description) VALUES (?, ?, ?)" +
                "ON CONFLICT(id) DO UPDATE SET name = ?");

        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(saveCommand);
            preparedStatement.setString(1, boardEntity.getId());
            preparedStatement.setString(2, boardEntity.getTitle());
            preparedStatement.setString(3, boardEntity.getDescription());
            preparedStatement.setString(4, boardEntity.getTitle());
            boolean resultInformation = preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            dataBaseUtility.rollBack(connection);
        } finally {
            dataBaseUtility.close(connection);
        }
    }

    private List<BoardEntity> findAllBoardFromDatabase(){
        List<BoardEntity> boardEntities = findAllBoard();
        for (BoardEntity each : boardEntities){
            each.setWorkflowIds(findWorkflowOnBoard(each.getId()));
        }
        return boardEntities;
    }

    private List<BoardEntity> findAllBoard(){
        Connection connection = dataBaseUtility.getConnection();
        String queryCommand = String.format("SELECT id, name, description FROM Board");
        List<BoardEntity> resultBoards = new ArrayList<>();

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryCommand);

            while (resultSet.next()){
                BoardEntity boardEntity = new BoardEntity(resultSet.getString("id"), resultSet.getString("name"),resultSet.getString("description"));
                resultBoards.add(boardEntity);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            dataBaseUtility.close(connection);
        }
        return resultBoards;
    }

    private List<String> findWorkflowOnBoard(String boardId){
        Connection connection = dataBaseUtility.getConnection();
        String queryCommand = String.format("SELECT id \n" +
                "FROM Workflow\n" +
                "WHERE boardId = ?");
        List<String> result = new ArrayList<>();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(queryCommand);
            preparedStatement.setString(1, boardId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                result.add(resultSet.getString("id"));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            dataBaseUtility.close(connection);
        }
        return result;
    }
}
