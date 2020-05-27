package phd.sa.csie.ntut.edu.tw.adapter.rest.column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phd.sa.csie.ntut.edu.tw.adapter.presenter.column.read.GetColumnsByBoardIDPresenter;
import phd.sa.csie.ntut.edu.tw.adapter.view.model.ViewModelStatus;
import phd.sa.csie.ntut.edu.tw.adapter.view.model.column.ColumnViewModel;
import phd.sa.csie.ntut.edu.tw.usecase.column.read.GetColumnsByBoardIDUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.read.GetColumnsByBoardIDUseCaseInput;

@RestController
@RequestMapping(value = "/api/columns")
public class ColumnRestAdapter {
    @Autowired
    private GetColumnsByBoardIDUseCase getColumnsByBoardIDUseCase;

    @GetMapping
    public ResponseEntity<ColumnViewModel> getColumnsByBoardID(@RequestParam String boardID) {
        GetColumnsByBoardIDUseCaseInput getColumnsByBoardIDUseCaseInput = new GetColumnsByBoardIDUseCaseInput();
        GetColumnsByBoardIDPresenter getColumnsByBoardIDUseCaseOutput = new GetColumnsByBoardIDPresenter();

        getColumnsByBoardIDUseCaseInput.setBoardID(boardID);

        this.getColumnsByBoardIDUseCase.execute(getColumnsByBoardIDUseCaseInput, getColumnsByBoardIDUseCaseOutput);

        ColumnViewModel viewModel = getColumnsByBoardIDUseCaseOutput.build();
        viewModel.setStatus(ViewModelStatus.NORMAL);
        return ResponseEntity.status(HttpStatus.OK).body(viewModel);
    }
}
