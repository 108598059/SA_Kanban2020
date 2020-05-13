package phd.sa.csie.ntut.edu.tw.usecase.column.dto;

import phd.sa.csie.ntut.edu.tw.domain.model.board.Column;
import phd.sa.csie.ntut.edu.tw.usecase.dto.DTO;
import phd.sa.csie.ntut.edu.tw.usecase.dto.DTOConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ColumnDTOConverter implements DTOConverter<Column> {

    @Override
    public ColumnDTO toDTO(Column entity) {
        ColumnDTO columnDTO = new ColumnDTO();
        columnDTO.setId(entity.getId().toString());
        columnDTO.setTitle(entity.getTitle());
        columnDTO.setWip(entity.getWIP());
        List<UUID> idList = entity.getCardIds();
        List<String> idStringList = new ArrayList<>();
        for (UUID id: idList) {
            idStringList.add(id.toString());
        }
        columnDTO.setCardIds(idStringList);
        return columnDTO;
    }

    @Override
    public Column toEntity(DTO dto) {
        ColumnDTO columnDTO = (ColumnDTO) dto;
        List<String> idStringList = columnDTO.getCardIds();
        List<UUID> idList = new ArrayList<>();
        for (String id: idStringList) {
            idList.add(UUID.fromString(id));
        }

        Column column = new Column(UUID.fromString(columnDTO.getId()), columnDTO.getTitle(), idList, columnDTO.getWip());
        return column;
    }
    
}
