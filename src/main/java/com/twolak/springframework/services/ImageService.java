package com.twolak.springframework.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author twolak
 *
 */
public interface ImageService {
	void saveImageFile(String id, MultipartFile file);
}
