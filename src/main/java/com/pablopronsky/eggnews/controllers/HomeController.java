package com.pablopronsky.eggnews.controllers;

import com.pablopronsky.eggnews.entities.News;
import com.pablopronsky.eggnews.services.NewsService;
import com.pablopronsky.eggnews.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    NewsService newsService;
    UserService userService;
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

    @GetMapping("/delete/{id}")
    public String deleteNews(@PathVariable Long id) {
        try {
            newsService.deleteNews(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/home/news";
    }

    @GetMapping("/readmore/{id}")
    public String readMore(@PathVariable String id, ModelMap model){
        try{
            model.put("news", newsService.getOne((Long.valueOf(id))));
            return "read_more.html";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "redirect:/home/news";
        }
    }

    @GetMapping("/signup")
    public String singUp(){
        return "signup.html";
    }

    @PostMapping("/register")
    public String loginCheck(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap model){
        try {
            userService.register(name, email, password, password2);
            return "index.html";
        } catch (Exception e) {
            return "signup.html";
        }
    }

    @GetMapping("/login")
    public String logIn(){
        return "login.html";
    }
    
}
