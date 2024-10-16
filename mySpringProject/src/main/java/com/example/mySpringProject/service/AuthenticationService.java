package com.example.mySpringProject.service;

import com.example.mySpringProject.dao.ResetPasswordRequest;
import com.example.mySpringProject.emailTemplateName.EmailTemplateName;
import com.example.mySpringProject.dao.AuthenticationDAO;
import com.example.mySpringProject.dao.ForgotPasswordRequest;
import com.example.mySpringProject.dtos.AccountLoginDTO;
import com.example.mySpringProject.dtos.RegistrationDTO;
import com.example.mySpringProject.exceptionhandlers.ProjectException;
import com.example.mySpringProject.model.ResetPasswordToken;
import com.example.mySpringProject.model.TokenModel.Token;
import com.example.mySpringProject.model.User;
import com.example.mySpringProject.model.role.Role;
import com.example.mySpringProject.repositories.ResetPasswordTokenRepository;
import com.example.mySpringProject.repositories.TokenRepository;
import com.example.mySpringProject.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.mySpringProject.exceptionhandlers.ErrorResponse.*;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Value("${confirmationUrl}")
    private String activationUrl;

    @Value("${resetPasswordUrl}")
    private String resetPasswordUrl;

    public ResponseEntity<AuthenticationDAO> registerUser(  @Valid RegistrationDTO registrationDTO) throws ProjectException, MessagingException {

         var existingUser = userRepository.findByEmail(registrationDTO.getEmail());
         if(existingUser.isPresent()){
             throw new ProjectException(EMAIL_ALREADY_EXIST);
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

        return ResponseEntity.ok(AuthenticationDAO.builder()
                .message("Account registration Successful!" +
                        " please check your mail to activate your account")
                .status("200")
                .build());

    }

    private void sendValidationEmail(User user) throws MessagingException {

        var newToken = generateAndSaveActivationToken(user);
        emailService.sendConfirmationEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                (String) newToken,
                "Account activation"

        );
    }

    private Object generateAndSaveActivationToken(User user) {
        //generateToken
        String generatedToken = generateActivationCode();
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken ;
    }

    private String generateActivationCode() {
        String characters = "123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < 8; i ++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }



    public ResponseEntity<AuthenticationDAO> activateAccount(String token) throws MessagingException, ProjectException {
        var savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ProjectException(TOKEN_DOES_NOT_EXIST));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            sendValidationEmail(savedToken.getUser());
            throw new ProjectException(ACTIVATION_TOKEN_EXPIRED);
        }
        var user = userRepository.findById(savedToken.getUser().getId()).orElseThrow
                (()-> new ProjectException(USER_NOT_FOUND));

        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);

        return ResponseEntity.ok(AuthenticationDAO.builder()
                .message("Account activated successfully!")
                .status("200")
                .build());

    }

    public AuthenticationDAO authenticate(@Valid AccountLoginDTO request) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail().trim(),
                            request.getPassword().trim()
                    )
            );

            var claims = new HashMap<String, Object>();
            var user = ((User) auth.getPrincipal());
            claims.put("fullName", user.fullName());

            var jwtToken = jwtService.generateToken(claims, user);

            return AuthenticationDAO.builder()
                    .message("Login successful!")
                    .status("200")
                    .token(jwtToken)
                    .build();

        } catch (AuthenticationException e) {
            return AuthenticationDAO.builder()
                    .message("Login failed: " + e.getMessage())
                    .status("401")
                    .build();
        }
    }

    public void generatePasswordResetTokenForUser (User user, String token){
        ResetPasswordToken passwordResetToken = new ResetPasswordToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setCreatedAt(LocalDateTime.now());
        passwordResetToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        passwordResetToken.setUser(user);
        resetPasswordTokenRepository.save(passwordResetToken);

    }

    public AuthenticationDAO forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws ProjectException, MessagingException {
        Optional<User> existingUser = userRepository.findByEmail(forgotPasswordRequest.getEmail());
        if(existingUser.isEmpty()){
            throw new ProjectException(USER_NOT_FOUND);
        }
        User user = existingUser.get();
        String token = UUID.randomUUID().toString();
        generatePasswordResetTokenForUser(user, token);

        String resetUrl = resetPasswordUrl + "?token=" + token;
        Map<String, Object> resetPasswordVariables = Map.of(
               "username", user.getName(),
               "resetPasswordUrl",resetUrl
        );

        emailService.sendEmail(
                EmailTemplateName.RESET_PASSWORD,
                user.getEmail(),
                "Reset Password",
                resetPasswordVariables
        );

        return AuthenticationDAO.builder()
                .message("Password Reset Link successfully sent to :" + user.getUsername())
                .status("200")
                .build();
    }


    public List<User> getAllUsers (){
        return userRepository.findAll();
    }

    @Transactional
    public AuthenticationDAO resetPassword(String token, ResetPasswordRequest resetPasswordRequest) throws ProjectException {
        var resetPasswordTokenOpt = resetPasswordTokenRepository.findByToken(token);
        if(resetPasswordTokenOpt.isEmpty()){
            throw new ProjectException(INVALID_TOKEN);
        }
        ResetPasswordToken resetPasswordToken = resetPasswordTokenOpt.get();
        if(resetPasswordToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new ProjectException(ACTIVATION_TOKEN_EXPIRED);
        }

        var userEmail = resetPasswordToken.getUser().getEmail();
        var user = userRepository.findByEmail(userEmail).orElseThrow(()-> new ProjectException(USER_NOT_FOUND));

        var newPassword = resetPasswordRequest.getNewPassword();
        var confirmPassword = resetPasswordRequest.getConfirmPassword();

        if(!newPassword.equals(confirmPassword)){
            throw new ProjectException(PASSWORDS_DO_NOT_MATCH);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return AuthenticationDAO.builder()
                .message("Reset Password successful!")
                .status("200")
                .build();

    }

}
