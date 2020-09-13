/**
 * 
 */
package com.twolak.springframework.services.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.twolak.springframework.domain.Recipe;
import com.twolak.springframework.repositories.reactive.RecipeReactiveRepository;
import com.twolak.springframework.services.ImageService;

import reactor.core.publisher.Mono;

/**
 * @author twolak
 *
 */
@Service
public class ImageServiceImpl implements ImageService {

	private final RecipeReactiveRepository recipeReactiveRepository;

	public ImageServiceImpl(RecipeReactiveRepository recipeReactiveRepository) {
		this.recipeReactiveRepository = recipeReactiveRepository;
	}

	@Override
	public Mono<Void> saveImageFile(String id, MultipartFile file) {
		
		Mono<Recipe> recipeMono = this.recipeReactiveRepository.findById(id).map(recipe -> {
			Byte[] byteObject = new Byte[0];
			try {
				byteObject = new Byte[file.getBytes().length];

				int i = 0;
	
				for (byte b : file.getBytes()) {
					byteObject[i++] = b;
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			
			recipe.setImage(byteObject);
			
			return recipe;
		});
		
		this.recipeReactiveRepository.save(recipeMono.block()).block();
		
		return Mono.empty();
	}
}
