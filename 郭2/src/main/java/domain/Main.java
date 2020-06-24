package domain;

import domain.adapter.controller.Homepage;
import domain.adapter.repository.board.InMemoryBoardRepository;
import domain.model.DomainEventBus;
import domain.usecase.board.repository.IBoardRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

@WebListener
public class Main implements ServletContextListener {

    private IBoardRepository boardRepository;
    private DomainEventBus eventBus;

    public Main(){
        super();
        boardRepository = new InMemoryBoardRepository();
        eventBus = new DomainEventBus();
    }
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        ServletRegistration.Dynamic servlet = context.addServlet("Homepage",new Homepage(boardRepository, eventBus));
        servlet.addMapping("/homepage");
        servlet.setLoadOnStartup(1);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
