package com.HotelManagment.HotelManagment.Controller;

import com.HotelManagment.HotelManagment.Service.interfac.IUserService;
import com.HotelManagment.HotelManagment.dto.Responce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Responce> getAllUsers(){
        Responce res= userService.getAllusers();
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Responce> getUserById(@PathVariable("id") Long id){
        Responce res= userService.getuserById(id.toString());
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Responce> deleteById(@PathVariable("id") Long id){
        Responce res= userService.deleteUser(id.toString());
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }
    @GetMapping("/get-logged-in-profile-info")
    public ResponseEntity<Responce> getLoggedInUserProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Responce res= userService.getMyInfo(email);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }
    @GetMapping("/get-user-bookings/{userId}")
    public ResponseEntity<Responce> getUserBookingHistory(@PathVariable("userId") String userId) {
        Responce response = userService.getUserBookingHistory(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
