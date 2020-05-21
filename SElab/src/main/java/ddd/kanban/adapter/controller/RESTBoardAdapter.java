package ddd.kanban.adapter.controller;

import ddd.kanban.adapter.DTO.BoardDTO;
import ddd.kanban.adapter.presenter.board.create.CreateBoardPresenter;
import ddd.kanban.adapter.presenter.board.get.GetAllBoardsPresenter;
import ddd.kanban.adapter.presenter.board.viewmodel.CreateBoardViewModel;
import ddd.kanban.adapter.presenter.board.viewmodel.GetAllBoardsViewModel;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.usecase.board.create.CreateBoardInput;
import ddd.kanban.usecase.board.create.CreateBoardUseCase;
import ddd.kanban.usecase.board.get.GetAllBoardsInput;
import ddd.kanban.usecase.board.get.GetAllBoardsUseCase;
import ddd.kanban.usecase.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/board", produces = MediaType.APPLICATION_JSON_VALUE)
public class RESTBoardAdapter {
    private DomainEventBus domainEventBus;
    private BoardRepository boardRepository;

    @Autowired
    public RESTBoardAdapter(DomainEventBus domainEventBus, BoardRepository boardRepository) {
        this.domainEventBus = domainEventBus;
        this.boardRepository = boardRepository;
    }

    @PostMapping
    public ResponseEntity<CreateBoardViewModel> createBoard(@RequestBody BoardDTO createBoardInputBody) {

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, domainEventBus);
        CreateBoardInput createBoardInput = new CreateBoardInput(createBoardInputBody.getBoardTitle(), createBoardInputBody.getBoardDescription());
        CreateBoardPresenter createBoardOutput = new CreateBoardPresenter();

        createBoardUseCase.execute(createBoardInput, createBoardOutput);

        return ResponseEntity.ok().body(createBoardOutput.buildCreateBoardViewModel());
    }

    @GetMapping
    public ResponseEntity<List<GetAllBoardsViewModel>> getAllBoards(){
        GetAllBoardsUseCase getAllBoardsUseCase = new GetAllBoardsUseCase(boardRepository);
        GetAllBoardsInput getAllBoardsInput = new GetAllBoardsInput();
        GetAllBoardsPresenter getAllBoardsOutput = new GetAllBoardsPresenter();

        getAllBoardsUseCase.execute(getAllBoardsInput, getAllBoardsOutput);

        return ResponseEntity.ok().body(getAllBoardsOutput.buildGetAllBoardsBoardViewModel());
    }

}
