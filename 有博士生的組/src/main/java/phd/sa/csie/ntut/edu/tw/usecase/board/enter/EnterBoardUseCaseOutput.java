package phd.sa.csie.ntut.edu.tw.usecase.board.enter;


import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

import java.util.ArrayList;
import java.util.List;

public interface EnterBoardUseCaseOutput extends UseCaseOutput {
    public static class ColumnViewObject {
        private String id;
        private String title;
        private int wip;
        private List<CardViewObject> cardList;

        public ColumnViewObject() {
            this.cardList = new ArrayList<>();
        }

        public static class CardViewObject {
            private String id;
            private String name;

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

        public List<CardViewObject> getCardList() {
            return cardList;
        }

        public void setCardList(List<CardViewObject> cardList) {
            this.cardList = cardList;
        }
    }

    public List<ColumnViewObject> getColumnViewList();

    public void setColumnViewList(List<ColumnViewObject> columnViewList);
}
