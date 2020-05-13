package phd.sa.csie.ntut.edu.tw.usecase.dto;

import java.util.UUID;

public abstract class DTO {

	private String id;

	public String getId() {
		return this.id;
	};

	public void setId(String id) {
		this.id = id;
	}

}