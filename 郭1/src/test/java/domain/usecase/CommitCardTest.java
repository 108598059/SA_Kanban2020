package domain.usecase;

import domain.adapter.BoardRepositoryImpl;
import domain.adapter.CardRepositoryImpl;
import domain.adapter.WorkflowRepositoryImpl;
import domain.controller.*;
import domain.usecase.board.BoardRepository;
import domain.usecase.board.create.CreateBoardInput;
import domain.usecase.board.create.CreateBoardOutput;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.card.CardRepository;
import domain.usecase.card.create.CreateCardInput;
import domain.usecase.card.create.CreateCardOutput;
import domain.usecase.card.create.CreateCardUseCase;
import domain.usecase.workflow.WorkflowRepository;
import domain.usecase.workflow.create.CreateWorkflowInput;
import domain.usecase.workflow.create.CreateWorkflowOutput;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CommitCardTest {
//
//    private String cardId;
//    private String workflowId;
//    private CardRepository cardRepository;
//    private WorkflowRepository workflowRepository;
//
//    @Before
//    public void setUp(){
//        workflowRepository = new WorkflowRepositoryImpl();
//
//        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository);
//        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInputImpl();
//        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutputImpl();
//
//        createWorkflowInput.setWorkflowName("workflow1");
//
//        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);
//        String workflowId = createWorkflowOutput.getWorkflowId();
//
//        CardRepository cardRepository = new CardRepositoryImpl();
//        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository);
//        CreateCardInput CreateCardInput = new CreateCardInputImpl();
//        CreateCardOutput CreateCardOutput = new CreateCardOutputImpl();
//
//
//        CreateCardInput.setCardName( "card1" ) ;
//        createCardUseCase.execute( CreateCardInput, CreateCardOutput ) ;
//
//        cardId = CreateCardOutput.getCardId();
//    }
//
//    @Test
//    public void commitCard(){
//        CommitCardInput commitCardInput = new CommitCardInputImpl();
//        CommitCardOutput commitCardOutput = new CommitCardOutputImpl();
//        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository,cardRepository);
//
//        commitCardInput.setWorkflowId(workflowId);
//        commitCardInput.setCardId(cardId);
//
//        commitCardUseCase.execute(commitCardInput,commitCardOutput);
//
//        assertEquals(commitCardOutput.getXXX());
//    }
}
