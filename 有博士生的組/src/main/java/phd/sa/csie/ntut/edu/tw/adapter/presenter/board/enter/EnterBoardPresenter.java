package phd.sa.csie.ntut.edu.tw.adapter.presenter.board.enter;

import phd.sa.csie.ntut.edu.tw.adapter.view.model.board.enter.EnterBoardViewModel;
import phd.sa.csie.ntut.edu.tw.usecase.board.enter.EnterBoardUseCaseOutput;

import java.util.ArrayList;
import java.util.List;

public class EnterBoardPresenter implements EnterBoardUseCaseOutput {
    private List<ColumnViewObject> columnViewList;

    public EnterBoardPresenter() {
        this.columnViewList = new ArrayList<>();
    }

    public EnterBoardViewModel build() {
        return new EnterBoardViewModel(this);
    }

    @Override
    public List<ColumnViewObject> getColumnViewList() {
        return columnViewList;
    }

    @Override
    public void setColumnViewList(List<ColumnViewObject> columnViewList) {
        this.columnViewList = columnViewList;
    }
}
