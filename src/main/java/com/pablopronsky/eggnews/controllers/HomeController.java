package com.pablopronsky.eggnews.controllers;

import com.pablopronsky.eggnews.entities.News;
import com.pablopronsky.eggnews.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    NewsService newsService;
    public String index(){
        return "home.html";
    }

    @GetMapping("/news")
    public String index(ModelMap model) throws Exception {
        List<News> news = newsService.listAllNews();
        model.addAttribute("news", news);
        return "home.html";
    }

    @GetMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable Long id){
        model.addAttribute("news", newsService.getOne(id));
        return "admin_panel.html";
    }

    @PostMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable String id, String title, String body, boolean isActive) throws Exception {
        News news = newsService.getOne(Long.valueOf(id));

        try{
            newsService.update(Long.valueOf(id), title, body, isActive);
            return "redirect:/home/news";
        }catch (Exception e){
            return "admin_panel.html";
        }

    }
}
