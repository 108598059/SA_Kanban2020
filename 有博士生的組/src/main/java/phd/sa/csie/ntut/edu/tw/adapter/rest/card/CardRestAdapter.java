package phd.sa.csie.ntut.edu.tw.adapter.rest.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phd.sa.csie.ntut.edu.tw.adapter.view.model.ViewModelStatus;
import phd.sa.csie.ntut.edu.tw.adapter.view.model.card.create.CreateCardViewModel;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.adapter.presenter.card.create.CreateCardPresenter;

@RestController
@RequestMapping("/api/card")
public class CardRestAdapter {
    @Autowired
    private CreateCardUseCase createCardUseCase;

    @PostMapping("/create")
    public ResponseEntity<CreateCardViewModel> createCard(@RequestBody CreateCardRequest requestBody){
        CreateCardUseCaseInput createCardInput = new CreateCardUseCaseInput();
        CreateCardPresenter createCardOutput = new CreateCardPresenter();

        createCardInput.setBoardID(requestBody.getBoardID());
        createCardInput.setCardName(requestBody.getCardName());

        try {
            this.createCardUseCase.execute(createCardInput, createCardOutput);

            CreateCardViewModel viewModel = createCardOutput.build();
            viewModel.setStatus(ViewModelStatus.NORMAL);
            return ResponseEntity.status(HttpStatus.OK).body(viewModel);
        } catch (IllegalArgumentException e) {
            CreateCardViewModel viewModel = createCardOutput.build();
            viewModel.setStatus(ViewModelStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(viewModel);
        }
    }
}

class CreateCardRequest {
    private String cardName;
    private String boardID;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }
}
