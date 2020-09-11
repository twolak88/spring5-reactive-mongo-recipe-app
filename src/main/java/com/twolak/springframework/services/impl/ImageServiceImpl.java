/**
 * 
 */
package com.twolak.springframework.services.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import com.twolak.springframework.domain.Recipe;
import com.twolak.springframework.repositories.RecipeRepository;
import com.twolak.springframework.services.ImageService;

/**
 * @author twolak
 *
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	private final RecipeRepository recipeRepository;

	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public void saveImageFile(String id, MultipartFile file) {

		Optional<Recipe> recipeOptional = this.recipeRepository.findById(id);

		if (!recipeOptional.isPresent()) {
			String msg = "Recipe for id: " + id + " not found!";
			log.debug(msg);
			throw new RuntimeException(msg);
		}

		try {
			Byte[] byteObject = new Byte[file.getBytes().length];

			int i = 0;

			for (byte b : file.getBytes()) {
				byteObject[i++] = b;
			}
			
			Recipe recipe = recipeOptional.get();
			recipe.setImage(byteObject);
			this.recipeRepository.save(recipe);
		} catch (IOException e) {
			log.error("Error occured!", e);
			e.printStackTrace();
		}
	}
}
