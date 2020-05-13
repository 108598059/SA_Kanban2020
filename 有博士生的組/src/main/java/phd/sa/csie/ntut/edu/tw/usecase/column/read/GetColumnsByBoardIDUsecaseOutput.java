package phd.sa.csie.ntut.edu.tw.usecase.column.read;


import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

import java.util.ArrayList;
import java.util.List;

public class GetColumnsByBoardIDUsecaseOutput implements UseCaseOutput {
    private List<ColumnViewObject> columnList;

    public GetColumnsByBoardIDUsecaseOutput() {
        this.columnList = new ArrayList<>();
    }

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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getWip() {
            return wip;
        }

        public void setWip(int wip) {
            this.wip = wip;
        }

        public List<CardViewObject> getCardList() {
            return cardList;
        }

        public void setCardList(List<CardViewObject> cardList) {
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
