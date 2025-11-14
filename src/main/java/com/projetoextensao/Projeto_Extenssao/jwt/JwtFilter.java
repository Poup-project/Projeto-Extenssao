package com.projetoextensao.Projeto_Extenssao.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.UUID;
import jakarta.servlet.Filter;

@Component
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;
    private static final ThreadLocal<UUID> currentClient = new ThreadLocal<>();

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public static UUID getCurrentClientId() {
        return currentClient.get();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        String authHeader = httpReq.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                UUID clientId = jwtUtil.extractClientId(token);
                currentClient.set(clientId);
            } catch (Exception ignored) {}
        }

        try {
            chain.doFilter(request, response);
        } finally {
            currentClient.remove();
        }
    }
}
