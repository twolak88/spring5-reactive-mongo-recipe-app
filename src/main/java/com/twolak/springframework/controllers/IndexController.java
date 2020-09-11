package com.twolak.springframework.controllers;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.context.LazyContextVariable;

import com.twolak.springframework.commands.RecipeCommand;
import com.twolak.springframework.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author twolak
 *
 */
@Slf4j
@Controller()
public class IndexController {
    
	private final RecipeService recipeService;
    
	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping({"/", "/index"})
    public String getIndexPage(Model model) {
		
		model.addAttribute("recipes", new LazyContextVariable<Set<RecipeCommand>>() {

			@Override
			protected Set<RecipeCommand> loadValue() {
				return recipeService.findAll();
			}
		});
		log.info("Getting Index Page");
		return "index";
    }
}
