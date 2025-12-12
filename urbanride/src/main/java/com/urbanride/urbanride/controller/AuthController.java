package com.urbanride.urbanride.controller;

import com.urbanride.urbanride.Dto.AuthRequest;
import com.urbanride.urbanride.Dto.AuthResponse;
import com.urbanride.urbanride.entity.User;
import com.urbanride.urbanride.Repository.UserRepository;
import com.urbanride.urbanride.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController{
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AuthRequest req){
        if(userRepository.findByEmail(req.getEmail()).isPresent())
            return ResponseEntity.badRequest().body("Email exists");

        var u = User.builder()
                .email(req.getEmail())
                .name(req.getName())
                .password(encoder.encode(req.getPassword()))
                .role(req.getRole())
                .phone(req.getPhone())
                .build();

        userRepository.save(u);

        return ResponseEntity.ok(
                new AuthResponse(
                        jwtUtil.generateToken(String.valueOf(u.getId())),
                        u.getName(),
                        u.getEmail(),
                        u.getRole(),
                        u.getId()
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req){
        var opt = userRepository.findByEmail(req.getEmail());
        if(opt.isEmpty() || !encoder.matches(req.getPassword(), opt.get().getPassword()))
            return ResponseEntity.status(401).body("Invalid");

        var u = opt.get();

        return ResponseEntity.ok(
                new AuthResponse(
                        jwtUtil.generateToken(String.valueOf(u.getId())),
                        u.getName(),
                        u.getEmail(),
                        u.getRole(),
                        u.getId()
                )
        );
    }


}
