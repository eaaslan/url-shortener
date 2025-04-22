package tr.com.eaaslan.urlshortener.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.eaaslan.urlshortener.entity.Dto.ClickEventDto;
import tr.com.eaaslan.urlshortener.entity.Dto.LoginRequest;
import tr.com.eaaslan.urlshortener.entity.Dto.RegisterRequest;
import tr.com.eaaslan.urlshortener.service.UserService;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/public/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }


    @PostMapping("/public/register")
    public ResponseEntity<RegisterRequest> registerUser(@RequestBody RegisterRequest registerRequest){

        userService.registerUser(registerRequest);
        return ResponseEntity.ok(registerRequest);
        //todo add RegisterResponse dto
    }
}
