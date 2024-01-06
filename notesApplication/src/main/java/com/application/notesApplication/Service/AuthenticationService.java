package com.application.notesApplication.Service;

import com.application.notesApplication.DAO.TokenRepository;
import com.application.notesApplication.DAO.UserRepository;
import com.application.notesApplication.DTO.AuthenticationRequest;
import com.application.notesApplication.Model.AuthenticationResponse;
import com.application.notesApplication.Model.Token;
import com.application.notesApplication.Model.TokenType;
import com.application.notesApplication.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(User user){
        if(userRepository.existsByUsername(user.getUsername()))
            return "User Already Exists";
        User u1 = User.builder()
                .username(user.getUsername())
//                .password(passwordEncoder.encode((CharSequence) user.getPassword()))
                .password(user.getPassword())
                .build();
        var saved = userRepository.save(u1);
//        var jwtToken = jwtService.generateToken(u1);
//        var refreshToken = jwtService.generateRefreshToken(u1);
        //saveUser(saved,jwtToken);

//        return AuthenticationResponse.builder()
//                .accessToken(jwtToken)
//                .refreshToken(refreshToken)
//                .build();
        return "SuccessFul";
    }

    private void saveUser(User saved, String jwtToken) {
        var token = Token.builder()
                .user(saved)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest user){
//        authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(
//                    user.getUsername(),
//                    passwordEncoder.encode(user.getPassword())
//            )
//        );
        User found  = userRepository.findByUsername(user.getUsername())
                .orElseThrow();
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken());
        //var pass = passwordEncoder.encode(user.getPassword());
        if(found.getPassword().equals(user.getPassword()))
        {
            var jwtToken = jwtService.generateToken(found);
            var refreshToken = jwtService.generateRefreshToken(found);
            revokeAllUserTokens(found);
            saveUser(found,jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }
//        else
        return AuthenticationResponse.builder().build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findActiveTokensByUserId(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
