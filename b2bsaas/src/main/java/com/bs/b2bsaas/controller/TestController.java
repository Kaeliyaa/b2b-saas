package com.bs.b2bsaas.controller;

import com.bs.b2bsaas.entity.User;
import com.bs.b2bsaas.service.JwtService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/test")
public class TestController {

  private final JwtService jwtService;

  public TestController(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @GetMapping("/extract")
  public String testExtraction() {
    User user = new User();
    user.setEmail("john@example.com");

    UUID tenantId = UUID.randomUUID();

    String token = jwtService.generateToken(user, tenantId, "ADMIN");

    return """
                Username: %s
                Role: %s
                Tenant: %s
                Valid: %s
                """.formatted(
        jwtService.extractUsername(token),
        jwtService.extractRole(token),
        jwtService.extractTenantId(token),
        jwtService.isTokenValid(token, user)
    );
  }
}