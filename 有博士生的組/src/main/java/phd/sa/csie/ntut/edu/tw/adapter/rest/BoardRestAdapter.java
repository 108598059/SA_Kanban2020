package phd.sa.csie.ntut.edu.tw.adapter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCaseOutput;

@RestController
@RequestMapping(value = "/api/board")
public class BoardRestAdapter {
    @Autowired
    private CreateBoardUseCase createBoardUseCase;

    @PostMapping(value = "/create")
    public ResponseEntity<CreateBoardResponse> createBoard(@RequestBody CreateBoardRequest requestBody) {
        CreateBoardUseCaseInput input = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutput output = new CreateBoardUseCaseOutput();

        input.setBoardName(requestBody.getBoardName());

        this.createBoardUseCase.execute(input, output);

        CreateBoardResponse responseBody = new CreateBoardResponse();
        responseBody.setBoardID(output.getBoardID());
        responseBody.setBoardName(output.getBoardName());

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}


class CreateBoardRequest {
    private String boardName;

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardName() {
        return this.boardName;
    }
}

class CreateBoardResponse {
    private String boardID;
    private String boardName;

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

    public String getBoardID() {
        return this.boardID;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardName() {
        return this.boardName;
    }
}
