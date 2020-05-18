package phd.sa.csie.ntut.edu.tw.usecase.board.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.board.Column;
import phd.sa.csie.ntut.edu.tw.usecase.column.dto.ColumnDTO;
import phd.sa.csie.ntut.edu.tw.usecase.column.dto.ColumnDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.DTO;

public class BoardDTOConverter {
    public static BoardDTO toDTO(Board entity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setID(entity.getID().toString());
        boardDTO.setName(entity.getName());

        ArrayList<ColumnDTO> columnDTOs = new ArrayList<ColumnDTO>();
        for (int i = 0; i < entity.getColumnNumber(); i++) {
            columnDTOs.add(ColumnDTOConverter.toDTO(entity.get(i)));
        }
        boardDTO.setColumnDTOs(columnDTOs);

        return boardDTO;
    }

    public static Board toEntity(DTO dto) {
        BoardDTO boardDTO = (BoardDTO) dto;

        List<Column> columns = new ArrayList<Column>();
        List<ColumnDTO> columnDTOs = boardDTO.getColumnDTOs();
        for (int i = 0; i < columnDTOs.size(); i++) {
            columns.add(ColumnDTOConverter.toEntity(columnDTOs.get(i)));
        }

        return new Board(UUID.fromString(boardDTO.getID()), boardDTO.getName(), columns);
    }
}
