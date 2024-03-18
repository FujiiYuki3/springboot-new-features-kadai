package com.example.samuraitravel.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteRegisterForm {
	@NotNull
	private Integer houseId;
	
	@NotNull
	private Integer userId;
	
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
