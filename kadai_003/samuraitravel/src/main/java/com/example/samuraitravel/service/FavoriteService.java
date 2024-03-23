package com.example.samuraitravel.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;
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
	public void create(FavoriteRegisterForm favoriteRegisterForm, House house, User user) {
		Favorite favorite = new Favorite();
		
		favoriteRegisterForm.setHouseId(house.getId());
		favoriteRegisterForm.setUserId(user.getId());
		
		favorite.setHouse(houseRepository.getReferenceById(favoriteRegisterForm.getHouseId()));
		favorite.setUser(userRepository.getReferenceById(favoriteRegisterForm.getUserId()));
		
		favoriteRepository.save(favorite);
	}
	
	@Transactional
	public boolean isFavoritedHouseAndFavoritedUser(House house, User user) {
		Favorite favoriteHouse = favoriteRepository.getByHouse(house);
		Favorite favoriteUser = favoriteRepository.getByUser(user);
		
		Map<Favorite, Favorite> map = new HashMap<>();
		map.put(favoriteHouse, favoriteUser);
		
		return map != null;
	}
	
	/*public boolean isFavoritedUser(Integer userId) {
		User user = favoriteRepository.getUserId(userId);
		return user != null;
	}*/
	
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