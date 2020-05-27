package phd.sa.csie.ntut.edu.tw.adapter.rest.column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phd.sa.csie.ntut.edu.tw.usecase.column.read.GetColumnsByBoardIDUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.read.GetColumnsByBoardIDUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.read.GetColumnsByBoardIDUseCaseOutput;

@RestController
@RequestMapping(value = "/api/columns")
public class ColumnRestAdapter {
    @Autowired
    private GetColumnsByBoardIDUseCase getColumnsByBoardIDUseCase;

    @GetMapping
    public ResponseEntity<ColumnResponseBody> getColumnsByBoardID(@RequestParam String boardID) {
        GetColumnsByBoardIDUseCaseInput getColumnsByBoardIDUseCaseInput = new GetColumnsByBoardIDUseCaseInput();
        GetColumnsByBoardIDUseCaseOutput getColumnsByBoardIDUseCaseOutput = new GetColumnsByBoardIDUseCaseOutput();

        getColumnsByBoardIDUseCaseInput.setBoardID(boardID);

        this.getColumnsByBoardIDUseCase.execute(getColumnsByBoardIDUseCaseInput, getColumnsByBoardIDUseCaseOutput);

        return ResponseEntity.status(HttpStatus.OK).body(new ColumnResponseBody(getColumnsByBoardIDUseCaseOutput));
    }
}
