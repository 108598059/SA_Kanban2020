package ddd.kanban.domain.model.workflow;

import java.util.ArrayList;
import java.util.List;

public class Column extends Lane {

    public Column(String id, String title, String workflowId){
        super(id, title, workflowId);
    }
}
