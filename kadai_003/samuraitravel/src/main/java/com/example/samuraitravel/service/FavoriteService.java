package com.example.samuraitravel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.form.FavoriteRegisterForm;
import com.example.samuraitravel.repository.FavoriteRepository;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.UserRepository;

@Service
public class FavoriteService {
	private final FavoriteRepository favoriteRepository;
	private final UserRepository userRepository;
	private final HouseRepository houseRepository;
	
	public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository, HouseRepository houseRepository) {
		this.favoriteRepository = favoriteRepository;
		this.userRepository = userRepository;
		this.houseRepository = houseRepository;
	}
	
	@Transactional
	public void create(FavoriteRegisterForm favoriteRegisterForm) {
		Favorite favorite = new Favorite();
		/*House house = houseRepository.getReferenceById(favoriteRegisterForm.getHouseId());
		User user = userRepository.getReferenceById(favoriteRegisterForm.getUserId());
		
		favoriteRegisterForm.setHouseId(house);
		favoriteRegisterForm.setUserId(user);*/
		
		favoriteRepository.save(favorite);
	}
	
	/*@Transactional
	public boolean cancelFavorite(FavoriteForm favoriteForm) {
		if(favoriteRepository.existsByUserIdAndHouseId(favoriteForm.getUser(), favoriteForm.getHouse())) {
			favoriteRepository.deleteByUserIdAndHouseId(favoriteForm.getUser(), favoriteForm.getHouse());
			return true;
		}
		return false;
	}
	
	public boolean wasFavorite(FavoriteForm favoriteForm) {
		if(create(favoriteForm) == true) {
			return favoriteRepository.existsByUserIdAndHouseId(favoriteForm.getUser(), favoriteForm.getHouse());
		}
		
		return false;
	}*/

}