package com.pablopronsky.eggnews.controllers;

import com.pablopronsky.eggnews.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @GetMapping("/create")
    public String createNews(){
        return "news.html";
    }

    @PostMapping("/created")
    public String createdNews(@RequestParam(required = false) String title, @RequestParam(required = false) String body, ModelMap model) throws Exception{
        try{
            newsService.createNews(title, body);
            model.put("success","News creted succesfully");
        }catch (Exception e){
            model.put("error", e.getMessage());
            return "news.html";
        }
        return "home.html";
    }
}
