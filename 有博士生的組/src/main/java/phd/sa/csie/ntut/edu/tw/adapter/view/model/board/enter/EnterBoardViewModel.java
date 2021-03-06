package phd.sa.csie.ntut.edu.tw.adapter.view.model.board.enter;

import phd.sa.csie.ntut.edu.tw.adapter.view.model.AbstractViewModel;
import phd.sa.csie.ntut.edu.tw.usecase.board.enter.EnterBoardUseCaseOutput;

import java.util.ArrayList;
import java.util.List;

public class EnterBoardViewModel extends AbstractViewModel {
    private List<ColumnViewObject> columnList;

    public EnterBoardViewModel(EnterBoardUseCaseOutput output) {
        this.columnList = new ArrayList<>();
        for (EnterBoardUseCaseOutput.ColumnViewObject columnViewObject: output.getColumnViewList()) {
            this.columnList.add(new ColumnViewObject(columnViewObject));
        }
    }

    public static class ColumnViewObject {
        private String id;
        private String title;
        private int wip;
        private List<ColumnViewObject.CardViewObject> cardList;

        public ColumnViewObject(EnterBoardUseCaseOutput.ColumnViewObject columnViewObject) {
            this.id = columnViewObject.getID();
            this.title = columnViewObject.getTitle();
            this.wip = columnViewObject.getWIP();
            this.cardList = new ArrayList<>();
            for (EnterBoardUseCaseOutput.ColumnViewObject.CardViewObject cardViewObject:
                    columnViewObject.getCardList()) {
                this.cardList.add(new CardViewObject(cardViewObject));
            }
        }

        public static class CardViewObject {
            private String id;
            private String name;

            public CardViewObject(EnterBoardUseCaseOutput.ColumnViewObject.CardViewObject cardViewObject) {
                this.id = cardViewObject.getID();
                this.name = cardViewObject.getName();
            }

            public String getID() {
                return id;
            }

            public void setID(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public String getID() {
            return id;
        }

        public void setID(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getWIP() {
            return wip;
        }

        public void setWIP(int wip) {
            this.wip = wip;
        }

        public List<ColumnViewObject.CardViewObject> getCardList() {
            return cardList;
        }

        public void setCardList(List<ColumnViewObject.CardViewObject> cardList) {
            this.cardList = cardList;
        }
    }

    public List<ColumnViewObject> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ColumnViewObject> columnList) {
        this.columnList = columnList;
    }
}
