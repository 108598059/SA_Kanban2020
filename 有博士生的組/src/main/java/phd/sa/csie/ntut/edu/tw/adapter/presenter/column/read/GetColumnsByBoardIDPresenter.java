package phd.sa.csie.ntut.edu.tw.adapter.presenter.column.read;

import phd.sa.csie.ntut.edu.tw.adapter.view.model.column.ColumnViewModel;
import phd.sa.csie.ntut.edu.tw.usecase.column.read.GetColumnsByBoardIDUseCaseOutput;

import java.util.ArrayList;
import java.util.List;

public class GetColumnsByBoardIDPresenter implements GetColumnsByBoardIDUseCaseOutput {
    private List<ColumnViewObject> columnList;

    public GetColumnsByBoardIDPresenter() {
        this.columnList = new ArrayList<>();
    }

    public ColumnViewModel build() {
        return new ColumnViewModel(this);
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
