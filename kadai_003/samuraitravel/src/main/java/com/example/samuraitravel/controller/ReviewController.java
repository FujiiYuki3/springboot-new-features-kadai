/*package com.example.samuraitravel.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.repository.ReviewRepository;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
	private final ReviewRepository reviewRepository;
	
	public ReviewController(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@GetMapping("/houses")
	public String show(Model model) {
		List<Review> reviews = reviewRepository.findAll();
		
		model.addAttribute("reviews", reviews);
		
		return "houses/show";
	}

}*/