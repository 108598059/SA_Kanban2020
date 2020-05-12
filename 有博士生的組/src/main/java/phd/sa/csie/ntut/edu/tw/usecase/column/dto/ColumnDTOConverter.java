package phd.sa.csie.ntut.edu.tw.usecase.column.dto;

import phd.sa.csie.ntut.edu.tw.domain.model.board.Column;
import phd.sa.csie.ntut.edu.tw.usecase.dto.DTO;
import phd.sa.csie.ntut.edu.tw.usecase.dto.DTOConverter;

public class ColumnDTOConverter implements DTOConverter<Column> {

    @Override
    public DTO toDTO(Column entity) {
        ColumnDTO columnDTO = new ColumnDTO();
        columnDTO.setId(entity.getId());
        columnDTO.setTitle(entity.getTitle());
        columnDTO.setWip(entity.getWIP());
        columnDTO.setCardIds(entity.getCardIds());
        return columnDTO;
    }

    @Override
    public Column toEntity(DTO dto) {
        ColumnDTO columnDTO = (ColumnDTO) dto;
        // TODO Auto-generated method stub
        return null;
    }
    
}
