 //code-start
package com.example.loginapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class LoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }
}

@RestController
@RequestMapping("/api/login")
class LoginController {

    private final Map<String, String> userDatabase = new HashMap<>();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        String encodedPassword = passwordEncoder.encode(loginRequest.getPassword());
        if (userDatabase.containsKey(loginRequest.getUsername()) &&
                userDatabase.get(loginRequest.getUsername()).equals(encodedPassword)) {
            return ResponseEntity.ok().body(new LoginSuccessResponse(loginRequest.getUsername()));
        } else {
            return ResponseEntity.status(401).body(new LoginFailureResponse("Invalid credentials"));
        }
    }
}

class LoginRequest {

    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class LoginSuccessResponse {

    private String username;

    public LoginSuccessResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

class LoginFailureResponse {

    private String errorMessage;

    public LoginFailureResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
//code-end