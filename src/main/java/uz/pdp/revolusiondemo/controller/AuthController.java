package uz.pdp.revolusiondemo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.revolusiondemo.payload.SignInDto;
import uz.pdp.revolusiondemo.payload.SignUpDto;
import uz.pdp.revolusiondemo.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam String email, @RequestParam String code) {
        return ResponseEntity.ok(authService.verifyEmail(code, email));
    }

    @GetMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email) {
        return ResponseEntity.ok(authService.resetPassword(email));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInDto signIn) {
        return ResponseEntity.ok(authService.signIn(signIn));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDto signUp) {
        return ResponseEntity.ok(authService.signUp(signUp));
    }

    @PutMapping("/verify-reset-password")
    public ResponseEntity<?> verifyResetPassword(@RequestParam String email, @RequestParam String code, @RequestParam String password) {
        return ResponseEntity.ok(authService.acceptResetPassword(code, email, password));
    }
}
