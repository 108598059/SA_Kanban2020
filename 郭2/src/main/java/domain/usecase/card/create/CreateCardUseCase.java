package domain.usecase.card.create;

import domain.aggregate.card.Card;
import domain.aggregate.workflow.Workflow;
import domain.usecase.card.repository.ICardRepository;
import domain.usecase.workflow.repository.IWorkflowRepository;


public class CreateCardUseCase {
    private ICardRepository cardRepository;
    private IWorkflowRepository workflowRepository;

    public CreateCardUseCase(IWorkflowRepository workflowRepository, ICardRepository cardRepository){
        this.workflowRepository = workflowRepository;
        this.cardRepository = cardRepository;
    }
    public void execute(CreateCardUseCaseInput createCardUseCaseInput, CreateCardUseCaseOutput createCardUseCaseOutput) {
        Card card = new Card(createCardUseCaseInput.getCardName());
        Workflow workflow = workflowRepository.getWorkflowById(createCardUseCaseInput.getWorkflowId());
        workflow.addCardInLane(createCardUseCaseInput.getStageId(), card.getCardId());

        cardRepository.add(card);
        workflowRepository.save(workflow);

        createCardUseCaseOutput.setCardName(card.getCardName());
        createCardUseCaseOutput.setCardId(card.getCardId());
    }
}
