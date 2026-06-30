package com.vedant.E_Commerce.Project.security;

import com.vedant.E_Commerce.Project.entity.User;
import com.vedant.E_Commerce.Project.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public OAuth2SuccessHandler(JwtUtil jwtUtil,
                                UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException {

        OAuth2User oAuth2User =
                (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Find or create user
        Optional<User> existingUser =
                userRepository.findByEmail(email);

        User user;

        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            // Create new user from Google account
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword("GOOGLE_OAUTH"); // not used for login
            user.setAddress("Not provided");
            user.setRole("USER");
            userRepository.save(user);
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(email);

        // Redirect to frontend with token and role
        String redirectUrl = frontendUrl
                + "/oauth2/success?token="
                + token
                + "&role="
                + user.getRole();

        getRedirectStrategy()
                .sendRedirect(request, response, redirectUrl);
    }
}