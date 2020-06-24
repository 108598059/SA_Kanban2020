package phd.sa.csie.ntut.edu.tw.adapter.rest.column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phd.sa.csie.ntut.edu.tw.adapter.presenter.board.enter.EnterBoardPresenter;
import phd.sa.csie.ntut.edu.tw.adapter.view.model.ViewModelStatus;
import phd.sa.csie.ntut.edu.tw.adapter.view.model.board.enter.EnterBoardViewModel;
import phd.sa.csie.ntut.edu.tw.usecase.board.enter.EnterBoardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.enter.EnterBoardUseCaseInput;

@RestController
@RequestMapping(value = "/api/columns")
public class ColumnRestAdapter {
    @Autowired
    private EnterBoardUseCase enterBoardUseCase;

    @GetMapping
    public ResponseEntity<EnterBoardViewModel> getColumnsByBoardID(@RequestParam String boardID) {
        EnterBoardUseCaseInput enterBoardUseCaseInput = new EnterBoardUseCaseInput();
        EnterBoardPresenter getColumnsByBoardIDUseCaseOutput = new EnterBoardPresenter();

        enterBoardUseCaseInput.setBoardID(boardID);

        this.enterBoardUseCase.execute(enterBoardUseCaseInput, getColumnsByBoardIDUseCaseOutput);

        EnterBoardViewModel viewModel = getColumnsByBoardIDUseCaseOutput.build();
        viewModel.setStatus(ViewModelStatus.NORMAL);
        return ResponseEntity.status(HttpStatus.OK).body(viewModel);
    }
}
