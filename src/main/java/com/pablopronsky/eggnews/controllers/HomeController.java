package com.pablopronsky.eggnews.controllers;

import com.pablopronsky.eggnews.entities.News;
import com.pablopronsky.eggnews.services.NewsService;
import com.pablopronsky.eggnews.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    NewsService newsService;
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/news")
    public String index(ModelMap model) throws Exception {
        try {
            List<News> news = newsService.listAllNews();
            model.put("news", news);

        } catch (Exception e) {
            model.put("error", e.getMessage());
        }
        return "home.html";
    }

    @GetMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable Long id) {
        model.addAttribute("news", newsService.getOne(id));
        return "admin_panel.html";
    }

    @PostMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable String id, String title, String body, boolean isActive) throws Exception {
        News news = newsService.getOne(Long.valueOf(id));
        try {
            newsService.update(Long.valueOf(id), title, body, isActive);
            return "redirect:/home/news";
        } catch (Exception e) {
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
    public String readMore(@PathVariable String id, ModelMap model) {
        model.put("news", newsService.getOne((Long.valueOf(id))));
        return "read_more.html";
    }

    @GetMapping("/signup")
    public String singUp() {
        return "signup.html";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam(required = false) String name, @RequestParam String email,
                               @RequestParam String password, @RequestParam String password2, ModelMap model,
                               @RequestParam(required = false)MultipartFile file) {
        try {
            userService.register(name, email, password, password2, file);
            model.put("success", "success");
            return "login.html";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            e.printStackTrace();
            return "signup.html";
        }
    }

    @GetMapping("/login")
    public String logIn(@RequestParam(required = false) String error, ModelMap model) {
        if (error == null) {
            return "login.html";
        }else {
            model.put("error", "Invalid username or password");
            return null;
        }
    }
}
