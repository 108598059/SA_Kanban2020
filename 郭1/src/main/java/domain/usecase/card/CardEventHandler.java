package domain.usecase.card;

import com.google.common.eventbus.Subscribe;
import domain.controller.CommitCardInputImpl;
import domain.controller.CommitCardOutputImpl;
import domain.entity.card.Card;
import domain.entity.card.event.CardCreated;
import domain.entity.workflow.Workflow;
import domain.usecase.workflow.WorkflowRepository;
import domain.usecase.workflow.commit.CommitCardInput;
import domain.usecase.workflow.commit.CommitCardOutput;
import domain.usecase.workflow.commit.CommitCardUseCase;

public class CardEventHandler{

    private WorkflowRepository workflowRepository;

    public CardEventHandler(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    @Subscribe
    public void commitCardHandleEvent(CardCreated cardCreated){

        CommitCardInput commitCardInput = new CommitCardInputImpl();
        CommitCardOutput commitCardOutput = new CommitCardOutputImpl();
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository);

        commitCardInput.setWorkflowId(cardCreated.getWorkflowId());
        commitCardInput.setStageId(cardCreated.getStageId());
        commitCardInput.setSwimlaneId(cardCreated.getSwimlaneId());
        commitCardInput.setCardId(cardCreated.getCardId());


        commitCardUseCase.execute(commitCardInput,commitCardOutput);

    }
}
