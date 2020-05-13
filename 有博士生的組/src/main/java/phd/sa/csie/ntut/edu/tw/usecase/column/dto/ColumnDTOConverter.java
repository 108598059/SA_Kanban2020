package phd.sa.csie.ntut.edu.tw.usecase.column.dto;

import phd.sa.csie.ntut.edu.tw.domain.model.board.Column;
import phd.sa.csie.ntut.edu.tw.usecase.dto.DTO;
import phd.sa.csie.ntut.edu.tw.usecase.dto.DTOConverter;

import java.util.UUID;

public class ColumnDTOConverter implements DTOConverter<Column> {

    @Override
    public ColumnDTO toDTO(Column entity) {
        ColumnDTO columnDTO = new ColumnDTO();
        columnDTO.setId(entity.getId().toString());
        columnDTO.setTitle(entity.getTitle());
        columnDTO.setWip(entity.getWIP());
        columnDTO.setCardIds(entity.getCardIds());
        return columnDTO;
    }

    @Override
    public Column toEntity(DTO dto) {
        ColumnDTO columnDTO = (ColumnDTO) dto;
        Column column = new Column(UUID.fromString(columnDTO.getId()), columnDTO.getTitle(), columnDTO.getCardIds(), columnDTO.getWip());
        return column;
    }
    
}
