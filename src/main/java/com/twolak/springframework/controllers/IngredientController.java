/**
 * 
 */
package com.twolak.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.twolak.springframework.commands.IngredientCommand;
import com.twolak.springframework.commands.UnitOfMeasureCommand;
import com.twolak.springframework.services.IngredientService;
import com.twolak.springframework.services.RecipeService;
import com.twolak.springframework.services.UnitOfMeasureService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * @author twolak
 *
 */
@Slf4j
@Controller
@RequestMapping("/recipe/{recipeId}")
public class IngredientController {
	
	private static final String RECIPE_INGREDIENT_INGREDIENTFORM = "recipe/ingredient/ingredientform";
	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;
	
	private WebDataBinder webDataBinder;
	
	public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}
	
	@InitBinder("ingredient")
	public void initBinder(WebDataBinder webDataBinder) {
		this.webDataBinder = webDataBinder;
	}
	
	@ModelAttribute("uomList")
	public Flux<UnitOfMeasureCommand> populateUnitOfMeassureList() {
		return this.unitOfMeasureService.findAll();
	}
	
	@GetMapping("/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		log.debug("Getting ingredients for recipe: " + recipeId);
		model.addAttribute("recipe", this.recipeService.findById(recipeId));
		return "recipe/ingredient/list";
	}
	
	@GetMapping("/ingredient/{ingredientId}/show")
	public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		log.debug("Getting ingredient for ingredient id: " + ingredientId + ", recipe id: " + recipeId);
		model.addAttribute("ingredient", this.ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
		return "recipe/ingredient/show";
	}
	
	@GetMapping("/ingredient/new")
	public String newIngredient(@PathVariable String recipeId, Model model) {
//		RecipeCommand recipeCommand = this.recipeService.findById(recipeId).block();
//		if (recipeCommand == null) {
//			throw new RuntimeException("Recipe doesn't exists!");
//		}
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeId);
		
		model.addAttribute("ingredient", ingredientCommand);
		ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
		
		return RECIPE_INGREDIENT_INGREDIENTFORM;
	}
	
	@GetMapping("/ingredient/{ingredientId}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		model.addAttribute("ingredient", this.ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
		return RECIPE_INGREDIENT_INGREDIENTFORM;
	}
	
	@PostMapping("/ingredient")
	public String saveOrUpdate(@ModelAttribute("ingredient") IngredientCommand ingredientCommand, @PathVariable("recipeId") String recipeId, Model model) {
		this.webDataBinder.validate();
		BindingResult bindingResult = this.webDataBinder.getBindingResult();
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(ObjectError -> {
				log.error(ObjectError.toString());
			});
			return RECIPE_INGREDIENT_INGREDIENTFORM;
		}
		IngredientCommand savedIngredientCommand = this.ingredientService.save(ingredientCommand).block();
		return "redirect:/recipe/" + recipeId + "/ingredient/" + savedIngredientCommand.getId() + "/show";
	}
	
	@GetMapping("/ingredient/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
		this.ingredientService.deleteById(recipeId, ingredientId).block();
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}
}
