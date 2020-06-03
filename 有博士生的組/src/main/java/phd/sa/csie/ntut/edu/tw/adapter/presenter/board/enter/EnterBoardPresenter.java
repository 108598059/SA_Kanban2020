package phd.sa.csie.ntut.edu.tw.adapter.presenter.board.enter;

import phd.sa.csie.ntut.edu.tw.adapter.view.model.board.enter.EnterBoardViewModel;
import phd.sa.csie.ntut.edu.tw.usecase.board.enter.EnterBoardUseCaseOutput;

import java.util.ArrayList;
import java.util.List;

public class EnterBoardPresenter implements EnterBoardUseCaseOutput {
    private List<ColumnViewObject> columnList;

    public EnterBoardPresenter() {
        this.columnList = new ArrayList<>();
    }

    public EnterBoardViewModel build() {
        return new EnterBoardViewModel(this);
    }

    @Override
    public List<ColumnViewObject> getColumnList() {
        return columnList;
    }

    @Override
    public void setColumnList(List<ColumnViewObject> columnList) {
        this.columnList = columnList;
    }
}
