package com.example.student.service;

import com.example.student.dto.AuthenticationResponse;
import com.example.student.dto.LoginRequestData;
import com.example.student.model.Student;
import com.example.student.model.TokenVerification;
import com.example.student.repository.StudentRepository;
import com.example.student.repository.TokenVerificationRepository;
import com.example.student.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final TokenVerificationRepository tokenVerificationRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(Student student){
        Student studentToSave = new Student();
        studentToSave.setFirstName(student.getFirstName());
        studentToSave.setLastName(student.getLastName());
        studentToSave.setDateOfBirth(student.getDateOfBirth());
        studentToSave.setEmail(student.getEmail());
        studentToSave.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(studentToSave);

        String token = TokenVerificationGenerator(studentToSave);
    }

    private String TokenVerificationGenerator(Student student){
        String token = UUID.randomUUID().toString();
        TokenVerification tokenVerification = new TokenVerification();
        tokenVerification.setToken(token);
        tokenVerification.setStudent(student);
        tokenVerificationRepository.save(tokenVerification);
        return token;
    }

    public AuthenticationResponse login(LoginRequestData loginRequestData){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestData.getUsername(), loginRequestData.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(token, loginRequestData.getUsername());
    }
}
