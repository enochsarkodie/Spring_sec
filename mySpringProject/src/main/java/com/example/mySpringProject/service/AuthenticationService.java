package com.example.mySpringProject.service;

import com.example.mySpringProject.EmailTemplateName.EmailTemplateName;
import com.example.mySpringProject.dtos.AccountLoginDTO;
import com.example.mySpringProject.dtos.RegistrationDTO;
import com.example.mySpringProject.model.TokenModel.Token;
import com.example.mySpringProject.model.role.Role;
import com.example.mySpringProject.model.User;
import com.example.mySpringProject.dao.AuthenticationDAO;
import com.example.mySpringProject.repositories.TokenRepository;
import com.example.mySpringProject.repositories.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${confirmationUrl}")
    private String activationUrl;

    public void registerUser(RegistrationDTO registrationDTO) throws MessagingException {
        if(userRepository.existsByEmail(registrationDTO.getEmail())){
            throw new MessagingException("Email already exist");
        }
        var user = User.builder()
                .firstName(registrationDTO.getFirstName())
                .lastName(registrationDTO.getLastName())
                .email(registrationDTO.getEmail())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(user);
        sendValidationEmail(user);



    }

    private void sendValidationEmail(User user) throws MessagingException {

        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                (String) newToken,
                "Account activation"
                //work on the response
        );
    }

    private Object generateAndSaveActivationToken(User user) {
        //generateToken
        String generatedToken = generateActivationCode(8);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken ;
    }

    private String generateActivationCode(int length ) {
        String characters = "123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i= 0; i<length; i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();


    }

    public AuthenticationDAO login (AccountLoginDTO loginDTO){
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );
          var claims = new HashMap<String, Object>();
          var user = ((User)auth.getPrincipal());
          claims.put("fullName", user.fullName());
          var jwtToken = jwtService.generateToken(claims,user);
        return AuthenticationDAO.builder()
                .status("200")
                .message("Login successful!")
                .token(jwtToken)
                .build();
    }

//@Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired, A new token has been sent to your mail ");
        }
        var user = userRepository.findById(savedToken.getUser().getId()).orElseThrow
                (()-> new UsernameNotFoundException("User not found"));

        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);

    }

}
