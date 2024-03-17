package com.example.samuraitravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.FavoriteRegisterForm;
import com.example.samuraitravel.repository.FavoriteRepository;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.UserRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.FavoriteService;

import jakarta.servlet.http.HttpSession;

@Controller
public class FavoriteController {
	private final FavoriteRepository favoriteRepository;
	private final UserRepository userRepository;
	private final HouseRepository houseRepository;
	private final FavoriteService favoriteService;
	//private final Favorite favorite;
	
	public FavoriteController(FavoriteRepository favoriteRepository, UserRepository userRepository, HouseRepository houseRepository, FavoriteService favoriteService/*, Favorite favorite*/) {
		this.favoriteRepository = favoriteRepository;
		this.userRepository = userRepository;
		this.houseRepository = houseRepository;
		this.favoriteService = favoriteService;
		//this.favorite = favorite;
	}
	
	@GetMapping("/favorites")
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC)Pageable pageable, Model model, HttpSession httpSession) {
		User user = userDetailsImpl.getUser();
		Page<Favorite> favoritesPage = favoriteRepository.findByUser(user, pageable);
		httpSession.setAttribute("favoriteHouse", houseRepository.findAll());
		
		model.addAttribute("user", user);
		model.addAttribute("favoritesPage", favoritesPage);
		
		return "favorites/index";
	}
	
	/*@GetMapping("houses/{id}")
	public String input(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable(name = "id") Integer id, @ModelAttribute FavoriteInputForm favoriteInputForm, Model model) {
		//House house = houseRepository.getReferenceById(id);
		//User user = userDetailsImpl.getUser();
		
		FavoriteRegisterForm favoriteRegisterForm = new FavoriteRegisterForm();
		
		//model.addAttribute("house", house);
		//model.addAttribute("user", user);
		model.addAttribute("favoriteRegisterForm", favoriteRegisterForm);
		
		return "/houses/{id}/create";
	}*/
	
	@PostMapping("/houses/{id}/create")
	public String create(/*@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable(name = "id") Integer id, */@ModelAttribute @Validated FavoriteRegisterForm favoriteRegisterForm) {
		//Integer houseId = houseRepository.getHouseId(id);
		//Integer userId = userRepository.getUserId();
		
		favoriteService.create(favoriteRegisterForm);
		
		return "redirect:/houses/{id}";
	}
	
	/*@PostMapping("houses/{id}/favorite")
	public String create(@PathVariable(name = "id") Integer id, @ModelAttribute @Validated FavoriteInputForm favoriteForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		Favorite favorite = new Favorite();
		favorite.setUserId(favoriteForm.getUserId());
		favorite.setHouseId(favoriteForm.getHouseId());
		favoriteRepository.insertFavorite(favorite);
		
		model.addAttribute("userId", user);
		model.addAttribute("houseId", house);
		
		redirectAttributes.addFlashAttribute("favoriteForm", favoriteForm);
		
		return "redirect:/houses/show";
	}*/
	
	/*@PostMapping("/create")
	public ResponseEntity<Favorite> create(@RequestBody Favorite favorite, Principal principal){
		Favorite favorite = new Favorite();
		String currentUserId = principal.getName();
		
		return "redirect:/houses/show";
	}*/
	
	/*@GetMapping("/houses/show")
	public String show(@PathVariable(name = "id") Integer id, @ModelAttribute @Validated FavoriteInputForm favoriteInputForm, Model model) {
		House house = houseRepository.getReferenceById(id);
		
		model.addAttribute("house", house);
		model.addAttribute("favoriteInputForm", favoriteInputForm);
		
		return "houses/show";
	}*/
	
	/*@PostMapping("/houses/show")
	public String Favorite(@PathVariable("houseId") int houseId, @ModelAttribute("favorite") FavoriteRepository favoriteRepository, Authentication loginUser, Model model) {
		if(favoriteRepository.existsByUserIdAndHouseId(loginUser.getId(), houseId) == true) {
			favoriteRepository.deleteByUserNameAndHouseId(loginUser.getName(), houseId);
		}else {
			favoriteRepository.setHouseId(houseId);
			favoriteRepository.setUserName(loginUser.getName());
			favoriteRepository.save(favoriteRepository);
		}
		
		return "redirect:/houses/show";
	}*/
	
	/*@GetMapping
	public String show(@ModelAttribute("houses") House house, Authentication loginUser, Model model) {
		//Map<Integer, BigInteger> favoriteCount = favoriteRepository.findFavoriteCount();
		//model.addAttribute("userName", favoriteRepository.findByUserName(loginUser.getName()));
		//model.addAttribute("houses", favoriteRepository.findAllHouses());
		//model.addAttribute("favoriteCount", favoriteCount);
		//model.addAttribute("myFavorites", favoriteRepository.findMyFavorites(loginUser.getName()));
		
		return "redirect:/favorites/list";
	}*/

}
