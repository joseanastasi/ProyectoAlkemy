package com.alkemy.ot9.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.alkemy.ot9.exceptions.NewsModelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.alkemy.ot9.entities.NewsEntity;
import com.alkemy.ot9.repository.INewsRepository;
import com.alkemy.ot9.interfaceService.INewsService;

@Service
public class NewsServiceImpl implements INewsService {

	@Autowired
	private INewsRepository newsRepository;

	@Override
	public List<NewsEntity> getListNews() {

		List<NewsEntity> listNews = new ArrayList<NewsEntity>();
		listNews = (List<NewsEntity>) newsRepository.findAll();

		return listNews;
	}

	@Override
	public void saveNews(NewsEntity news) {

		newsRepository.save(news);

	}

	@Override
	public NewsEntity getNewsById(Long id) throws NewsModelNotFoundException {

		Optional<NewsEntity> newsEntity = newsRepository.findById(id);

		if (newsEntity.isEmpty()) {
			throw new NewsModelNotFoundException("La agenda/novedad no fue encontrada");
		}

		return newsEntity.get();
	}

	@Override
	public void deleteNewsById(Long id) throws NewsModelNotFoundException {
		Optional<NewsEntity> newsEntity = newsRepository.findById(id);

		if (newsEntity.isEmpty()) {
			throw new NewsModelNotFoundException("La agenda/novedad no fue encontrada");
		}
		newsRepository.deleteById(id);

	}

	@Override
	public Page<NewsEntity> getAllNews(Pageable pageable) {

		return newsRepository.findAll(pageable);
	}

	@Override
	public Page<NewsEntity> getListNewsToEnable(boolean enabled, Pageable pageable) {
		
		return newsRepository.listNewsToEnable(enabled, pageable);
	}
	
	

}
