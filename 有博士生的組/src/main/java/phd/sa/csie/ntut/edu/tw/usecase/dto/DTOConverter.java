package phd.sa.csie.ntut.edu.tw.usecase.dto;

public interface DTOConverter<E> {

	public DTO toDTO(E entity);

	public E toEntity(DTO dto);

}