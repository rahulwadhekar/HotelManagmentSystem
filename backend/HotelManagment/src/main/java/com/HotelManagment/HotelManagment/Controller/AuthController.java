package com.HotelManagment.HotelManagment.Controller;

import com.HotelManagment.HotelManagment.Entity.User;
import com.HotelManagment.HotelManagment.Service.interfac.IUserService;
import com.HotelManagment.HotelManagment.dto.Request;
import com.HotelManagment.HotelManagment.dto.Responce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<Responce> register(@RequestBody User user){

        Responce responce = userService.register(user);

         return ResponseEntity.status(responce.getStatusCode()).body(responce);


    }

    @PostMapping("/login")
    public ResponseEntity<Responce> login(@RequestBody Request request){

        Responce responce = userService.login(request);
        return ResponseEntity.status(responce.getStatusCode()).body(responce);


    }
}
