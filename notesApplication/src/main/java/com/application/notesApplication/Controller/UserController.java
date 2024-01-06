package com.application.notesApplication.Controller;

import com.application.notesApplication.Service.AuthenticationService;
import com.application.notesApplication.DAO.UserRepository;
import com.application.notesApplication.DTO.AuthenticationRequest;
import com.application.notesApplication.Model.AuthenticationResponse;
import com.application.notesApplication.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user){
        //check if user already exists
        String ar = authenticationService.register(user);
        if(ar.equals("SuccessFul"))
            return ResponseEntity.status(HttpStatus.CREATED).body("User Created SuccessFully, Continue to login");
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Registration failed: Username already exists");
    }
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse ar = authenticationService.authenticate(authenticationRequest);
        if(ar.getAccessToken()!=null && ar.getRefreshToken()!=null)
            return ResponseEntity.ok(ar);
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: Invalid credentials");
    }
}
