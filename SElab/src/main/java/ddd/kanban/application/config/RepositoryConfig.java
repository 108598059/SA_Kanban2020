package ddd.kanban.application.config;

import ddd.kanban.adapter.repository.board.SqliteBoardRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public BoardRepository boardRepository() {
        return new SqliteBoardRepository();
    }

    @Bean
    public WorkflowRepository workflowRepository() {
        return new InMemoryWorkflowRepository();
    }
}
