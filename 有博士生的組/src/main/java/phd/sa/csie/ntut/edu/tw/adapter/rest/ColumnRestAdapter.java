package phd.sa.csie.ntut.edu.tw.adapter.rest;

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
    public ResponseEntity<GetColumnsByBoardIDUseCaseOutput> getColumnsByBoardID(@RequestParam String boardID) {
        GetColumnsByBoardIDUseCaseInput input = new GetColumnsByBoardIDUseCaseInput();
        GetColumnsByBoardIDUseCaseOutput output = new GetColumnsByBoardIDUseCaseOutput();

        input.setBoardID(boardID);

        this.getColumnsByBoardIDUseCase.execute(input, output);

        return ResponseEntity.status(HttpStatus.OK).body(output);
    }
}
