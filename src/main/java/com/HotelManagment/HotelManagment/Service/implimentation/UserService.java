package com.HotelManagment.HotelManagment.Service.implimentation;

import com.HotelManagment.HotelManagment.Entity.User;
import com.HotelManagment.HotelManagment.Exceptions.OurException;
import com.HotelManagment.HotelManagment.Repository.UserRepository;
import com.HotelManagment.HotelManagment.Service.interfac.IUserService;
import com.HotelManagment.HotelManagment.dto.Request;
import com.HotelManagment.HotelManagment.dto.Responce;
import com.HotelManagment.HotelManagment.dto.UserDTO;
import com.HotelManagment.HotelManagment.utils.Jwtutils;
import com.HotelManagment.HotelManagment.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
   private PasswordEncoder passwordEncoder;

    @Autowired
    private Jwtutils jwtutils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public Responce register(User request) {
        Responce responce = new Responce();

        try {

            if(request.getRole() == null || request.getRole().isBlank()){
               request.setRole("User");

            }

            if(userRepository.existsByEmail(request.getEmail())){
                throw new OurException(request.getEmail()+ "Already has Account");

            }
            request.setPassword(passwordEncoder.encode(request.getPassword()));
            User user = userRepository.save(request);
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            responce.setStatusCode(200);
            responce.setUser(userDTO);

        }catch (OurException e){
            responce.setStatusCode(400);
            responce.setMessage(e.getMessage());

        }catch (Exception e){
            responce.setStatusCode(500);
            responce.setMessage("Error Occured During user Registration"+ e.getMessage());

        }

        return responce;
    }

    @Override
    public Responce login(Request request) {
        Responce responce = new Responce();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

            var user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new OurException("User not Found"));
            var token = jwtutils.generateToken(user);
            responce.setStatusCode(200);
            responce.setToken(token);
            responce.setRole(user.getRole());
            responce.setExpirationTime("7 days");
            responce.setMessage("Successfully");



        }catch (OurException e){
            responce.setStatusCode(400);
            responce.setMessage(e.getMessage());

        }catch (Exception e){
            responce.setStatusCode(500);
            responce.setMessage("Error Occured During user Registration"+ e.getMessage());

        }

        return responce;
    }

    @Override
    public Responce getAllusers() {
        Responce responce = new Responce();

        try{
            List<User> ListUser = userRepository.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(ListUser);
           responce.setStatusCode(200);
            responce.setMessage("successful");
            responce.setUserList(userDTOList);

        }
        catch (OurException e){
            responce.setStatusCode(400);
            responce.setMessage(e.getMessage());

        }catch (Exception e){
            responce.setStatusCode(500);
            responce.setMessage("Error Occured getting users"+ e.getMessage());

        }

        return responce;
    }

    @Override
    public Responce getUserBookingHistory(String userId) {

        Responce responce = new Responce();

        try{
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
            responce.setStatusCode(200);
            responce.setMessage("successful");
            responce.setUser(userDTO);
        }
         catch (OurException e){
            responce.setStatusCode(400);
            responce.setMessage(e.getMessage());

        }catch (Exception e){
            responce.setStatusCode(500);
            responce.setMessage("Error Occured getting History"+ e.getMessage());

        }
        return responce;
    }

    @Override
    public Responce deleteUser(String userId) {
        Responce responce = new Responce();

        try{
              userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User not Found"));
            userRepository.deleteById(Long.valueOf(userId));
            responce.setStatusCode(200);
            responce.setMessage("successful");
        }
        catch (OurException e){
            responce.setStatusCode(400);
            responce.setMessage(e.getMessage());

        }catch (Exception e){
            responce.setStatusCode(500);
            responce.setMessage("Error Occured getting History"+ e.getMessage());

        }
        return responce;
    }

    @Override
    public Responce getuserById(String userId) {
        Responce responce = new Responce();

        try{
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            responce.setStatusCode(200);
            responce.setMessage("successful");
            responce.setUser(userDTO);
        }
        catch (OurException e){
            responce.setStatusCode(400);
            responce.setMessage(e.getMessage());

        }catch (Exception e){
            responce.setStatusCode(500);
            responce.setMessage("Error Occured getting User"+ e.getMessage());

        }
        return responce;
    }

    @Override
    public Responce getMyInfo(String email) {
        Responce responce = new Responce();

        try{
            User user = userRepository.findByEmail(email).orElseThrow(()->new OurException("User not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            responce.setStatusCode(200);
            responce.setMessage("successful");
            responce.setUser(userDTO);
        }
        catch (OurException e){
            responce.setStatusCode(400);
            responce.setMessage(e.getMessage());

        }catch (Exception e){
            responce.setStatusCode(500);
            responce.setMessage("Error Occured getting User"+ e.getMessage());

        }
        return responce;
    }
}
