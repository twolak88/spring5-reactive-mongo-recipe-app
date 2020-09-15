package com.twolak.springframework.controllers;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.thymeleaf.exceptions.TemplateInputException;

import com.twolak.springframework.commands.RecipeCommand;
import com.twolak.springframework.exceptions.NotFoundException;
import com.twolak.springframework.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author twolak
 *
 */
@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

	private static final String VIEW_RECIPE_SHOW = "recipe/show";
	private static final String VIEW_RECIPE_RECIPEFORM = "recipe/recipeform";
	private RecipeService recipeService;
	
	private WebDataBinder webDataBinder;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		this.webDataBinder = webDataBinder;
	}

	@GetMapping("/{recipeId}/show")
	public String getRecipe(@PathVariable String recipeId, Model model) {
		model.addAttribute("recipe", this.recipeService.findById(recipeId));
		return VIEW_RECIPE_SHOW;
	}
	
	@GetMapping("/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return VIEW_RECIPE_RECIPEFORM;
	}
	
	@GetMapping("/{recipeId}/update")
	public String updateRecipe(@PathVariable String recipeId, Model model) {
		model.addAttribute("recipe", this.recipeService.findById(recipeId));
		return VIEW_RECIPE_RECIPEFORM;
	}
	
	@PostMapping
	public String saveOrUpdate(@ModelAttribute("recipe") RecipeCommand recipeCommand) {
		this.webDataBinder.validate();
		BindingResult bindingResult = this.webDataBinder.getBindingResult();
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(ObjectError -> {
				log.error(ObjectError.toString());
			});
			return VIEW_RECIPE_RECIPEFORM;
		}
		this.recipeService.save(recipeCommand).block();
		return "redirect:/";
	}
	
	@GetMapping("/{recipeId}/delete")
	public String deleteRecipe(@PathVariable String recipeId) {
		log.debug("Deleting id: " + recipeId);
		this.recipeService.deleteById(recipeId).block();
		return "redirect:/";
	}
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler({NotFoundException.class, TemplateInputException.class, NumberFormatException.class, WebExchangeBindException.class})
	public String handleNotFound(Exception exception, Model model) {
		log.error("Handling " + exception.getClass());
		log.error(exception.getMessage());
		log.error(ExceptionUtils.getStackTrace(exception));
		
		model.addAttribute("exception", exception);
		model.addAttribute("httpStatus", HttpStatus.NOT_FOUND);
		return "recipe/error/404error";
	}
}
