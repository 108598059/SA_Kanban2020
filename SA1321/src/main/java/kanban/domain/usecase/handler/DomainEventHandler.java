package kanban.domain.usecase.handler;

import com.google.common.eventbus.Subscribe;
import kanban.domain.adapter.presenter.workflow.commit.CommitWorkflowPresenter;
import kanban.domain.adapter.presenter.card.commit.CommitCardPresenter;
import kanban.domain.model.DomainEvent;
import kanban.domain.model.DomainEventBus;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.domainEvent.repository.IDomainEventRepository;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;

public class DomainEventHandler {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;
    private IDomainEventRepository domainEventRepository;

    public DomainEventHandler(IDomainEventRepository domainEventRepository) {
        this.domainEventRepository = domainEventRepository;

    }

    @Subscribe
    public void handleEvent(DomainEvent domainEvent) {
        domainEventRepository.save(domainEvent);
    }
}
