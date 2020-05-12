package phd.sa.csie.ntut.edu.tw.usecase.board.dto;

import java.util.ArrayList;

import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Column;
import phd.sa.csie.ntut.edu.tw.usecase.column.dto.ColumnDTO;
import phd.sa.csie.ntut.edu.tw.usecase.column.dto.ColumnDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.dto.DTO;
import phd.sa.csie.ntut.edu.tw.usecase.dto.DTOConverter;

public class BoardDTOConverter implements DTOConverter<Board> {
    
    private ColumnDTOConverter columnDTOConverter = new ColumnDTOConverter();

    @Override
    public DTO toDTO(Board entity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setName(entity.getName());
        ArrayList<ColumnDTO> columnDTOs = new ArrayList<ColumnDTO>();
        for (int i = 0; i < entity.getNumberOfColumns(); i++) {
            ColumnDTO columnDTO = this.columnDTOConverter.toDTO(entity.get(i));
            columnDTOs.add(columnDTO);
        }
        boardDTO.setColumnDTOs(columnDTOs);
        return boardDTO;
    }

    @Override
    public Board toEntity(DTO dto) {
        BoardDTO boardDTO = (BoardDTO) dto;
        // TODO Auto-generated method stub1
        return null;
    }

    

}
