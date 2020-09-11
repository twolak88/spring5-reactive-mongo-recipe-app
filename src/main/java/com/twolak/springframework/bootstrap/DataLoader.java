package com.twolak.springframework.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.twolak.springframework.domain.Category;
import com.twolak.springframework.domain.Difficulty;
import com.twolak.springframework.domain.Ingredient;
import com.twolak.springframework.domain.Notes;
import com.twolak.springframework.domain.Recipe;
import com.twolak.springframework.domain.UnitOfMeasure;
import com.twolak.springframework.repositories.CategoryRepository;
import com.twolak.springframework.repositories.RecipeRepository;
import com.twolak.springframework.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author twolak
 *
 */
@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final CategoryRepository categoryRepository;
	private final RecipeRepository recipeRepository;

	public DataLoader(UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.categoryRepository = categoryRepository;
		this.recipeRepository = recipeRepository;
	}
	
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.debug("Loading bootstrap data");
		if (this.categoryRepository.count() == 0) {
			loadCategories();
		}
		if (this.unitOfMeasureRepository.count() == 0) {
			LoadUnitOfMeasures();
		}
		Set<Recipe> recipes = getRecipes();
		recipeRepository.saveAll(recipes);
		syncRecipeCategories(recipes);
	}
	
	private void syncRecipeCategories(Set<Recipe> recipes) {
		Set<Category> categories = new HashSet<>();
		recipes.forEach(recipe -> recipe.getCategories().forEach(category -> {
			category.getRecipes().add(recipe);
			categories.add(category);
			}));
		this.categoryRepository.saveAll(categories);
		
	}

	private Set<Recipe> getRecipes() {
		
		Set<Recipe> recipes = new HashSet<>();
		
		Optional<UnitOfMeasure> teaspoonOpt = unitOfMeasureRepository.findByDescription("Teaspoon");
		
		if(!teaspoonOpt.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}
		
		Optional<UnitOfMeasure> tablespoonOpt = unitOfMeasureRepository.findByDescription("Tablespoon");
		if(!tablespoonOpt.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}
		
		Optional<UnitOfMeasure> cupOpt = unitOfMeasureRepository.findByDescription("Cup");
		if(!cupOpt.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}
		
		Optional<UnitOfMeasure> pinchOpt = unitOfMeasureRepository.findByDescription("Pinch");
		if(!pinchOpt.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}
		
		Optional<UnitOfMeasure> ounceOpt = unitOfMeasureRepository.findByDescription("Ounce");
		if(!ounceOpt.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}
		
		Optional<UnitOfMeasure> dashOpt = unitOfMeasureRepository.findByDescription("Dash");
		if(!dashOpt.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}
		
		Optional<UnitOfMeasure> poundOpt = unitOfMeasureRepository.findByDescription("Pound");
		if(!poundOpt.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}
		
		Optional<UnitOfMeasure> pieceOpt = unitOfMeasureRepository.findByDescription("Piece");
		if(!pieceOpt.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}
		
		Optional<UnitOfMeasure> quartOpt = unitOfMeasureRepository.findByDescription("Quart");
		if(!quartOpt.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}
		
		UnitOfMeasure teaspoonUom = teaspoonOpt.get();
		UnitOfMeasure tablespoonUom = tablespoonOpt.get();
		UnitOfMeasure cupUom = cupOpt.get();
		UnitOfMeasure pinchUom = pinchOpt.get();
		UnitOfMeasure OunceUom = ounceOpt.get();
		UnitOfMeasure dashUom = dashOpt.get();
		UnitOfMeasure poundUom = poundOpt.get();
		UnitOfMeasure pieceUom = pieceOpt.get();
		UnitOfMeasure quartUom = quartOpt.get();
		
		Optional<Category> americanOpt = categoryRepository.findByDescription("American");
		if(!americanOpt.isPresent()) {
			throw new RuntimeException("Expected Category not found");
		}
		
		Optional<Category> italianOpt = categoryRepository.findByDescription("Italian");
		if(!italianOpt.isPresent()) {
			throw new RuntimeException("Expected Category not found");
		}
		
		Optional<Category> mexicanOpt = categoryRepository.findByDescription("Mexican");
		if(!mexicanOpt.isPresent()) {
			throw new RuntimeException("Expected Category not found");
		}
		
		Optional<Category> fastFoodOpt = categoryRepository.findByDescription("Fast Food");
		if(!fastFoodOpt.isPresent()) {
			throw new RuntimeException("Expected Category not found");
		}
		
		Category american = americanOpt.get();
		Category italian = italianOpt.get();
		Category mexican = mexicanOpt.get();
		Category fastFood = fastFoodOpt.get();
		

		Recipe guacamole = createRecipe("How to Make Perfect Guacamole Recipe", 
				10, 
				0, 
				4, 
				"simplyrecipes",
				"https://www.simplyrecipes.com/recipes/perfect_guacamole/", 
				"1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" + 
				"\n" + 
				"2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" + 
				"\n" + 
				"3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" + 
				"\n" + 
				"Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" + 
				"\n" + 
				"Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" + 
				"\n" + 
				"Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" + 
				"\n" + 
				"4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n", 
				Difficulty.EASY,
				createNotes("Be careful handling chiles if using. Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours."));
		guacamole.addIngredient(new Ingredient("ripe avocados", BigDecimal.valueOf(2), pieceUom));
		guacamole.addIngredient(new Ingredient("salt", BigDecimal.valueOf(0.25), teaspoonUom));
		guacamole.addIngredient(new Ingredient("fresh lime juice or lemon juice", BigDecimal.valueOf(1), tablespoonUom));
		guacamole.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", BigDecimal.valueOf(2), tablespoonUom));
		guacamole.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", BigDecimal.valueOf(2), pieceUom));
		guacamole.addIngredient(new Ingredient("cilantro (leaves and tender stems), finely chopped", BigDecimal.valueOf(2), tablespoonUom));
		guacamole.addIngredient(new Ingredient("freshly grated black pepper", BigDecimal.valueOf(1), dashUom));
		guacamole.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", BigDecimal.valueOf(0.5), pieceUom));
		guacamole.addIngredient(new Ingredient("Red radishes or jicama, to garnish", BigDecimal.valueOf(5), pieceUom));
		guacamole.addIngredient(new Ingredient("Tortilla chips, to serve", BigDecimal.valueOf(1), pieceUom));
		guacamole.getCategories().add(mexican);
		guacamole.getCategories().add(american);
		recipes.add(guacamole);
		
		log.info("guacamole created");
		
		Recipe chicken = createRecipe("Spicy Grilled Chicken Tacos Recipe", 
				20,
				15, 
				6, 
				"simplyrecipes",
				"https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/",
				"1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" + 
				"\n" + 
				"2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" + 
				"\n" + 
				"Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" + 
				"\n" + 
				"Spicy Grilled Chicken Tacos\n" + 
				"\n" + 
				"3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" + 
				"\n" + 
				"4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" + 
				"\n" + 
				"Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" + 
				"\n" + 
				"5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n",
				Difficulty.HARD,
				createNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)"));
		chicken.addIngredient(new Ingredient("ancho chili powder", BigDecimal.valueOf(2), tablespoonUom));
		chicken.addIngredient(new Ingredient("dried oregano", BigDecimal.valueOf(1), teaspoonUom));
		chicken.addIngredient(new Ingredient("dried cumin", BigDecimal.valueOf(1), teaspoonUom));
		chicken.addIngredient(new Ingredient("sugar", BigDecimal.valueOf(1), teaspoonUom));
		chicken.addIngredient(new Ingredient("salt", BigDecimal.valueOf(0.5), teaspoonUom));
		chicken.addIngredient(new Ingredient("clove garlic, finely chopped", BigDecimal.valueOf(1), pieceUom));
		chicken.addIngredient(new Ingredient("finely grated orange zest", BigDecimal.valueOf(1), tablespoonUom));
		chicken.addIngredient(new Ingredient("fresh-squeezed orange juice", BigDecimal.valueOf(3), tablespoonUom));
		chicken.addIngredient(new Ingredient("olive oil", BigDecimal.valueOf(2), tablespoonUom));
		chicken.addIngredient(new Ingredient("skinless, boneless chicken thighs", BigDecimal.valueOf(1.25), poundUom));
		chicken.addIngredient(new Ingredient("small corn tortillas", BigDecimal.valueOf(8), pieceUom));
		chicken.addIngredient(new Ingredient("packed baby arugula", BigDecimal.valueOf(3), cupUom));
		chicken.addIngredient(new Ingredient("medium ripe avocados, sliced", BigDecimal.valueOf(2), pieceUom));
		chicken.addIngredient(new Ingredient("radishes, thinly sliced", BigDecimal.valueOf(4), pieceUom));
		chicken.addIngredient(new Ingredient("cherry tomatoes, halved", BigDecimal.valueOf(0.5), quartUom));
		chicken.addIngredient(new Ingredient("red onion, thinly sliced", BigDecimal.valueOf(0.25), pieceUom));
		chicken.addIngredient(new Ingredient("Roughly chopped cilantro", BigDecimal.valueOf(1), pieceUom));
		chicken.addIngredient(new Ingredient("sour cream thinned", BigDecimal.valueOf(0.5), cupUom));
		chicken.addIngredient(new Ingredient("milk", BigDecimal.valueOf(0.5), cupUom));
		chicken.addIngredient(new Ingredient("lime, cut into wedges", BigDecimal.valueOf(1), pieceUom));
		chicken.getCategories().add(american);
		chicken.getCategories().add(fastFood);
		recipes.add(chicken);
		
		log.info("chicken created");
		
		return recipes;
	}

	private Recipe createRecipe(String description, Integer prepTime, Integer cookTime, Integer servings, String source,
			String url, String directions, Difficulty difficulty, Notes notes) {
		Recipe recipe = new Recipe();
		recipe.setDescription(description);
		recipe.setPrepTime(prepTime);
		recipe.setCookTime(cookTime);
		recipe.setServings(servings);
		recipe.setSource(source);
		recipe.setUrl(url);
		recipe.setDirections(directions);
		recipe.setDifficulty(difficulty);
		recipe.setNotes(notes);
		return recipe;
	}

	private Notes createNotes(String note) {
		Notes notes = new Notes();
		notes.setNote(note);
		return notes;
	}
	
	private void loadCategories() {
		saveCategory("American");
		saveCategory("Italian");
		saveCategory("Mexican");
		saveCategory("Fast Food");
	}
	
	private void saveCategory(String category) {
		Category cat = new Category();
		cat.setDescription(category);
		this.categoryRepository.save(cat);
	}
	
	private void LoadUnitOfMeasures() {
		saveUnitOfMeasure("Teaspoon");
		saveUnitOfMeasure("Tablespoon");
		saveUnitOfMeasure("Cup");
		saveUnitOfMeasure("Pinch");
		saveUnitOfMeasure("Ounce");
		saveUnitOfMeasure("Dash");
		saveUnitOfMeasure("Pound");
		saveUnitOfMeasure("Piece");
		saveUnitOfMeasure("Quart");
	}
	
	private void saveUnitOfMeasure(String description) {
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setDescription(description);
		this.unitOfMeasureRepository.save(unitOfMeasure);
	}
}
