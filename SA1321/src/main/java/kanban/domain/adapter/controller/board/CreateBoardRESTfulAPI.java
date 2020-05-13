package kanban.domain.adapter.controller.board;

import kanban.domain.adapter.repository.board.MySqlBoardRepository;
import kanban.domain.usecase.board.create.CreateBoardInput;
import kanban.domain.usecase.board.create.CreateBoardOutput;
import kanban.domain.usecase.board.create.CreateBoardUseCase;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class CreateBoardRESTfulAPI {
    @Path("/{userId}/boards")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBoard(@PathParam("userId") String userId, String boardInfo){
        String boardName = "";
        try {
            JSONObject jsonObject = new JSONObject(boardInfo);
            boardName = jsonObject.getString("name");
        }catch(JSONException e){
            e.printStackTrace();
        }

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(new MySqlBoardRepository());
        CreateBoardInput input = new CreateBoardInput();
        input.setUserId(userId);
        CreateBoardOutput output = new CreateBoardOutput();

        input.setBoardName(boardName);
        createBoardUseCase.execute(input, output);
        return Response.status(Response.Status.CREATED).entity(output).build();
    }
}
