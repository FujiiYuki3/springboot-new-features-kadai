package com.example.samuraitravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.FavoriteRegisterForm;
import com.example.samuraitravel.form.ReservationInputForm;
import com.example.samuraitravel.repository.FavoriteRepository;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.FavoriteService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/houses")
public class HouseController {
	private final HouseRepository houseRepository;
	private final FavoriteService favoriteService;
	
	public HouseController(HouseRepository houseRepository, FavoriteService favoriteService/*, UserRepository userRepository, ReviewRepository reviewRepository*/) {
		this.houseRepository = houseRepository;
		this.favoriteService = favoriteService;
	}
	
	@GetMapping
	public String index( @RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "area", required = false) String area,
			@RequestParam(name = "price", required = false) Integer price,
			@RequestParam(name = "order", required = false) String order,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model)
	{
		Page<House> housePage;
		
		if(keyword != null && !keyword.isEmpty()) {
			if(order != null && order.equals("priceAsc")) {
				housePage = houseRepository.findByNameLikeOrAddressLikeOrderByPriceAsc("%" + keyword + "%", "%" + keyword + "%", pageable);
			}else {
				housePage = houseRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%", "%" + keyword + "%", pageable);
			}
		} else if (area != null && !area.isEmpty()) {
			if(order != null && order.equals("priceAsc")) {
				housePage = houseRepository.findByAddressLikeOrderByPriceAsc("%" + area + "%", pageable);
			}else {
				housePage = houseRepository.findByAddressLikeOrderByCreatedAtDesc("%" + area + "%", pageable);
			}
		} else if (price != null) {
			if(order != null && order.equals("priceAsc")) {
				housePage = houseRepository.findByPriceLessThanEqualOrderByPriceAsc(price, pageable);
			}else {
				housePage = houseRepository.findByPriceLessThanEqualOrderByCreatedAtDesc(price, pageable);
			}
		} else {
			if(order != null && order.equals("priceAsc")) {
				housePage = houseRepository.findAllByOrderByPriceAsc(pageable);
			}else {
				housePage = houseRepository.findAllByOrderByCreatedAtDesc(pageable);
			}
		}
		
		model.addAttribute("housePage", housePage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("area", area);
		model.addAttribute("price", price);
		model.addAttribute("order", order);
		
		return "houses/index";
	}
	
	@GetMapping("/{id}")
	public String show(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable(name = "id") Integer id,FavoriteRegisterForm favoriteRegisterForm, FavoriteRepository favoriteRepository, HttpServletRequest httpServletRequest, Model model) {
		House house = houseRepository.getReferenceById(id);
		
		
		model.addAttribute("house", house);
		
		if(userDetailsImpl != null) {
			User user = userDetailsImpl.getUser();
			
			favoriteRegisterForm.setHouseId(house.getId());
			favoriteRegisterForm.setUserId(user.getId());
			
			boolean isFavorited = favoriteService.isFavoritedHouseAndFavoritedUser(house, user);
			
			model.addAttribute("user", user);
			model.addAttribute("reservationInputForm", new ReservationInputForm());
			model.addAttribute("favoriteRegisterForm", favoriteRegisterForm);
			model.addAttribute("success", isFavorited);
		}
		
		return "houses/show";
	}

}
