package phd.sa.csie.ntut.edu.tw.usecase.dto;

import java.util.UUID;

public abstract class DTO {

	private UUID id;

	public UUID getId() {
		return this.id;
	};

	public void setId(UUID id) {
		this.id = id;
	}

}