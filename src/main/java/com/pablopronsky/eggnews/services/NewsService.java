package com.pablopronsky.eggnews.services;

import com.pablopronsky.eggnews.entities.News;
import com.pablopronsky.eggnews.repositories.NewsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    NewsRepository newsRepository;
    @Transactional
    public void createNews(String title, String body) throws Exception{
        validate(title, body);
        News news = new News();
        news.setTitle(title);
        news.setBody(body);
        news.setActive(true);
        news.setDate(new Date());
        newsRepository.save(news);
    }
    @Transactional
    public List<News> searchNewsByTitle(String title) throws Exception{
        try{
            return newsRepository.searchNewsByTitle(title);
        }catch (Exception e){
            return null;
        }
    }
    @Transactional
    public List<News> listAllNews() throws Exception{
        try{
            return newsRepository.findAll();
        }catch (Exception e){
            return null;
        }
    }

    public News getOne(Long id){
        return newsRepository.getReferenceById(id);
    }
    @Transactional
    public News update(Long id, String title, String body, boolean isActive) throws Exception{
        validate(title, body);
        News news = new News();
        Optional<News> newsOptional = newsRepository.findById(id);

        if (newsOptional.isPresent()){
            news = newsOptional.get();
            news.setTitle(title);
            news.setBody(body);
            news.setActive(isActive);
            news.setDate(new Date());

            newsRepository.save(news);
        }

        try{
            return news;
        }catch (Exception e){
            return null;
        }
    }
    @Transactional
    public void deleteNews(Long id, String title, String body) throws Exception {
        validate(title, body);
        News news = new News();
        Optional<News> newsOptional = newsRepository.findById(id);

        if (newsOptional.isPresent()){
            news = newsOptional.get();
            news.setActive(false);
            newsRepository.delete(news);
        }
    }

    public void validate(String title, String body) throws Exception{
        if(title == null || title.isEmpty())throw new Exception("Error, the title must be filled");
        if(body == null || body.isEmpty())throw new Exception("Error, the body must be filled");
    }
}
