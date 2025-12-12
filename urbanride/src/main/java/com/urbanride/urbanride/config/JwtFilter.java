package com.urbanride.urbanride.config;

import com.urbanride.urbanride.Repository.UserRepository;
import com.urbanride.urbanride.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component                    // << REQUIRED
@RequiredArgsConstructor      // << REQUIRED
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String auth = request.getHeader("Authorization");

        if (auth != null && auth.startsWith("Bearer ")) {
            try {
                String token = auth.substring(7);
                String userId = jwtUtil.validateAndGetUserId(token);

                var opt = userRepository.findById(UUID.fromString(userId));

                if (opt.isPresent()) {
                    var u = opt.get();

                    var ud = User.withUsername(u.getEmail())
                            .password(u.getPassword())
                            .authorities(Collections.emptyList())
                            .build();

                    var authToken = new UsernamePasswordAuthenticationToken(
                            ud,
                            null,
                            ud.getAuthorities()
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception ignored) {}
        }

        filterChain.doFilter(request, response);
    }
}
