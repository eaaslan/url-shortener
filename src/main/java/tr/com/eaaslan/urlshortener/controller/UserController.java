package tr.com.eaaslan.urlshortener.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.eaaslan.urlshortener.entity.UrlMapping;
import tr.com.eaaslan.urlshortener.entity.User;
import tr.com.eaaslan.urlshortener.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUser();
    }



}
