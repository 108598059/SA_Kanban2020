package domain.usecase.card;

import com.google.common.eventbus.Subscribe;
import domain.adapters.controller.card.CommitCardInputImpl;
import domain.adapters.controller.card.CommitCardOutputImpl;
import domain.entity.DomainEventBus;
import domain.entity.card.event.CardCreated;
import domain.usecase.flowevent.FlowEventRepository;
import domain.usecase.workflow.WorkflowRepository;
import domain.usecase.workflow.commit.CommitCardInput;
import domain.usecase.workflow.commit.CommitCardOutput;
import domain.usecase.workflow.commit.CommitCardUseCase;

public class CardEventHandler{
    private FlowEventRepository flowEventRepository;
    private WorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public CardEventHandler(FlowEventRepository flowEventRepository, WorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.flowEventRepository = flowEventRepository;
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    @Subscribe
    public void commitCardHandleEvent(CardCreated cardCreated){

        CommitCardInput commitCardInput = new CommitCardInputImpl();
        CommitCardOutput commitCardOutput = new CommitCardOutputImpl();
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(flowEventRepository, workflowRepository, eventBus);

        commitCardInput.setWorkflowId(cardCreated.getWorkflowId());
        commitCardInput.setStageId(cardCreated.getStageId());
        commitCardInput.setSwimlaneId(cardCreated.getSwimlaneId());
        commitCardInput.setCardId(cardCreated.getCardId());


        commitCardUseCase.execute(commitCardInput,commitCardOutput);

    }
}
