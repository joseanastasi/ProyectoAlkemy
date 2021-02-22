package com.alkemy.ot9.interfaceService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alkemy.ot9.entities.NewsEntity;
import com.alkemy.ot9.exceptions.NewsModelNotFoundException;

public interface INewsService {

	List<NewsEntity> getListNews();

	void saveNews(NewsEntity news);

	NewsEntity getNewsById(Long id) throws NewsModelNotFoundException;

	void deleteNewsById(Long id) throws NewsModelNotFoundException;

	Page<NewsEntity> getAllNews(Pageable pageable);
	
	Page<NewsEntity> getListNewsToEnable(boolean enabled, Pageable pageable);

}
