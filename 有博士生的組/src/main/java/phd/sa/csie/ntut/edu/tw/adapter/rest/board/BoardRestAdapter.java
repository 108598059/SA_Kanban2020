package phd.sa.csie.ntut.edu.tw.adapter.rest.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phd.sa.csie.ntut.edu.tw.adapter.presenter.board.create.CreateBoardPresenter;
import phd.sa.csie.ntut.edu.tw.adapter.view.model.ViewModelStatus;
import phd.sa.csie.ntut.edu.tw.adapter.view.model.board.create.CreateBoardViewModel;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCaseOutput;

@RestController
@RequestMapping(value = "/api/board")
public class BoardRestAdapter {
    @Autowired
    private CreateBoardUseCase createBoardUseCase;

    @PostMapping(value = "/create")
    public ResponseEntity<CreateBoardViewModel> createBoard(@RequestBody CreateBoardRequest requestBody) {
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        CreateBoardPresenter createBoardUseCaseOutput = new CreateBoardPresenter();

        createBoardUseCaseInput.setBoardName(requestBody.getBoardName());
        createBoardUseCaseInput.setWorkspaceID(requestBody.getWorkspaceID());

        this.createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);

        CreateBoardViewModel viewModel = createBoardUseCaseOutput.build();
        viewModel.setStatus(ViewModelStatus.NORMAL);

        return ResponseEntity.status(HttpStatus.OK).body(viewModel);
    }
}


class CreateBoardRequest {
    private String boardName;
    private String workspaceID;

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardName() {
        return this.boardName;
    }

    public String getWorkspaceID() {
        return workspaceID;
    }

    public void setWorkspaceID(String workspaceID) {
        this.workspaceID = workspaceID;
    }
}
