package phd.sa.csie.ntut.edu.tw.usecase.board.dto;

import java.util.List;

import phd.sa.csie.ntut.edu.tw.usecase.column.dto.ColumnDTO;
import phd.sa.csie.ntut.edu.tw.usecase.dto.DTO;

public class BoardDTO extends DTO {

    private String name;
    private List<ColumnDTO> columnDTOs;

    public String getName() {
        return name;
    }

    public List<ColumnDTO> getColumnDTOs() {
        return columnDTOs;
    }

    public void setColumnDTOs(List<ColumnDTO> columnDTOs) {
        this.columnDTOs = columnDTOs;
    }

    public void setName(String name) {
        this.name = name;
    }

}
