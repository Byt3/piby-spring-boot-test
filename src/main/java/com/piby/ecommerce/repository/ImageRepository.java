package com.piby.ecommerce.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.piby.ecommerce.entities.Image;

public interface ImageRepository extends PagingAndSortingRepository<Image, Long> {
	
	public Image findByName(String name);
	
}
