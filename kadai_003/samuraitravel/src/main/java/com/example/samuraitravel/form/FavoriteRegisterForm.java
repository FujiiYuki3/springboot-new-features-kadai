package com.example.samuraitravel.form;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteRegisterForm {
	@NotNull
	private Integer houseId;
	
	@NotNull
	private Integer userId;
	
	public House getHouse(House house) {
		return house;
	}
	
	public void setHouseId(House house) {
		this.houseId = house.getId();
	}
	
	public User getUser(User user) {
		return user;
	}
	
	public void setUserId(User user) {
		this.userId = user.getId();
	}

}
