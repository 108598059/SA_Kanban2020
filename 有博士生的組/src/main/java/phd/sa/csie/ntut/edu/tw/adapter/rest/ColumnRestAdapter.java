package phd.sa.csie.ntut.edu.tw.adapter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phd.sa.csie.ntut.edu.tw.usecase.column.read.GetColumnsByBoardIDUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.read.GetColumnsByBoardIDUsecaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.read.GetColumnsByBoardIDUsecaseOutput;

@RestController
@RequestMapping(value = "/api/columns")
public class ColumnRestAdapter {
    @Autowired
    private GetColumnsByBoardIDUseCase getColumnsByBoardIDUseCase;

    @GetMapping
    public ResponseEntity<GetColumnsByBoardIDUsecaseOutput> getColumnsByBoardID(@RequestParam String boardID) {
        GetColumnsByBoardIDUsecaseInput input = new GetColumnsByBoardIDUsecaseInput();
        GetColumnsByBoardIDUsecaseOutput output = new GetColumnsByBoardIDUsecaseOutput();

        input.setBoardID(boardID);

        this.getColumnsByBoardIDUseCase.execute(input, output);

        return ResponseEntity.status(HttpStatus.OK).body(output);
    }
}
