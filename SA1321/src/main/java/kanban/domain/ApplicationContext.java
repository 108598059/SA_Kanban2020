package kanban.domain;

import kanban.domain.adapter.repository.board.MySqlBoardRepository;
import kanban.domain.usecase.board.create.CreateBoardUseCase;
import kanban.domain.usecase.board.get.GetBoardsUseCase;
import kanban.domain.usecase.board.repository.IBoardRepository;

public class ApplicationContext {

    private static ApplicationContext applicationContext;
    private IBoardRepository boardRepository;

    public ApplicationContext() {
        boardRepository = new MySqlBoardRepository();

    }

    public static ApplicationContext getInstance() {
        if(applicationContext == null) {
            applicationContext = new ApplicationContext();
        }

        return applicationContext;
    }

    public CreateBoardUseCase getCreateBoardUseCase() {
        return new CreateBoardUseCase(boardRepository);
    }

    public GetBoardsUseCase getGetBoardsUseCase() {
        return new GetBoardsUseCase(boardRepository);
    }
}
