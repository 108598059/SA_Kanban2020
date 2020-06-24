package phd.sa.csie.ntut.edu.tw.usecase.column.dto;

import phd.sa.csie.ntut.edu.tw.model.board.Column;
import phd.sa.csie.ntut.edu.tw.usecase.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ColumnDTOConverter {
    public static ColumnDTO toDTO(Column entity) {
        ColumnDTO columnDTO = new ColumnDTO();
        columnDTO.setID(entity.getID().toString());
        columnDTO.setTitle(entity.getTitle());
        columnDTO.setWIP(entity.getWIP());

        List<UUID> idList = entity.getCardIDs();
        List<String> idStringList = new ArrayList<>();
        for (UUID id: idList) {
            idStringList.add(id.toString());
        }
        columnDTO.setCardIDs(idStringList);

        List<UUID> preservedPositionIDList = entity.getPreservedPosition();
        List<String> preservedPositionList = new ArrayList<>();
        for (UUID id: preservedPositionIDList) {
            preservedPositionList.add(id.toString());
        }
        columnDTO.setPreservedPosition(preservedPositionList);

        return columnDTO;
    }

    public static Column toEntity(DTO dto) {
        ColumnDTO columnDTO = (ColumnDTO) dto;

        List<String> idStringList = columnDTO.getCardIDs();
        List<UUID> idList = new ArrayList<>();
        for (String id: idStringList) {
            idList.add(UUID.fromString(id));
        }

        List<String> preservedPositionList = columnDTO.getPreservedPosition();
        List<UUID> preservedPositionIDList = new ArrayList<>();
        for (String id: preservedPositionList) {
            preservedPositionIDList.add(UUID.fromString(id));
        }

        return new Column(UUID.fromString(columnDTO.getID()), columnDTO.getTitle(), idList, preservedPositionIDList, columnDTO.getWIP());
    }
}
