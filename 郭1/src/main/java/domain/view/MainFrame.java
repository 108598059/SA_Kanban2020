package domain.view;

import domain.adapters.View;
import domain.adapters.controller.board.BoardController;
import domain.adapters.viewmodel.board.BoardViewModel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame implements View, ActionListener{

    private JButton boardCreate = new JButton("createBoard");
    private JPanel buttonListContainer = new JPanel();
    private JPanel boardListContainer = new JPanel();
    private JList<String> boardList = new JList<String>();
    private JTextField boardNameInput = new JTextField(20);
    private DefaultListModel<String> model = new DefaultListModel<String>();
    private JLabel board = new JLabel();

    private BoardViewModel viewModel;
    private BoardController boardController;

    public MainFrame(BoardController boardController, BoardViewModel viewModel){
        super();

        initialWindowsProperty();
        initialComponents();
        initialLayout();


        this.viewModel = viewModel;
        this.boardController = boardController;

        boardCreate.addActionListener(this);


    }

    private void initialComponents() {

        boardList.setFixedCellWidth(100);
        boardList.setFixedCellHeight(20);


        board.setText("BoardList");

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
        boardListContainer.setLayout(new BoxLayout(boardListContainer,BoxLayout.PAGE_AXIS));

        this.add(buttonListContainer,BorderLayout.EAST);
        this.add(boardListContainer,BorderLayout.WEST);

        buttonListContainer.add(boardNameInput);
        buttonListContainer.add(boardCreate);
        boardListContainer.add(board);
        boardListContainer.add(boardList);

        board.setAlignmentX(Component.LEFT_ALIGNMENT);
        boardList.setAlignmentX(Component.LEFT_ALIGNMENT);
    }



    public void updateUI(){

        model.clear();
        List<String> boardNameList = viewModel.getBoardNameList();
        for (String boardName : boardNameList){
            model.addElement(boardName);
        }
        boardList.setModel(model);
    }


    public void actionPerformed(ActionEvent e) {
        if(!boardNameInput.getText().equals("")){
            boardController.createBoard(boardNameInput.getText());
            updateUI();
        }
    }
}
