package phd.sa.csie.ntut.edu.tw.adapter.view.model;

public abstract class AbstractViewModel implements ViewModel {
    private ViewModelStatus status;

    @Override
    public ViewModelStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(ViewModelStatus status) {
        this.status = status;
    }
}
