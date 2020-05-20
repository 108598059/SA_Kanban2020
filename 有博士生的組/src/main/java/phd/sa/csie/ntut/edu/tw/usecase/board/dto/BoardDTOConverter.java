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
    public static BoardDTO toDTO(Board board) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setID(board.getID().toString());
        boardDTO.setName(board.getName());
        boardDTO.setWorkspaceID(board.getWorkspaceID().toString());

        ArrayList<ColumnDTO> columnDTOs = new ArrayList<>();
        for (int i = 0; i < board.getColumnNumber(); i++) {
            columnDTOs.add(ColumnDTOConverter.toDTO(board.get(i)));
        }
        boardDTO.setColumnDTOs(columnDTOs);

        return boardDTO;
    }

    public static Board toEntity(BoardDTO boardDTO) {
        List<Column> columns = new ArrayList<>();
        List<ColumnDTO> columnDTOs = boardDTO.getColumnDTOs();
        for (int i = 0; i < columnDTOs.size(); i++) {
            columns.add(ColumnDTOConverter.toEntity(columnDTOs.get(i)));
        }

        return new Board(UUID.fromString(boardDTO.getID()),
                         UUID.fromString(boardDTO.getWorkspaceID()),
                         boardDTO.getName(),
                         columns);
    }
}
