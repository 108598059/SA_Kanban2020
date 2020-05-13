package domain.view;

import domain.adapters.View;
import domain.adapters.controller.board.BoardController;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements View, ActionListener{

    private JButton boardCreate = new JButton("createBoard");
    private JPanel buttonListContainer = new JPanel();
    private JPanel boardListContainer = new JPanel();
    private JList<String> boardList = new JList<String>();
    private JTextField boardNameInput = new JTextField(20);
    private DefaultListModel<String> model = new DefaultListModel<String>();

    private BoardController boardController;

    public MainFrame(BoardController boardController){
        super();

        this.boardController = boardController;

        initialWindowsProperty();
        initialLayout();


        boardCreate.addActionListener(this);

    }

    private void initialWindowsProperty(){
        this.setSize(800,600);
        this.setVisible(true);
        this.setTitle("KanBan");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initialLayout(){
        this.getContentPane().setLayout(new BorderLayout());
        buttonListContainer.setLayout(new FlowLayout());
        boardListContainer.setLayout(new FlowLayout());

        this.add(buttonListContainer,BorderLayout.EAST);
        this.add(boardListContainer,BorderLayout.WEST);

        buttonListContainer.add(boardNameInput);
        buttonListContainer.add(boardCreate);
        boardListContainer.add(boardList);

        boardList.setFixedCellWidth(100);
        boardList.setFixedCellHeight(20);
    }


    public void addBoard(String boardName){

        model.addElement(boardName);
        boardList.setModel(model);

    }


    public void actionPerformed(ActionEvent e) {
        if(!boardNameInput.getText().equals(""));
            boardController.handleCreateBoard(this,boardNameInput.getText());
    }
}
