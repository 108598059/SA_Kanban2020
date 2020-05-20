package domain.adapter.presenter;

import domain.adapter.view_model.ViewModel;

public interface Presenter<V extends ViewModel> {
    V createView();
}
