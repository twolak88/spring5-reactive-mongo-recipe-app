package com.twolak.springframework.services;

import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

/**
 * @author twolak
 *
 */
public interface ImageService {
	Mono<Void> saveImageFile(String id, MultipartFile file);
}
