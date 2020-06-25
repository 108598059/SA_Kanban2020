package kanban.domain.adapter.presenter.card.cycleTime;

import kanban.domain.usecase.card.cycleTime.CalculateCycleTimeOutput;
import kanban.domain.usecase.card.cycleTime.CycleTimeModel;

public class CalculateCycleTimePresenter implements CalculateCycleTimeOutput {
    private CycleTimeModel cycleTimeModel;

    @Override
    public void setCycleTimeModel(CycleTimeModel cycleTimeModel) {
        this.cycleTimeModel = cycleTimeModel;
    }

    @Override
    public CycleTimeModel getCycleTimeModel() {
        return cycleTimeModel;
    }
}
