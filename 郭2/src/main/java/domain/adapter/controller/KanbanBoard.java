package domain.adapter.controller;

import domain.usecase.board.repository.IBoardRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class KanbanBoard extends HttpServlet {

    private IBoardRepository boardRepository;

    public KanbanBoard(IBoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(boardRepository.toString());
    }
}
