package kanban.domain.adapter.controller.board;


import kanban.domain.adapter.repository.board.MySqlBoardRepository;
import kanban.domain.usecase.board.get.GetBoardsByUserIdInput;
import kanban.domain.usecase.board.get.GetBoardsByUserIdUseCase;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class GetBoardsByUserIdRESTfulAPI {
    @Path("/{userId}/boards")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBoard(@PathParam("userId") String userId){

        GetBoardsByUserIdUseCase getBoardsByUserIdUseCase = new GetBoardsByUserIdUseCase(new MySqlBoardRepository());
        GetBoardsByUserIdInput input = new GetBoardsByUserIdInput();
        GetBoardsByUserIdPresenter presenter = new GetBoardsByUserIdPresenter();

        input.setUserId("1");
        getBoardsByUserIdUseCase.execute(input, presenter);

        return Response.status(Response.Status.OK).entity(presenter.build()).build();
    }
}
