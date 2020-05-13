package ddd.kanban.spring.controller;



import ddd.kanban.adapter.DTO.BoardDTO;
import ddd.kanban.adapter.controller.BoardController;
import ddd.kanban.adapter.repository.board.SqliteBoardRepository;
import ddd.kanban.domain.model.DomainEventBus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/board", produces = MediaType.APPLICATION_JSON_VALUE)
public class SpringBoardController {


    private final  BoardController boardController = new BoardController(new SqliteBoardRepository(), new DomainEventBus());

    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO request) {
        BoardDTO output = boardController.createBoard(request);
        return ResponseEntity.ok().body(output);
    }
}
